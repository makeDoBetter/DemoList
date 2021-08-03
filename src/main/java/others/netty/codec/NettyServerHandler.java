package others.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Description:自定义管道处理器，需要继承ChannelInboundHandlerAdapter并重写对应方法
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/14 16:22
 * @since JDK 1.8
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * description: 有读事件发生时处理逻辑
     *
     * @param ctx 通道处理器上下文
     * @param msg 客户端消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("id=" + student.getId() + "name="+ student.getName());
    }

    /**
     * description: 读事件处理完成后逻辑
     *
     * @param ctx 通道处理器上下文
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
    }

    /**
     * description: 异常处理逻辑
     *
     * @param ctx   通道处理器上下文
     * @param cause 异常对象
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
