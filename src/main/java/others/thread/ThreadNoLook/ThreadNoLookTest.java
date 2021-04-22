package others.thread.ThreadNoLook;

/**
 * 并发编程中共享变量的不可见性
 *
 * 多线程修改共享变量会出现修改值后的不可见性
 * 底层原因
 * JMM(Java Memory Model)，Java内存模型。是JVM规范定义的一种内存模型描述了java程序中各种变量，
 * 尤其是共享变量的访问规则，描述把变量存储到底层的细节，以及在JVM中将变量存储到内存和从内存中读取变量的底层细节。
 * 是为了屏蔽各种硬件与操作系统的内存访问差异（内存屏障）
 * --所以共享变量（成员变量与类变量）都存在与主内存中，堆内存
 * --每个线程存在自己的工作内存（线程独享，缓存区，Java栈中是部分区域）,保留了被线程使用的共享变量的副本
 * --线程对共享变量的操作是在自己的工作内存上进行的，不能直接操作主内存
 * --不同线程不能访问对方的工作内存，访问变量只能通过主内存值传递完成
 *
 * 解决共享变量不可见性的方法
 * 1.volatile关键字
 * --带有此关键字的共享变量被线程修改时，会更新主内存中的值，同时其他线程的副本失效，并更新为新副本
 * 2.同步锁
 * 同步锁底层原理
 * ---线程获得锁对象
 * ---清空线程工作内存
 * ---拷贝主内存最新副本到工作内存
 * ---执行线程任务
 * ---将修改后的共享变量更新到主内存
 * ---释放锁
 * 由于同步锁会有工作内存的更新操作，因此可以很好的处理共享变量的不可见性
 *
 * volatile与synchronized差别
 * 1.volatile只能修饰成员变量跟类变量，synchronized可以修饰代码块跟成员方法
 * 2.volatile可以保证数据的可见行，但是不能保证原子性（线程安全），
 * synchronized是一种排他操作（使得线程安全），可以保证原子性
 * 3.volatile是轻量级的同步机制，对性能的影响比synchronized小
 *
 * 拓展：
 * volatile,synchronized,final 都保证了可见行性与有序性
 * 其实是保持主内存与工作内存的一致性与禁止指令重排
 *
 * 指令重排：计算机在执行程序的时候，为了提高性能，编译器与处理器常常会对指令进行重新排序
 * 而这在多线程场景下指令未按顺序执行，导致异常
 *
 * 原子性
 * 一个任务包含多个操作，这写操作不会被其他线程打断，操作同生共死，不可分割
 *
 * 参考：https://juejin.im/post/6861885337568804871?utm_source=gold_browser_extension
 * @author fengjirong
 * @date 2020/9/24 11:21
 */
public class ThreadNoLookTest {

    public static void main(String[] args) {
        MyThread th = new MyThread();
        th.start();

        while (true){
            synchronized (ThreadNoLookTest.class){
                if (th.isFlag()){
                System.out.println("主线程执行");
                }
            }
        }

    }
}

class MyThread extends Thread {
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            this.flag = true;
            System.out.println("flag="+this.flag);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
