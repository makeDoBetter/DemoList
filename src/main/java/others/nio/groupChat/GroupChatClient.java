package others.nio.groupChat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Description:群聊客户端
 * 实现消息的发送与接受
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/12 16:01
 * @since JDK 1.8
 */
public class GroupChatClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1234;

    public GroupChatClient() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentInfo(String msg) {
        String info;
        try {
            info = socketChannel.getLocalAddress().toString() + "说：" + msg;
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int count = selector.select();
            if (count > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                if (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupClient = new GroupChatClient();
        try {
            System.out.println(groupClient.socketChannel.getLocalAddress().toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        groupClient.readInfo();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                groupClient.sentInfo(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                groupClient.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
