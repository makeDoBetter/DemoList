package others.thread.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 只有出现多个线程访问共享资源时才需要做同步，否则是不需要同步化处理的
 * <p>
 * 线程不安全模拟
 * 当多个线程使用同一个线程任务时，可能会导致线程不安全的出现
 * <p>
 * 例：两个线程同时进行取款
 * 小明取钱10000.0
 * 小红取钱10000.0
 * 小明余额为0.0
 * 小红余额为-10000.0
 * 因为多个线程对象同时操作一个线程任务，导致线程任务被拆分，因此导致余额为负的情况出现
 * 因此，为了保证线程安全，需要保证线程同步，线程同步就是为了解决线程安全问题
 * 解决线程安全问题的核心思想：让多个线程先后依次获得线程共享资源，解决线程安全问题
 * 线程同步的做法：把共享资源上锁，每次只能一个线程访问完成后，其他线程才能进行访问
 * <p>
 * 线程同步的三种方法：
 * 1.同步代码块
 * 2.同步方法
 * 3.lock显示锁
 * <p>
 * a.同步代码块
 * 作用：把出现线程安全的核心代码块进行上锁，每次只能一个线程进行资源的访问
 * 格式 synchronized(锁对象){
 * ----核心代码块
 * ----}
 * <p>
 * 锁对象：理论上可是是任意的，只要保证唯一就行（保证唯一是为了多个对象能够识别某个锁的状态）
 * 原则上：锁对象建议使用共享资源（唯一性）
 * --实例方法中建议使用this作为锁对象，此时this正好是共享资源；
 * --静态方法中建议使用类名.class作为锁对象
 * tips：同步锁代码块的范围越精细越好
 * <p>
 * b.同步方法
 * 作用：把出现线程安全的方法进行上锁，每次只能一个线程进行资源的访问
 * 格式 直接在方法上加一个修饰符 synchronized
 * 原理上跟同步代码块一样，只是上锁的范围扩大了
 * <p>
 * 同步锁底层也是有锁对象的
 * --实例方法中建议使用this作为锁对象，此时this正好是共享资源；
 * --静态方法中建议使用类名.class作为锁对象
 * <p>
 * c.Lock显示锁
 * 功能强大
 * 方法：lock()    上锁
 * unlock()  解锁
 * 为了代码的健壮性，应该使用 try-catch-finally 保证最终解锁，防止阻塞
 * <p>
 * 线程安全的小结
 * 线程安全，但是性能差
 * 线程不安全，性能好，因此不需要考虑线程安全问题，建议使用线程不安全
 * <p>
 * 拓展
 * StringBuilder 线程不安全
 * StringBuffer  线程安全
 * <p>
 * synchronized与Lock的区别：
 * 1.首先synchronized是java内置关键字，在jvm层面，Lock是个java类；
 * 2.synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁；
 * 3.synchronized会自动释放锁(a 线程执行完同步代码会释放锁 ；b 线程执行过程中发生异常会释放锁)，
 * Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁；
 * 4.用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。
 * 如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；
 * 5.synchronized的锁可重入、不可中断、非公平，而Lock锁可重入、可判断、可公平（两者皆可）
 * 6.Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。
 * <p>
 * synchronized锁是对象锁，而不是锁定某个方法或者代码块
 * 因此假设两个线程调用两个示例，那么就是有两把锁，这两把锁互不影响，可能出现异步操作
 * <p>
 * 线程A持有对象的Lock锁，线程B可以异步调用该对象的非synchronized方法；
 * 线程A持有对象的Lock锁，线程B调用该对象的synchronized方法则需要排队等待
 * <p>
 * 加锁可以保证多线程操作的原子性
 *
 * synchronized锁重入
 * 自己可以再次获得自己的内部锁
 * 当一个线程获得对象锁后，当再次请求获得当前对象锁时是可以获得这个锁的
 * 通俗来说：
 * --当一个线程获得当前对象的锁，并且还没有释放锁的时候，synchronized的方法可以调用其他synchronized的方法；
 * --一个类中的synchronized方法调用类里面其他synchronized方法，是看可以永远得到锁的；
 * --锁重入具有继承关系，即子类synchronized的方法可以调用父类synchronized的方法
 *
 * @author fengjirong
 * @date 2020/9/23 16:42
 */
public class ThreadSafeTest {
    public static void main(String[] args) {
        Account account = new Account(10000, "CUDS");
        Account account2 = new Account(10000, "CCDA");
        //脏读示例
        AccountDirtyRead account3 = new AccountDirtyRead();
        //锁重入
        Service service = new Service();
        //锁重入继承
        ServiceSon serviceSon = new ServiceSon();
        AccountLock accountLock = new AccountLock(10000, "CUDS");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        /*pool.submit(new Runnable() {
            @Override
            public void run() {
                //1.同步代码块
                //锁的是对象，不是代码块，因此两个线程分别引用不同对象，不会出现线程锁定，打印出异步结果
                account.doAmount01(10000);
                account.doAmount01(10000);
                //2.同步方法
                //account.doAmount02(10000);
                //3.Lock显示锁
                //accountLock.doAmount(10000);

            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                //1.同步代码块
                account2.doAmount01(10000);
                account2.doAmount01(10000);
                //2.同步方法
                //account.doAmount02(10000);
                //3.Lock显示锁
                //accountLock.doAmount(10000);
            }
        });*/

        //当线程1占有对象的锁时，线程2可以异步访问该对象的非synchronized方法，这样如果操作共享数据，会出现脏读
        //要解决这个问题，需要在可能出现脏读的方法也加上synchronized同步锁
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    account3.setAccountDirtyRead(1000, "中国");
                }
            },"线程1").start();
            Thread.sleep(500);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    account3.getInfo();
                }
            },"线程2").start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //线程重入测试实例
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.service1();
                serviceSon.printInfo();
            }
        }).start();
    }
}
