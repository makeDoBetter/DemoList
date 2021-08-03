package others.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Description:客户端连接服务端发送并接收消息
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/14 16:34
 * @since JDK 1.8
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端线程池
        NioEventLoopGroup group = new NioEventLoopGroup();
        //客户端启动器
        Bootstrap bootstrap = new Bootstrap();
        //配置参数
        //绑定线程池
        try {
            bootstrap.group(group)
                    //绑定通道
                    .channel(NioSocketChannel.class)
                    //绑定处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加一个protoBuf的编码器
                            pipeline.addLast("encode", new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            //绑定服务端，异步
            System.out.println("客户端准备完成");
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 1234).sync();
            //监控通道关闭
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
