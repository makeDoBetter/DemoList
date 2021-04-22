package others.thread;

/**
 * 通过实现Runnable()接口创建线程
 * <p>
 * 1.创建自定义类实现Runnable()接口
 * 2.实现run()接口
 * 3.创建一个线程任务对象
 * 4.把线程任务对象封装成线程对象
 * 5.调用线程对象的start()方法
 * <p>
 * 优点
 * 1.线程任务类只是实现了Runnable()接口，还能实现其他接口，增加了拓展性
 * 2.同一个线程任务对象可以封装成多个线程对象
 * 3.适合多个线程共享同一个资源
 * 4.增加程序的健壮性，实现解耦操作，代码可以被多个线程共享，代码与线程独立
 * 5.线程池可以放入实现Runnable或Callable接口的线程任务对象
 *
 * @author fengjirong
 * @date 2020/9/23 14:26
 */
public class MyThread02 {
    public static void main(String[] args) {
        //线程任务类是执行业务的，非线程类
        Runnable demo = new MyThread02Demo();
        //同一个线程任务对象可以封装成多个线程对象
        Thread thread1 = new Thread(demo, "线程1");
        Thread thread2 = new Thread(demo, "线程2");

        //匿名内部类的方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "----" + i);
                }
            }
        }, "线程3").start();
        thread1.start();
        thread2.start();
    }
}

class MyThread02Demo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "----" + i);
        }
    }
}
