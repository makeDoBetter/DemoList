package others.thread.ThreadSafe;

/**
 * @author fengjirong
 * @date 2020/10/22 11:43
 */
public class ServiceSon extends Service {
    public synchronized void printInfo(){
        System.out.println("this is son,and then print my father");
        this.service1();
    }
}
