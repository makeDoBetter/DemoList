package others.thread.ThreadSafe;

/**
 * @author fengjirong
 * @date 2020/10/22 11:39
 */
public class Service {

    public synchronized void service1(){
        System.out.println("service1");
        service2();
    }

    public synchronized void service2(){
        System.out.println("service2");
        service3();
    }

    public synchronized void service3(){
        System.out.println("service3");
    }
}
