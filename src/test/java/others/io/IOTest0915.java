package others.io;

import org.junit.Test;

import java.io.*;

/**
 * 处理流测试类，处理流即节点流外再包裹一层流，外面这层流即为处理流
 *
 * @author fengjirong
 * @date 2020/9/15 10:53
 */
public class IOTest0915 {

    /**
     * 使用缓冲流进行字节流数据的复制，缓冲流默认缓冲区为2E13,当缓冲区满的时候进行一次缓存刷新操作，
     * 即将缓冲区中的数据写入硬盘，提高文件写入速度，
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/15 11:04
     */
    @Test
    public void BufferTest(){
        BufferedInputStream bufferIn = null;
        BufferedOutputStream bufferOut = null;
        try {
            //初始化File对象
            File source = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\picture.jpg");
            File to = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\picture04.jpg");
            //初始化IO对象
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out  = new FileOutputStream(to);
            bufferIn = new BufferedInputStream(in);
            bufferOut = new BufferedOutputStream(out);
            //IO操作
            int len;
            byte[] bytes = new byte[1024];
            while ((len = bufferIn.read(bytes)) != -1){
                bufferOut.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭IO
            //只要将外层包裹的处理流关闭了，那么节点流也会关闭
            try {
                if (bufferOut != null){
                    bufferOut.close();
                }
                if (bufferIn != null){
                    bufferIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字符流的复制操作，缓冲流有一个FileIReader没有的方法
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/15 11:11
     */
    @Test
    public void bufferReadAndWrite() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            //File 对象的初始化
            File source = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_01.txt");
            File to = new File("D:\\work\\myTestSource\\src\\test\\java\\others\\io\\hello_03.txt");
            //IO 对象的初始化
            FileReader in = new FileReader(source);
            FileWriter out = new FileWriter(to);
            reader = new BufferedReader(in);
            writer = new BufferedWriter(out);
            //操作IO
            //readLine()方法直接读取一行，当读取完毕后放回null
            String sBuffer;
            while ((sBuffer = reader.readLine()) != null){
                writer.write(sBuffer+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭IO
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
