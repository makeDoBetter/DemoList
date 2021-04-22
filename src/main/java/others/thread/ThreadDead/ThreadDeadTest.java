package others.thread.ThreadDead;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 死锁
 *
 * 多个线程被阻塞它们中的一个或全部在等待某个资源的释放，由于线程被无限期阻塞，因此程序无法正常终止。
 *
 * 线程死锁的必要条件
 * 1.互斥使用。一个资源被某个线程占用时，其他线程不可使用(非共享资源)
 * 2.不可抢占。资源请求者不能从资源占用者手中抢夺资源，只能有后者主动释放
 * 3.请求与保持。资源请求者在请求资源的时候，同时保持对原有资源的占用
 * 4.循环等待。即存在一个循环等待队列：A等待B的资源，B等待A的资源
 * 以上四个条件同时成立时即产生死锁，反之，只有有一个条件不满足，则死锁消失
 *
 * 案例
 * 客户（手握资金，等待商品）
 * 商户（手握商品，等待资金）
 *
 * 死锁在形式上是锁的嵌套
 *
 * 以下为一个必然死锁案例
 * @author fengjirong
 * @date 2020/9/24 10:04
 */
public class ThreadDeadTest {
    //资源唯一性
    private static Object task1 = new Object();
    private static Object task2 = new Object();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (task1){
                    System.out.println("线程1占有资源1请求资源2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (task2){
                        System.out.println("线程1占有资源2");
                    }
                }
            }
        });

        pool.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (task2){
                    System.out.println("线程2占有资源2请求资源1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (task1){
                        System.out.println("线程2占有资源1");
                    }
                }
            }
        });
    }
}
