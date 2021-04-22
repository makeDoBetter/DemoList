package others.netty.http;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * Description:处理http请求与响应
 * HttpObject 客户端与服务端交流消息封装
 * SimpleChannelInboundHandler 继承这个handler只显式处理特性类型的消息，这里只处理HttpObject消息
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/21 10:34
 * @since JDK 1.8
 */
public class HttpSeverHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            String uri = ((HttpRequest) msg).uri();
            //通过uri过滤静态资源-网页图标请求
            //uri 统一资源标识符 人名  张三
            //url 统一资源定位符 户口本上定位具体一个人 江西省/南昌市/红谷滩新区/。。。/张三
            if ("/favicon.ico".equals(uri)){
                return;
            }
            //打印客户端消息
            System.out.println(msg.getClass());
            //响应客户端消息
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello, 我是服务端", CharsetUtil.UTF_8);
            //生产HttpResponse对象,设置response版本，状态码,响应内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //设置响应数据类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            //设置响应文本长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
