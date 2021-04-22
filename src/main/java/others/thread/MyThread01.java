package others.thread;

/**
 * 继承Thread的方法实现多线程
 * 需要重新run() 方法
 *
 * 创建多线程方法1
 * 1.自定义线程类继承Thread类
 * 2.重写run()方法
 * 3.初始化自定义类对象
 * 4.调用start()方法
 *
 * 优点
 * 编码简单
 * 缺点
 * 局限性，单继承不能继承其他类，降低拓展性
 *
 * @author fengjirong
 * @date 2020/9/21 20:04
 */
public class MyThread01 extends Thread {

    /**
     * description: 通过有参构造器创建带名字的线程
     *
     * @param name
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 14:25
     */
    public MyThread01(String name){
        super(name);
        System.out.println("线程构造方法："+Thread.currentThread().getName());
        System.out.println("线程构造方法this："+this.getName()+"isAlive="+this.isAlive());
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}
