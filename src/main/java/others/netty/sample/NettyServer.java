package others.netty.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Description:Netty服务端，接收并打印客户端发送的消息并返回
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/14 16:07
 * @since JDK 1.8
 */
public class NettyServer {
    public static void main(String[] args) {
        //创建两个线程池
        //boss只处理客户端连接请求
        //worker处理真正的业务请求（read，write）
        //两个线程池都会一直循环
        //每个线程池中默认拥有的NioEventLoop数量是 cpu*2
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        //创建启动器，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        //配置参数
        try {
            //1.设置线程组
            bootstrap.group(boss, worker)
                    //2.设置通道
                    .channel(NioServerSocketChannel.class)
                    //3.设置连接队列长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //4.设置保持活跃状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //5.设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //给通道绑定的管道最后维护一个处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务端准备完毕--");
            //绑定一个端口，异步模型实现，不会阻塞
            ChannelFuture cf = bootstrap.bind(1234).sync();
            //给ChannelFuture对象绑定一个监听器
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()){
                        System.out.println("服务端绑定接口成功");
                    }
                    else if (cf.isCancelled()){
                        System.out.println("服务端绑定接口取消");
                    }
                    else if (cf.isDone()){
                        System.out.println("服务端绑定接口完成");
                    }else {
                        System.out.println("服务端绑定接口失败");
                    }
                }
            });
            //监听通道关闭
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
