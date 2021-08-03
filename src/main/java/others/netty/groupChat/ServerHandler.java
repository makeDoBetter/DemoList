package others.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 实现客户端上下线状态、消息通知
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/25 10:47
 * @since JDK 1.8
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 定义一个全局的单线程的静态变量，用于存储整个连接的客户端集合
     */
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //不需要遍历，group可以直接处理
        group.writeAndFlush("[客户端]" + channel.remoteAddress() + "上线");
        group.add(channel);
        System.out.println(group.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //不需要手动remove()当前channel，group会自动剔除离线channel
        group.writeAndFlush("[客户端]" + channel.remoteAddress() + "下线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel self = ctx.channel();
        //实现消息的转发,忽略自身
        group.forEach(channel -> {
            if (self != channel) {
                System.out.println(msg + "发送到客户端" + channel.remoteAddress());
                channel.writeAndFlush("[客户端]" + self.remoteAddress() + "说：" + msg + "\n");
            }
        });
    }
}
