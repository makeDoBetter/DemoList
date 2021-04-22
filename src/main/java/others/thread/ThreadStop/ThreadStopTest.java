package others.thread.ThreadStop;

/**
 * @author fengjirong
 * @date 2020/10/19 17:09
 */
public class ThreadStopTest {
    public static void main(String[] args) {
        /*MyThread th1 = new MyThread("线程1");
        th1.start();
        Thread.sleep(3000);
        th1.interrupt();*/

        MyThread01 th2 = new MyThread01("线程2");
        th2.start();
        th2.interrupt();
        System.out.println(th2.isInterrupted());
        /*MyThread02 th3 = new MyThread02("线程2");
        th3.start();
        th3.interrupt();
        System.out.println(th3.isInterrupted());*/
    }
}

/**
 * 使用异常抛出停止线程运行
 *
 * @return
 * @Author fengjirong
 * @Date   2020/10/19 17:56
 */
class MyThread extends Thread{

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 1000000; i++) {
                if (Thread.interrupted()){
                    System.out.println("检测到线程中断，循环结束");
                    throw new InterruptedException();
                }
                System.out.println(Thread.currentThread().getName() + i);
            }
        } catch (InterruptedException e) {
            System.out.println("线程抛出异常");
            e.printStackTrace();
        }
    }
}

/**
 * description: sleep让线程进入中断状态
 * 线程休眠状态下停止线程，会使线程进入catch，并且清除停止状态值，使状态值为false
 *
 * @Author fengjirong
 * @Date   2020/10/20 14:53
 */
class MyThread01 extends Thread{
    public MyThread01(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 50000; i++) {
                System.out.println(Thread.currentThread().getName()+"--->"+i);
            }
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println("sleep后进入异常");
            System.out.println("线程sleep后进入异常中断状态" + Thread.currentThread().isInterrupted());
            e.printStackTrace();
        }
    }
}


/**
 * description: 使用return+interrupt停止线程，不推荐使用，推荐使用try-catch处理
 *
 * @Author fengjirong
 * @Date   2020/10/20 15:17
 */
class MyThread02 extends Thread{
    public MyThread02(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        while (true){
            if (this.isInterrupted()){
                System.out.println("stop");
                return;
            }
        }
    }
}
