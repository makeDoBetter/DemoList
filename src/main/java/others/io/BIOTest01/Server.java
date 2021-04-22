package others.io.BIOTest01;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现单服务器端同时接收多客户端IO数据
 * 场景，客户端每输出一行，服务端打印到控制台
 * 原理，每接收到一个Socket，为其创建一个线程进行后续处理
 *
 * 传统阻塞IO，不使用多线程的情况下，当Socket没有数据时，服务端线程将会同步阻塞，等待数据传入
 * 使用多线程处理时，服务端将会创建与客户端对等数量的线程处理
 *
 * 局限性：
 * 1.每个Socket都会创建一个线程，线程之间的竞争、切换会影响系统性能；
 * 2.每个线程都会占用栈空间与CPU资源；
 * 3.并不是每个Socket实例都进行IO操作，有很多的无意义线程操作。
 *
 * 使用线程池解决线程栈溢出的局限性
 * 如果单个消息处理缓慢，或者线程池中的工作线程都阻塞，那么消息将会被堆积在任务队列中，新的Socket将会被拒绝，客户端会发生大量连接超时
 * @author fengjirong
 * @date 2020/12/18 15:53
 */
public class Server {
    public static void main(String[] args) {
        //伪异步IO并发
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("服务器启动");
            //解决以上局限性    使用线程池
            ExecutorService es = new ThreadPoolExecutor(3, 10,
                    120, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    getThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            while (true){
                Socket socket = ss.accept();
                System.out.println("一个客户端连接");
                es.submit(new ServerThreadReader(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            AtomicInteger sn = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                ThreadGroup g = Thread.currentThread().getThreadGroup();
                Thread t = new Thread(g, r);
                t.setName("线程" + sn.incrementAndGet());
                return t;
            }
        };
    }
}
