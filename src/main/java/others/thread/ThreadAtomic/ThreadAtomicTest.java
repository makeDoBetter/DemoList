package others.thread.ThreadAtomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程保持操作的原子性
 *
 * 一个任务包含多个操作，这写操作不会被其他线程打断，操作同生共死，不可分割
 *
 * 场景，100个线程执行0加100，有可能出现最终最终加和不是10000的情况，
 * 这是由于可能多个（n）线程同时操作共享变量，导致变量相加少了n-1
 *
 * 保持操作原子性的两种方法
 * 1.加锁
 * 2.使用原子类
 *
 * 原子类在java.util.concurrent.atomic下，其性能高效，线程安全
 * 拿AtomicInteger为例，其底层采用CAS(Compare And Swap)比较与交换原理保证数据的原子性
 * 假设同时有两个线程a、b获得主内存的值100，a线程加1后，使用旧值与主内存中变量值比较，发现相同，则拿新值更新主内存，
 * 与此同时，b线程加1后也拿自己的旧值与主内存变量值比较，发现不一致，此次加1作废，更新工作内存变量值，重新加1再比较
 * 如下
 * --主内存    10      10      10          11          11                11        12(更新)
 * --a线程     10      11      10==10      11          11                11        11
 * --b线程     10      11      11          10！=11     11（更新副本）    12        11==11
 *
 * 拓展
 * CAS与synchronized：乐观锁与悲观锁
 * --synchronized从悲观的角度出发，总是假设最坏的情况，认为每次有其他线程取访问共享数据的时候都会进行修改，
 * 因此每个线程访问的时候都会加锁，只有当前线程获得锁对象的时候才能进行操作
 * --CAS是从乐观的角度出发，总是假设最好的情况，认为每次其他线程访问共享数据的时候都不会对数据进行修改，因此不会上锁，
 * 只是在更新主数据的时候进行判断数据是否被人修改
 * 乐观锁综合性能较好，而悲观锁性能较差
 * ReentrantLock(显示锁)也是一个悲观锁
 * @author fengjirong
 * @date 2020/9/24 16:09
 */
public class ThreadAtomicTest {
    public static void main(String[] args) {
        Runnable task = new MyRunnable();

        for (int i = 0; i < 100; i++) {
            new Thread(task).start();
        }
    }
}

/**
 * 使用线程锁保持多线程的原子性
 *
 class MyRunnable implements Runnable{
    private int count = 0;
    @Override
    public void run() {
        synchronized ("ces"){
            for (int i = 0; i < 100; i++) {
                count++;
                System.out.println("=====>"+count);
            }
        };
    }
}*/

/**
 * 使用原子类实现多线程的原子性
 */
class MyRunnable implements Runnable{
    private AtomicInteger ato = new AtomicInteger();
    @Override
    public void run() {
        synchronized ("ces"){
            for (int i = 0; i < 100; i++) {
                System.out.println("=====>"+ato.incrementAndGet());
            }
        };
    }
}