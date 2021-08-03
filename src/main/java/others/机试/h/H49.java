package others.机试.h;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class H49 implements Runnable {

    private String name;
    private Object prev;
    private Object self;
    private int count;
    private H49(String name, Object prev, Object self, int count) {
        this.name = name;
        this.prev = prev;
        this.self = self;
        this.count = count;
    }
    @Override
    public void run() {
        // int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Object d = new Object();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int count = Integer.valueOf(br.readLine());
        H49 pa = new H49("A", d, a, count);
        H49 pb = new H49("B", a, b, count);
        H49 pc = new H49("C", b, c, count);
        H49 pd = new H49("D", c, d, count);

        new Thread(pa).start();
        Thread.sleep(100); // 确保按顺序A、B、C执行
        new Thread(pb).start();
        Thread.sleep(100);
        new Thread(pc).start();
        Thread.sleep(100);
        new Thread(pd).start();
    }
}
