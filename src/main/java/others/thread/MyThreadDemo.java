package others.thread;

/**
 * CPU 处理一个现有线程集的顺序是不确定的
 *
 * 线程的作用：
 * 1.可以提高程序的效率，线程也支持并发性，可以更多机会得到CPU
 * 2.多线程可以解决很多业务模型
 * 3.大型高并发技术的核心技术
 *
 * 线程构造方法是由主线程调用的
 * sleep():休眠Thread.currentThread()指定时间，不会失去抢占的cpu，也不会失去同步锁
 * getId()：线程唯一标识，创建线程时获得，线程生命周期间不变，线程停止后可复用
 * @author fengjirong
 * @date 2020/9/21 20:06
 */
public class MyThreadDemo {
    public static void main(String[] args) {
        MyThread01 th1 = new MyThread01("线程1");
        MyThread01 th2 = new MyThread01("线程2");
        //Thread()构造方法参数可以是Runnable()对象，也可以是Thread()对象，因为Thread实现了Runnable接口
        //这样可以将当前线程run()方法交给其他线程处理
        Thread th3 = new Thread(th1, "线程3");
        //start()方法启动线程，,它的作用是对
        //线程进行特殊的初始化，然后由JVM运行此线程的run()方法
        // 直接运行run()方法不会启动多线程
        //运行结果与代码次序没有关系，执行start()方法的顺序不等于代码次序
        //设置当前线程为守护线程
        //通过 setDaemon(true)来设置线程为“守护线程”；将一个用户线程设置为守护线程的方式是在 线程对象创建 之前
        // 用线程对象的 setDaemon 方法。
        //th1.setDaemon(true);
        th1.start();
        th2.start();
        th3.start();
        //isAlive() 线程是否处于活动状态，即线程已经启动还没有关闭
        System.out.println(th1.getName()+th1.isAlive());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(th1.getName()+th1.isAlive());
        //获得线程唯一标识
        System.out.println(th1.getName()+th1.getId());
        System.out.println(th2.getName()+th2.getId());
        System.out.println(th3.getName()+th3.getId());
        //interrupt()：中断当前线程
        Thread.currentThread().interrupt();
        //Thread.interrupted() 获得当前线程的中断状态，同时去除当前线程的中断状态，因此第二次调用返回false
        //详细看官方文档
        System.out.println("is interrupt：" + Thread.interrupted());
        System.out.println("is interrupt：" + Thread.interrupted());
        Thread.currentThread().interrupt();
        //isInterrupted()获得当前线程的中断状态，但是不会清除中断状态，因此两次调用都是
        System.out.println("is interrupt2: " + Thread.currentThread().isInterrupted());
        System.out.println("is interrupt2: " + Thread.currentThread().isInterrupted());
    }
}
