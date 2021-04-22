package others.nio;

import java.nio.ByteBuffer;

/**
 *
 * NIO与IO的差别，IO操作对象直接为流数据，NIO操作对象为缓冲区
 * IO直接通过输入输出流连接文件与程序，并且数据流只能单向操作
 * NIO需要数据传输时，先建立文件与程序的管道，再通过缓冲区在管道中进行传输数据
 *      管道就像是铁路，而缓冲区就像是铁路上的火车，管道本身不传输数据，有缓冲区暂存，传输，可双向传输数据
 *
 * NIO为各个基本数据类型提供了相应的管道（boolean类型除外）
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * Buffer的核心属性（Buffer底层为一个数组）
 * capacity：表示缓冲区数组的长度，一旦指定，不可修改
 * limit：   表示允许读取的缓冲区数组最大下标
 * position：表示当前操作的数组下标
 * mark：    表示一个记录当前position的标记，可通过mark()进行标记、reset()方法回退到mark位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * allocate()初始化一个缓冲区
 * put()方法向缓冲区写入数据
 * get()方法从缓冲区读取数据
 * flip()读写转换
 * rewind() 重复读
 *
 * 直接缓冲区与非直接缓冲区
 *  直接缓冲区建立在物理内存中   ByteBuffer.allocateDirect()，
 *      可以一定情况下提高效率，但是将文件写入直接缓冲区，程序将失去对这部分数据的控制权
 *  非直接缓冲区建立在JVM的堆内存中  ByteBuffer.allocate()
 *
 * @author fengjirong
 * @date 2020/9/16 11:06
 */
public class BufferTest {

    public static void main(String[] args) {
        String str = "hello world";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //初始化时capacity与limit都等于缓冲区长度，position指向第一个数组下标
        System.out.println("-----------------allocate-------------------");
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        buffer.put(str.getBytes());
        //向缓冲区写入数据后，capacity与limit不变，而position指向下一个可操作下标
        System.out.println("-----------------put-------------------");
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        //转换读写
        buffer.flip();
        //转换缓冲区为读后，capacity不变，limit指向原position，而position指向缓冲区第一个数组下标
        System.out.println("-----------------flip-------------------");
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        byte[] arr = new byte[buffer.limit()];
        buffer.get(arr);
        System.out.println(new String(arr, 0, buffer.limit()));
        //get()方法结束后position与limit同时指向原limit位置
        System.out.println("-----------------get-------------------");
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        buffer.rewind();
        //重复读数据
        System.out.println("-----------------rewind-------------------");
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        //清空缓冲区，还原所有的标志，但是需要注意的是，缓冲区中的数据并没有被真的清除，
        //只是处于一个被遗忘的状态，通过下标还是可以获取到这些数据
        buffer.clear();
        byte b = buffer.get(0);
        System.out.println("-----------------clear-------------------");
        System.out.println((char)b);
        System.out.println("capacity = "+buffer.capacity());
        System.out.println("limit = "+buffer.limit());
        System.out.println("position = "+buffer.position());

        //测试mark属性
        doMark();
        //测试直接缓冲区
        doDirect();
    }

    static  void doMark(){
        String str = "hello world";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        System.out.println("---------------mark测试开始----------------");
        System.out.println("---------------初始化----------------");
        System.out.println("position = "+buffer.position());
        byte[] arr = new byte[buffer.limit()];
        buffer.get(arr, 0, 2);
        System.out.println("---------------读前两个----------------");
        System.out.println(new String(arr, 0, 2));
        System.out.println("position = "+buffer.position());

        //标记
        buffer.mark();

        buffer.get(arr, 2, 2);
        System.out.println("---------------读3到4两个----------------");
        System.out.println(new String(arr, 0, 2));
        System.out.println("position = "+buffer.position());

        //回滚到mark位置
        buffer.reset();
        System.out.println("---------------回滚----------------");
        System.out.println("position = "+buffer.position());
    }

    static void doDirect(){
        //初始化直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        //打印是否为直接缓冲区
        System.out.println("---------------是否为直接缓冲区----------------");
        System.out.println(buffer.isDirect());
    }
}
