package others.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/9 16:38
 * @since JDK 1.8
 */
public class NioClient {
    public static void main(String[] args) {
        String str = "hello, i am client";
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 1234));
            socketChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
