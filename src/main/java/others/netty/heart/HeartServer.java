package others.netty.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 实现Netty心跳监控并处理
 * 客户端使用 netty\groupChat\Client.java
 * Netty心跳机制核心在 IdleStateHandler()处理器中，
 * 其底层使用的是Netty中定时任务实现read/write空闲监控并由下一个处理器处理对应的事件
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/25 15:41
 * @since JDK 1.8
 */
public class HeartServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //Netty心跳处理器，用于在对应时间内未发生读写操作时触发事件
                            //long readerIdleTime 读时间
                            // long writerIdleTime 写时间
                            // long allIdleTime 读写时间
                            // TimeUnit unit 时间单位
                            //当触发对应事件后会传递到当前处理器的后一个处理器处理这些对应的事件
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(1234).sync();
            System.out.println("服务端启动成功");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
