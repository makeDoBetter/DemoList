package others.thread.ThreadAtomic;

import java.util.HashMap;
import java.util.Map;

/**
 * 并发包-ConcurrentHashMap
 *
 * HashMap 线程不安全，但是性能优秀
 * HashTable 线程安全，但是性能差
 * --HashTable底层对整个表添加锁，因此效率低下，因此被弃用
 * ConCurrentHashMap 线程安全，并且性能优秀
 * --ConCurrentHashMap底层使用CAS+局部锁，即并发情况下只锁定当前hash对应的桶，其他hash下的数据可以正常读写
 *
 * 因此，在不需要考虑并发的业务场景时，优先考虑使用HashMap，而需要考虑高并发情况时，使用ConCurrentHashMap能保证
 * @author fengjirong
 * @date 2020/9/24 17:24
 */
public class ConCurrentHashMapTest {
    public static void main(String[] args) {
        MyRunnable01 task = new MyRunnable01();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();

        try {
            //等待线程死亡
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("数量为=" + task.getMap().size());
    }
}

class MyRunnable01 implements Runnable{
    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500000; i++) {
            map.put(Thread.currentThread().getName()+i, Thread.currentThread().getName()+i);
        }
    }
}
