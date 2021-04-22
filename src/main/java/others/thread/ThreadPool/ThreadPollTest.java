package others.thread.ThreadPool;

import java.util.concurrent.*;

/**
 * 线程池
 * 是一个容纳多个线程对象的容器，其中的线程可以反复使用，省去了频繁创建跟销毁线程的操作，避免反复创建线程而消耗资源
 * <p>
 * 线程池的优点：
 * 1.降低资源消耗
 * --减少创建跟销毁线程的次数，每个线程都可以反复使用
 * 2.提高相应速度
 * --这是相对的，因为不用频繁创建线程，因此不易导致系统宕机
 * 3.提高线程的可管理性
 * --能够指定线程的数量，不会因线程过多而导致死机
 * <p>
 * 线程池
 * 工作线程
 * 任务队列
 * 线程管理器
 * 线程池包含指定数量的线程与线程任务队列，线程池中的线程依次分配给任务队列中的任务，
 * 没有分配到的任务存在与阻塞任务队列中，等待其他任务完成，被释放的线程分配给自己
 * 当线程池中的线程没有任务时，都处于等待状态
 * 线程管理器用于管理线程
 * <p>
 * 线程池的核心思想：线程的复用，同一个线程可以重复使用，处理多个任务
 * <p>
 * 线程池创建
 * 线程池Java的代表类：ExecutorService
 * <p>
 * 不推荐使用以下方法
 * Executors类下提供的public static ExecutorService newFixedThreadPool(int nThreads)
 * 用来创建一个指定线程数量的线程池
 *
 * 四种线程池创建差异
 * <i>newCachedThreadPool</i> 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。对于执行
 * 很多短期异步任务的程序而言，这些线程池通常可提高程序性能。调用 execute 将重用以前构造
 * 的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并
 * 从缓存中移除那些已有 60 秒钟未被使用的线程。因此，长时间保持空闲的线程池不会使用任何资
 * 源。
 * <i>newFixedThreadPool</i> 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。在任意点，在大
 * 多数 nThreads 线程会处于处理任务的活动状态。如果在所有线程处于活动状态时提交附加任务，
 * 则在有可用线程之前，附加任务将在队列中等待。如果在关闭前的执行期间由于失败而导致任何
 * 线程终止，那么一个新线程将代替它执行后续的任务（如果需要）。在某个线程被显式地关闭之
 * 前，池中的线程将一直存在。
 * <i>newScheduledThreadPool</i> 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
 * <i>newSingleThreadExecutor</i> Executors.newSingleThreadExecutor()返回一个线程池（这个线程池只有一个线程）,这个线程
 * 池可以在线程死后（或发生异常时）重新启动一个线程来替代原来的线程继续执行下去
 * <p>
 * Future<?> submit(Runnable task);用来提交一个Runnable的线程任务对象,提交完自动执行
 * Future<?> submit(Callable task);用来提交一个Callable的线程任务对象，提交完自动执行
 *
 * 线程池初始化的时候并不会立刻创建指定数量的工作线程，而是在线程池添加任务的时候一个个创建，最终创建到指定数量的
 * 工作线程，例如：Executors.newFixedThreadPool(3)指定数量为3，但是只进行两次submit或execute,最终该线程池中只会有两个
 * 工作线程
 *
 * @author fengjirong
 * @date 2020/9/23 23:19
 */
public class ThreadPollTest {
    public static void main(String[] args) {
        //创建一个线程池
        //不推荐使用该方法创建线程
        //ExecutorService pool = Executors.newFixedThreadPool(3)
        ExecutorService pool = new ThreadPoolExecutor(3, 10,
                120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                getThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //初始化任务
        Runnable task = new MyRunnable();
        //添加任务队列
        //任务1，创建新线程，提交后自动执行
        pool.submit(task);
        //任务2，创建新线程，提交后自动执行
        pool.submit(task);
        //任务3，创建新线程，提交后自动执行
        pool.submit(task);
        //任务4，等待前面完成，复用已有线程
        pool.submit(task);
        //以下为实现Callable的线程任务
        Future<String> f1 = pool.submit(new MyCallable(15));
        Future<String> f2 = pool.submit(new MyCallable(20));
        Future<String> f3 = pool.submit(new MyCallable(25));
        Future<String> f4 = pool.submit(new MyCallable(30));

        //不需要担心主线程的get()方法在实现子线程submit()前取不到值，
        // 即使get()先抢到线程，也会先将线程让给任务
        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
            System.out.println(f4.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //正常情况下线程池是不会消亡的
        //如果想要线程死亡，以下两个方法可以使用
        //等待任务完成后关闭线程池
        pool.shutdown();
        //立即关闭线程池，即使还有任务没有执行完成
        //pool.shutdownNow();
    }

    /**
     * description: 获得线程工厂
     *
     * @return ThreadFactory
     * @Author fengjirong
     * @Date   2020/12/21 11:59
     */
    static private ThreadFactory getThreadFactory(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                ThreadGroup group = Thread.currentThread().getThreadGroup();
                Thread thread = new Thread(group, r);
                return thread;
            }
        };
    }


}

class MyRunnable implements Runnable {

    public MyRunnable() {
        System.out.println("构造方法：" + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "===" + i);
        }
    }
}

class MyCallable implements Callable<String> {
    private int n;

    public MyCallable(int n) {
        this.n = n;
    }

    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return Thread.currentThread().getName() + " 计算0到" + this.n + "=" + sum;
    }
}