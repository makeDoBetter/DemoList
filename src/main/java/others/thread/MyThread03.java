package others.thread;

import org.springframework.cglib.proxy.CallbackFilter;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过实现Callable()接口的创建线程对象
 *
 * 1.自定义类实现Callable()接口
 * 2.重新call()方法
 * 3.初始化Callable的线程任务对象
 * 4.初始化一个未来任务对象
 * 5.将未来任务对象封装成一个线程对象
 * 6.通过线程对象执行start()方法
 *
 * 优点
 * 1.线程任务类只是实现了Callable()接口，还能实现其他接口，增加了拓展性
 * 2.同一个线程任务对象可以封装成多个线程对象
 * 3.适合多个线程共享同一个资源
 * 4.增加程序的健壮性，实现解耦操作，代码可以被多个线程共享，代码与线程独立
 * 5.线程池可以放入实现Runnable或Callable接口的线程任务对象
 * 6.能直接获得线程任务的返回值
 * 缺点
 * 1.代码复杂
 *
 * @author fengjirong
 * @date 2020/9/23 14:58
 */
public class MyThread03{
    public static void main(String[] args) {
        Callable call = new MyThread03demo();
        //初始化未来任务对象
        //因为未来任务对象实现Runnable()接口，因此可以将未来任务对象转换成线程对象
        FutureTask task = new FutureTask(call);
        Thread thread1 = new Thread(task, "线程1");
        Thread thread2 = new Thread(task, "线程2");
        thread1.start();
        thread2.start();
        try {
            //获取重新的call()方法的返回值
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class MyThread03demo implements Callable<String> {
    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName()+"---"+i);
            sum=+i;
        }
        return "sum="+sum;
    }
}
