package others.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * 非阻塞式NIO（针对网络IO）
 * <p>
 * 通道
 * \--SelectableChannel
 * \---SocketChannel
 * \---ServerSocketChannel
 * \---DatagramChannel
 * <p>
 * 缓冲区
 * Buffer
 * <p>
 * 选择器(Selector)：是SelectableChannel的多路复用器，用于监控SelectableChannel的状况
 * <p>
 * 选择器是实现非阻塞IO的关键，而非阻塞IO使用对象为网络IO，因此FIleChannel不适用，
 * 从底层来说，是因为FileCHannel没有继承SelectableChannel。
 * <p>
 * <p>
 * 以下有一个猜测，服务端首先初始化ServerSocketChannel的作用在于连接中间件，即初始化完成的选择器
 *
 * @author fengjirong
 * @date 2020/9/17 15:30
 */
public class NonBlockingNIOTest {

    /**
     * 客户端传输数据
     *
     * @param url the url
     * @return
     * @Author fengjirong
     * @Date 2020/9/17 15:41
     */
    public void client(String url) {
        SocketChannel sin = null;
        FileChannel in = null;
        try {
            //初始化通道
            sin = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8124));
            //设置非阻塞模式
            sin.configureBlocking(false);
            //初始化文件通道
            in = FileChannel.open(Paths.get(url), StandardOpenOption.READ);
            //初始化缓冲区
            ByteBuffer buff = ByteBuffer.allocate(1024);
            //初始化选择器
            Selector se = Selector.open();
            //注册选择器
            sin.register(se, SelectionKey.OP_READ);

            //读取文件并发送
            while (in.read(buff) != -1) {
                //切换读写模式
                buff.flip();
                sin.write(buff);
                buff.clear();
            }
            //new Thread(new GetResult(sin,se)).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sin != null) {
                    sin.close();
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

    class GetResult implements Runnable{

        private SocketChannel channel;

        private Selector select;

        public GetResult(SocketChannel channel, Selector select) {
            this.channel = channel;
            this.select = select;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if ((select.select() <= 0)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<SelectionKey> it = select.selectedKeys().iterator();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isReadable()) {
                        try {
                            while (channel.read(buffer) != -1){
                                buffer.flip();
                                System.out.println(buffer.toString());
                                buffer.clear();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    it.remove();
                }
            }


        }
    }

    /**
     * 服务端处理客户端发送的数据
     *
     * @param url the url
     * @return
     * @Author fengjirong
     * @Date 2020/9/17 15:56
     */
    public void server(String url) {
        ServerSocketChannel ssin = null;
        FileChannel in = null;
        SocketChannel sch = null;
        SocketChannel sin = null;
        try {
            //初始化服务端通道
            ssin = ServerSocketChannel.open();
            //切换模式
            ssin.configureBlocking(false);
            //绑定端口号
            ssin.bind(new InetSocketAddress(8124));
            //本地文件处理通道
            in = FileChannel.open(Paths.get(url),
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            //选择器初始化
            Selector sel = Selector.open();
            //注册网络通道到选择器中,并且指定监听事件，如果需要监听多个事件，可以使用\（或）运算符
            //如：ssin.register(sel, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);
            ssin.register(sel, SelectionKey.OP_ACCEPT);
            //轮循扫描事件
            while (sel.select() > 0) {
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        //获取客户端通道
                        sin = ssin.accept();
                        //切换成非阻塞模式
                        sin.configureBlocking(false);
                        //注册到选择器中
                        sin.register(sel, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        sch = (SocketChannel) key.channel();
                        //初始化缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (sch.read(buffer) != -1) {
                            buffer.flip();
                            in.write(buffer);
                            buffer.clear();
                        }
                    }
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sin != null) {
                    sin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ssin != null) {
                    ssin.close();
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
                if (sch != null) {
                    sch.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
