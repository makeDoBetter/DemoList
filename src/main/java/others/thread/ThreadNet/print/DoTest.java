package others.thread.ThreadNet.print;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/11 17:05
 * @since JDK 1.8
 */
public class DoTest {
    public static void main(String[] args) {
        /*只有一个TestThread对象*/
        TestThread t = new TestThread();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);

        t1.setName("线程1");
        t2.setName("线程2");

        t1.start();
        t2.start();
    }
}
