package others.nio;

import org.junit.Test;

/**
 * @author fengjirong
 * @date 2020/9/17 16:36
 */
public class NIOTest {
    NonBlockingNIOTest test = new NonBlockingNIOTest();

    @Test
    public void testClient(){
        test.client("D:\\work\\myTestSource\\src\\test\\java\\others\\nio\\picture.jpg");
    }

    @Test
    public void testServer(){
        test.server("D:\\work\\myTestSource\\src\\test\\java\\others\\nio\\picture01.jpg");
    }

}
