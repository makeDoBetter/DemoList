package others.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: 群聊服务端
 * 实现客户端上下线提示及消息转发
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/12 14:39
 * @since JDK 1.8
 */
public class GroupChatServer {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.handler();
    }

    private GroupChatServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println("服务端异常");
            e.printStackTrace();
        }
    }

    private void handler() {
        try {
            while (true) {
                //阻塞两秒
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            System.out.println("客户端" + socketChannel.getRemoteAddress() + "上线");
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        if (key.isReadable()) {
                            //读取数据
                            readInfo(key);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readInfo(SelectionKey key) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            int count = channel.read(buffer);
            if (count > 0) {
                //打印客户端信息
                String msg = new String(buffer.array());
                System.out.println(msg);
                //转发
                sentToOther(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println("客户端" + channel.getRemoteAddress() + "下线");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sentToOther(String msg, SocketChannel self) {
        Set<SelectionKey> keys = selector.keys();
        System.out.println("此时有" + keys.size() + "客户端在线");
        SelectableChannel channel = null;
        for (SelectionKey key : keys) {
            channel = key.channel();
            try {
                if (channel instanceof SocketChannel && channel != self) {
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    SocketChannel dest = (SocketChannel) channel;
                    dest.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
