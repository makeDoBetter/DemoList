package others.netty.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 自定义心跳处理器，重写 userEventTriggered()方法处理对应的事件
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/25 15:54
 * @since JDK 1.8
 */
public class HeartServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //向下转换
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventLog;
            switch (event.state()) {
                case READER_IDLE:
                    eventLog = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventLog = "写空闲";
                    break;
                case ALL_IDLE:
                    eventLog = "读写空闲";
                    break;
                default:
                    eventLog = "其他异常";
            }
            System.out.println(ctx.channel().remoteAddress() + eventLog);
        }
        super.userEventTriggered(ctx, evt);
    }
}
