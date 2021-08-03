package others.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/22 17:42
 * @since JDK 1.8
 */
public class ByteBufTest {
    public static void main(String[] args) {
        //根据消息与编码方式创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
        //是否存在数据
        if (byteBuf.hasArray()) {
            //获得数据数组
            byte[] array = byteBuf.array();
            System.out.println(new String(array, CharsetUtil.UTF_8));
            //读取一个字符，readIndex会加1并修改可读缓冲区长度
            System.out.println(byteBuf.readByte());
            //读取指定下标的字符，不会改变readIndex
            System.out.println(byteBuf.getByte(0));
            //可读的缓冲区长度
            //this.writerIndex - this.readerIndex
            System.out.println(byteBuf.readableBytes());
            //获得ReadIndex
            System.out.println(byteBuf.readerIndex());
            //获得WriteIndex
            System.out.println(byteBuf.writerIndex());
            //获得缓冲区容量 此处为64
            System.out.println(byteBuf.capacity());
            //是否可读
            System.out.println(byteBuf.isReadable());
            //片段读 从下标0开始读6个字符，通过utf-8进行解码
            System.out.println(byteBuf.getCharSequence(0,6, Charset.forName("utf-8")));
        }
    }
}
