package others.机试.h;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/11 17:11
 * @since JDK 1.8
 */
public class TestRunnable implements Runnable {
    int i = 1;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                notify();
                try {
                    Thread.currentThread();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i < 100) {
                    System.out.println(i);
                    i++;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
