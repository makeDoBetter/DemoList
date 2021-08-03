package others.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Description: 将handler注册组件抽离为单独的一个配置类
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/21 11:03
 * @since JDK 1.8
 */
public class HsHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加netty自带http编解码器，其包含HttpRequestDecoder与HttpResponseEncoder
        //HttpServerCodec extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpSeverHandler());
        System.out.println("入站处理器注册完成");
    }
}
