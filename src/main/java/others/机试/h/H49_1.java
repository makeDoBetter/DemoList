package others.机试.h;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/11 16:25
 * @since JDK 1.8
 */
public class H49_1 {
    public static void main(String[] args) {
        TestRunnable testRunnable1 = new TestRunnable();
        TestRunnable testRunnable2 = new TestRunnable();

        new Thread(testRunnable1).start();
        new Thread(testRunnable2).start();


    }
}