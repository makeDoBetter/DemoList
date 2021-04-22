package others.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 通道(Channel)：用于源节点与目标节点的连接，其本身不存储数据，需要配合缓冲区进行数据的传输
 * <p>
 * 通道的主要实现类
 * java.nio.channels.Channel接口：
 * \--FileChannel
 * \--SocketChannel
 * \--ServerSocketChannel
 * \--DatagramChannel
 * 第一个为本地数据传输通道，后三个为网络传输通道
 * <p>
 * 获取通道的方法
 * 1. 支持通道的类提供了getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 网络IO:
 * Socket
 * ServerSocket
 * DatagramSocket
 * 2.  jdk 1.7 NIO.2针对通道类提供了静态open()方法
 * 3.  jdk 1.7 NIO.2对Files工具类的newByteChannel()
 * <p>
 * 通道之间之间进行数据传输
 * transferTo()/transferFrom()
 * <p>
 * 分散读取与聚集写入
 * 分散读取是通过一个通道将数据按顺序分散读取到多个缓冲区中
 * 聚集写入是将多个缓冲区中的数据聚集到一个通道中
 *
 * @author fengjirong
 * @date 2020/9/16 14:43
 */
public class ChannelTest {

    public static void main(String[] args) {
        //方法1
        //doCopy();
        //方法2
        //doCopy02();
        //通道间直接传输
        //doCopy03();
        //分散读出与聚集写入
        //doCopy04();
        //方法3
        //doCopy05();
        //handler();
        handlerG();
    }

    /**
     * 通过支持通道的类创建通道，操作通道完成字节数据的复制
     *
     * @return
     * @Author fengjirong
     * @Date 2020/9/16 15:07
     */
    static void doCopy() {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel chin = null;
        FileChannel chout = null;
        try {
            //输入输出流的初始化
            //只有节点流支持getChannel()方法获取Channel
            fin = new FileInputStream(new File("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture.jpg"));
            fout = new FileOutputStream(new File("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture01.jpg"));
            //通道初始化
            chin = fin.getChannel();
            chout = fout.getChannel();
            //缓冲区初始化
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //操作通道
            //将字节数据读入缓冲区，同时写出到硬盘中
            while (chin.read(allocate) != -1) {
                //读写转换
                allocate.flip();
                chout.write(allocate);
                //清空缓冲区
                allocate.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭通道、流
            try {
                if (chin != null) {
                    chin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (chout != null) {
                    chout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 采用直接缓冲区的方式复制字节数据（内存映射）
     * 只有FileChannel能够使用直接缓冲区
     *
     * @return
     * @Author fengjirong
     * @Date 2020/9/16 15:19
     */
    static void doCopy02() {
        FileChannel chin = null;
        FileChannel chout = null;
        try {
            //建立通道
            //StandardOpenOption.CREATE_NEW 表示创建文件，如果存在则报错
            //StandardOpenOption.CREATE     表示创建文件，如果存在则覆盖
            chin = FileChannel.open(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture.jpg"), StandardOpenOption.READ);
            chout = FileChannel.open(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture02.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
            //建立直接缓冲区
            //channel.map()方法与ByteBuffer.allocateDirect() 作用一致
            MappedByteBuffer inmap = chin.map(FileChannel.MapMode.READ_ONLY, 0, chin.size());
            MappedByteBuffer outmap = chout.map(FileChannel.MapMode.READ_WRITE, 0, chin.size());

            //因为是直接在物理内存中间建立缓冲区，所以不需要再进行数据流的copy
            byte[] dst = new byte[inmap.limit()];
            inmap.get(dst);
            outmap.put(dst);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //通道关闭
            try {
                if (chin != null) {
                    chin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (chout != null) {
                    chout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 直接通过通道之间传输数据(利用的也是直接缓冲区)
     *
     * @return
     * @Author fengjirong
     * @Date 2020/9/16 15:45
     */
    static void doCopy03() {
        FileChannel chin = null;
        FileChannel chout = null;
        try {
            //建立通道
            //StandardOpenOption.CREATE_NEW 表示创建文件，如果存在则报错
            //StandardOpenOption.CREATE     表示创建文件，如果存在则覆盖
            chin = FileChannel.open(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture.jpg"), StandardOpenOption.READ);
            chout = FileChannel.open(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture03.jpg"),
                    StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            //transferTo()与transferFrom()效果一致，只是两者之间的操作对象不同
            //chin.transferTo(0, chin.size(), chout);
            chout.transferFrom(chin, 0, chin.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (chin != null) {
                    chin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (chout != null) {
                    chout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 分散读取与聚集写入
     * 可以看作是对数组的数组的操作
     *
     * @return
     * @Author fengjirong
     * @Date 2020/9/16 16:24
     */
    static void doCopy04() {
        RandomAccessFile in = null;
        RandomAccessFile out = null;
        FileChannel chin = null;
        FileChannel chout = null;
        try {
            in = new RandomAccessFile("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\hello.txt", "rw");
            out = new RandomAccessFile("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\hello_01.txt", "rw");

            chin = in.getChannel();
            chout = out.getChannel();

            ByteBuffer all1 = ByteBuffer.allocate(100);
            ByteBuffer all2 = ByteBuffer.allocate(1024);
            ByteBuffer[] arr = {all1, all2};
            chin.read(arr);
            System.out.println(new String(arr[0].array(), 0, all1.limit()));
            System.out.println("-------------------------读取下一个缓冲区-------------------------------");
            System.out.println(new String(arr[1].array(), 0, all2.limit()));
            chout.write(arr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (chin != null) {
                    chin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (chout != null) {
                    chout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用Files.newByteChannel获取通道
     *
     * @return
     * @Author fengjirong
     * @Date   2020/9/21 15:37
     */
    static void doCopy05(){
        SeekableByteChannel in = null;
        SeekableByteChannel out = null;
        try {
            in = Files.newByteChannel(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture.jpg"), StandardOpenOption.READ);
            out = Files.newByteChannel(Paths.get("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture04.jpg"),
                    StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer des = ByteBuffer.allocate(1024);
            while (in.read(des) != -1){
                des.flip();
                out.write(des);
                des.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void handler(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\picture.jpg"));
            outputStream = new FileOutputStream(new File("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\handlerPicture.jpg"));
            FileChannel in = inputStream.getChannel();
            FileChannel out = outputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (in.read(buffer) != -1){
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void handlerG(){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("D:\\work\\myTestSource\\src\\main\\java\\others\\nio\\0408.txt"));
            FileChannel channel = outputStream.getChannel();
            String str1 = "hello";
            String str2 = "world";
            ByteBuffer b1 = ByteBuffer.allocate(1024);
            b1.put(str1.getBytes());
            b1.flip();
            ByteBuffer b2 = ByteBuffer.allocate(1024);
            b2.put(str2.getBytes());
            b2.flip();
            ByteBuffer[] byteBuffers = {b1, b2};
            channel.write(byteBuffers);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
