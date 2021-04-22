package others.thread.ThreadNet;

/**
 * 线程通信
 * 消息队列的原理
 *
 * 多个线程因为在同一个进程中，因此互相通信比较容易
 * 线程通信的经典模型：生产者与消费者问题
 *  生产者生产商品，消费者消费商品
 *  生产不能过剩吗，消费不能剩余
 *
 * 模拟案例
 * 消费者：小红、小明取钱
 * 生产者：爸爸、岳父、干爹
 *
 * 小明、小红取钱，有则取，没有则等待，唤醒爸爸们存钱
 * 爸爸们，钱大于0则等待，否则存钱，唤醒小明、小红取钱
 *
 * 注意：线程通信一定是多个线程同时操作一个共享资源的情况下
 *       需要首先保证线程安全，否则线程通信毫无意义
 *
 * 线程通信提供的Object的核心方法
 *  wait()      当前线程进入等待状态，必须由锁对象进行调用
 *  notify()    唤醒当前锁对象上的某个线程，必须由锁对象进行调用
 *  notifyAll() 唤醒当前锁对象上的所有线程，必须由锁对象进行调用
 *
 *  wait()会放弃当前线程的对象锁，并且当前线程进入等待此对象的等待池，只有针对此对象调用notify()本线程才进入对象锁定池
 *  准备获取对象锁进入运行状态
 * @author fengjirong
 * @date 2020/9/23 20:56
 */
public class ThreadNetTest {
    public static void main(String[] args) {
        Account account = new Account(10000,"ICBC_001");
        Thread littleMing = new DrawThread(account,"小明", 10000);
        Thread littleRed = new DrawThread(account,"小红", 10000);

        Thread bb1 = new SaveThread(account, "爸爸", 10000);
        Thread bb2 = new SaveThread(account, "岳父", 10000);
        Thread bb3 = new SaveThread(account, "干爹", 10000);

        //多个线程同时操作同一个资源
        littleMing.start();
        littleRed.start();
        bb1.start();
        bb2.start();
        bb3.start();

    }
}
