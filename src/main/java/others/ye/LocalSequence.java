package others.ye;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.SYNC;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 *
 * @author maketubo
 * @version 1.0
 * @ClassName LocalSequence
 * @description
 * @date 2020/6/1 10:38
 * @since JDK 1.8
 */
public class LocalSequence extends AbstractSequence{

    private Logger logger = LoggerFactory.getLogger(LocalSequence.class);

    private static final String BLOOM_FILTER_PATH = "/app/bloomFilter.data";

    private static final String DEFAULT_INDEX_FILE_PATH = "/app/seq.index";

    private static final String DEFAULT_DATA_FILE_PATH = "/app/seq.data";

    private String indexFilePath = DEFAULT_INDEX_FILE_PATH;
    private String dataFilePath = DEFAULT_DATA_FILE_PATH;

    private MappedByteBuffer indexBuffer;
    private MappedByteBuffer dataBuffer;

    private volatile int seqSize = 0;

    private ReentrantLock lock = new ReentrantLock();

    public <T> LocalSequence(SequenceInitJob<T> job, String prefix) {
        //加载布隆过滤器
        //加载堆外内存
        loadSequences(job, prefix);
    }

    public <T> LocalSequence(String indexFilePath, String dataFilePath, SequenceInitJob<T> job, String prefix) {
        this.indexFilePath = indexFilePath;
        this.dataFilePath = dataFilePath;
        //加载布隆过滤器
        //加载堆外内存
        loadSequences(job, prefix);
    }

    // index 现在顺序排列 后面改为二叉树
    @Override
	protected <T> void loadSequences(SequenceInitJob<T> job, String prefix) {
        //维护关于这两个文件的堆外内存
        // 0.1M 假设seqName 32 byte  + seqVal or offset 8byte = 40 byte;
        // 每40 1piece
        // 可以存 1024 * 100 byte / 40 = 2500多个seq
        File indexFile = new File(indexFilePath); File dataFile = new File(dataFilePath);
        boolean dataExists = dataFile.exists();
        boolean indexExists = dataFile.exists();
        Map<String, Long> sequenceMap = getSequenceMap(job.injectSequences(), prefix);
        seqSize = sequenceMap.size();
        if(dataExists) {
            dataBuffer = initBuf(dataFile, 1024 * 100);
            //先从索引文件里面找
            if(indexExists) {
                indexBuffer = initBuf(indexFile, 1024 * 100);
                Map<SeqName, Long> remoteSeqMap = sequenceMap.entrySet().stream()
                        .collect(Collectors.toMap(entry ->
                                        new SeqName(entry.getKey().getBytes(Charset.forName("UTF-8"))),
                                entry -> entry.getValue()));
                byte[] nameBytes = new byte[32];
                byte[] offsetBytes = new byte[4];
                byte[] valBytes = new byte[8];
                while (indexBuffer.remaining() >= 32 && dataBuffer.remaining() >= 40){
                    indexBuffer.get(nameBytes);
                    indexBuffer.get(offsetBytes);
                    Long remoteVal = remoteSeqMap.get(new SeqName(nameBytes));
                    int offset = Ints.fromByteArray(offsetBytes);
                    dataBuffer.position(offset);
                    dataBuffer.get(valBytes);
                    long localVal = Longs.fromByteArray(valBytes);
                    if(remoteVal != null && remoteVal > localVal) {
                        //要把远程的seq写入localSeq文件里
                        dataBuffer.position(offset);
                        dataBuffer.put(valBytes);
                    }
                }
            } else {
                indexBuffer = initBuf(indexFile, 1024 * 100);
                Map<SeqName, Long> remoteSeqMap = sequenceMap.entrySet().stream()
                        .collect(Collectors.toMap(entry ->
                                        new SeqName(entry.getKey().getBytes(Charset.forName("UTF-8"))),
                                entry -> entry.getValue()));
                byte[] nameBytes = new byte[32];
                byte[] valBytes = new byte[8];
                int i = 32;
                int nextStart = 0;
                while (indexBuffer.remaining() >= 32 && dataBuffer.remaining() >= 40){
                    dataBuffer.get(nameBytes);
                    dataBuffer.get(valBytes);
                    Long remoteVal = remoteSeqMap.get(new SeqName(nameBytes));
                    long localVal = Longs.fromByteArray(valBytes);
                    if(remoteVal > localVal) {
                        //要把远程的seq写入localSeq文件里
                        dataBuffer.position((nextStart + 1) * 32);
                        dataBuffer.put(valBytes);
                    }
                    nextStart = nextStart * 36;
                    indexBuffer.position(nextStart);
                    indexBuffer.put(mergeByteArray(nameBytes, Ints.toByteArray(i)));
                    i = i + 40;
                    nextStart ++;
                }
            }
        } else {
            getBytes(sequenceMap);
        }
    }

