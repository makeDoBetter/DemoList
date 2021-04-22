package others.thread.ThreadMethod;


import java.util.Random;

/**
 * 线程优先级补充：
 *
 * 线程优先级0-10一共10个等级，设置优先级时不在这个范围内都会报错
 *
 * 有三个预设优先级
 * --MIN_PRIORITY = 1
 * --NORM_PRIORITY = 5
 * --MAX_PRIORITY = 10
 *
 * 线程优先级的特性
 * 继承性：子线程将会继承父线程的优先级
 * 规则性：CPU将资源更多的给优先级高的线程
 * 随机性：优先级高的线程会优先执行run(),但是不能将优先级与执行顺序挂钩，优先级高的不一定每次先执行
 *
 *
 * @author fengjirong
 * @date 2020/10/20 16:18
 */
public class ThreadMethodTest {
    public static void main(String[] args) {
        /* MyThread01 th1 = new MyThread01();
        th1.start();*/

        /*for (int i = 0; i < 5; i++) {
            Thread02 th2 = new Thread02();
            //设置线程优先级1-10
            th2.setPriority(5);
            th2.start();
            Thread03 th3 = new Thread03();
            th3.setPriority(6);
            th3.start();
        }*/

        try {
            Thread04 th4 =new Thread04();
            th4.setPriority(Thread.NORM_PRIORITY - 4);
            th4.start();
            Thread05 th5 =new Thread05();
            th5.setPriority(Thread.NORM_PRIORITY + 4);
            th5.start();
            Thread.sleep(500);
            th4.stop();
            th5.stop();
            System.out.println("th4-->i="+th4.getI());
            System.out.println("th5-->i="+th5.getI());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread01 extends Thread{
    @Override
    public void run() {
        super.run();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            //yield():当前线程放弃CPU占用，将CPU交给其他线程，什么时候交出不确定，而且有可能刚交出就抢占回来
            Thread.yield();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "毫秒");
    }
}

class Thread02 extends Thread{
    @Override
    public void run() {
        super.run();
        long start = System.currentTimeMillis();
        long a = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 500000; j++) {
                Random random = new Random();
                random.nextInt();
                a += i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("11111111111111111111111-->耗时" + (end - start));
    }
}

class Thread03 extends Thread{
    @Override
    public void run() {
        super.run();
        long start = System.currentTimeMillis();
        long a = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 500000; j++) {
                Random random = new Random();
                random.nextInt();
                random.nextInt();
                a += i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("222222222222---》耗时" + (end - start));
    }
}

/*比较不同优先级下谁跑得快*/
class Thread04 extends Thread{
    private int i = 0;

    @Override
    public void run() {
        while (true){
            i++;
        }
    }

    public int getI() {
        return i;
    }
}

class Thread05 extends Thread{
    private int i = 0;

    @Override
    public void run() {
        while (true){
            i++;
        }
    }

    public int getI() {
        return i;
    }
}