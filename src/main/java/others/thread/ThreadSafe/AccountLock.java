package others.thread.ThreadSafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 取钱类
 *
 * @author fengjirong
 * @date 2020/9/23 16:33
 */
public class AccountLock {
    private double amount;
    private String name;
    /*创建一个锁对象*/
    private final Lock lock = new ReentrantLock();

    public AccountLock() {
    }

    public AccountLock(double amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通过显示的Lock锁进行上锁解锁操作
     *
     * @param amount
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 17:26
     */
    public void doAmount(double amount) {
        String name = Thread.currentThread().getName();
        lock.lock();
        try {
            if (this.amount >= amount) {
                System.out.println(name + "取钱" + amount);
                this.amount -= amount;
                System.out.println(name + "余额为"+this.amount);
            }else {
                System.out.println(name + "余额不足，余额为"+this.amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