    private byte[] getBytes(Map<String, Long> sequenceMap) {
        dataBuffer = initBuf(new File(dataFilePath), 1024 * 100);
        indexBuffer = initBuf(new File(indexFilePath), 1024 * 100);
        dataBuffer.position(0);
        indexBuffer.position(0);
        int i = 0;
        byte[] result = null;
        for (Map.Entry<String, Long> entry : sequenceMap.entrySet()) {
            byte[] seqNameBytes = entry.getKey().getBytes(Charset.forName("UTF-8")); //32
            if (seqNameBytes.length < 32) {
                byte[] zeroBytes = new byte[32 - seqNameBytes.length];
                seqNameBytes = mergeByteArray(seqNameBytes, zeroBytes);
            }
            indexBuffer.put(seqNameBytes);
            dataBuffer.put(seqNameBytes);
            indexBuffer.put(Ints.toByteArray(32 + 40 * i));
            byte[] seqValBytes = Longs.toByteArray(entry.getValue()); //8
            dataBuffer.put(seqValBytes);
            result = mergeByteArray(result,
                    mergeByteArray(seqNameBytes, seqValBytes, 0, seqValBytes.length));
            i++;
        }
        return result;
    }

    @Override
    public Long getNext(String seqName) {
        return getNext(seqName, 1);
    }

    @Override
    public Long getNext(String seqName, int num) {
        lock.lock();
        try {
            //布隆过滤器判断是否seqName在文件中
            //不在文件中直接返回 -1
            indexBuffer.position(0);
            byte[] nameBytes = new byte[32];
            byte[] offsetBytes = new byte[4];
            byte[] valBytes = new byte[8];
            while (indexBuffer.remaining() >= 32 && dataBuffer.remaining() >= 40){
                indexBuffer.get(nameBytes);
                indexBuffer.get(offsetBytes);
                int offset = Ints.fromByteArray(offsetBytes);
                dataBuffer.position(offset);
                dataBuffer.get(valBytes);
                long localVal = Longs.fromByteArray(valBytes);
                if(compareIgnoreLen(nameBytes, seqName.getBytes()) == 0){
                    long result = localVal + num;
                    dataBuffer.position(offset);
                    dataBuffer.put(Longs.toByteArray(result));
                    return result;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    private MappedByteBuffer initBuf(File file, long size) {
        //把文件内容映射到堆外内存上
        try (FileChannel channel = FileChannel.open(Paths.get(file.getAbsolutePath()),
                READ, WRITE, CREATE, SYNC)){
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            // finally
            if (dataBuffer != null) {
                CleanerUtil.clean(dataBuffer);
            }
            if (indexBuffer != null) {
                CleanerUtil.clean(indexBuffer);
            }
        } finally {
            super.finalize();
        }
    }

    private byte[] mergeByteArray(byte[] remain, byte[] bytes){
        return mergeByteArray(remain, bytes, 0, bytes.length);
    }

    private byte[] mergeByteArray(byte[] remain, byte[] bytes, int from, int to){
        byte[] result;
        if(remain == null) {
            result =  new byte[to - from];
            System.arraycopy(bytes, from, result, 0, result.length);
        } else {
            result =  new byte[remain.length + to - from];
            System.arraycopy(remain, 0, result, 0, remain.length);
            System.arraycopy(bytes, from, result, remain.length, to - from);
        }
        return result;
    }

    public static int compareIgnoreLen(byte[] a, byte[] b)
    {
        if (a == b)
        {
            return 0;
        }
        if (a == null)
        {
            return -1;
        }
        if (b == null)
        {
            return 1;
        }
        int minLen = Math.min(a.length, b.length);
        for (int i = 0; i < minLen; ++i)
        {
            int aVal = a[i] & 0xFF, bVal = b[i] & 0xFF;
            if (aVal < bVal)
            {
                return -1;
            }
            if (aVal > bVal)
            {
                return 1;
            }
        }
        return 0;
    }

    public void writeVal(String seqName, Long currentVal) {
        lock.lock();
        try {
            //布隆过滤器判断是否seqName在文件中
            //不在文件中直接返回 -1 然后写入最后
            indexBuffer.position(0);
            byte[] nameBytes = new byte[32];
            byte[] offsetBytes = new byte[4];
            boolean find = false;
            while (indexBuffer.remaining() >= 32 && dataBuffer.remaining() >= 40){
                indexBuffer.get(nameBytes);
                indexBuffer.get(offsetBytes);
                int offset = Ints.fromByteArray(offsetBytes);
                if(compareIgnoreLen(nameBytes, seqName.getBytes()) == 0){
                    find = true;
                    dataBuffer.position(offset);
                    dataBuffer.put(Longs.toByteArray(currentVal));
                }
            }
            if(!find) {
                int indexOffset = (seqSize - 1) * 40;
                int dataOffset = (seqSize - 1) * 36;
                byte[] seqNameBytes = seqName.getBytes();
                if (seqNameBytes.length < 32) {
                    byte[] zeroBytes = new byte[32 - seqNameBytes.length];
                    seqNameBytes = mergeByteArray(seqNameBytes, zeroBytes);
                }
                indexBuffer.position(indexOffset);
                indexBuffer.put(seqNameBytes);
                indexBuffer.put(Ints.toByteArray(dataOffset));
                dataBuffer.position(dataOffset);
                dataBuffer.put(seqNameBytes);
                dataBuffer.put(Longs.toByteArray(currentVal));
            }
        } finally {
            lock.unlock();
        }
    }
}
