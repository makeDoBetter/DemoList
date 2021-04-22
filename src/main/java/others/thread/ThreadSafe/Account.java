package others.thread.ThreadSafe;

/**
 * 取钱类
 *
 * @author fengjirong
 * @date 2020/9/23 16:33
 */
public class Account {
    private double amount;
    private String name;

    public Account() {
    }

    public Account(double amount, String name) {
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
     * 通过同步代码块的方式实现线程同步
     *
     * @param amount
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 17:26
     */
    public void doAmount01(double amount) {
        String name = Thread.currentThread().getName();
        synchronized (this){
            if (this.amount >= amount) {
                System.out.println(name + "取钱" + amount);
                this.amount -= amount;
                System.out.println(name + "余额为"+this.amount);
            }else {
                System.out.println(name + "余额不足，余额为"+this.amount);
            }
        }
    }

    /**
     * 通过同步方法实现线程同步
     *
     * @param amount
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 17:27
     */
    public synchronized void doAmount02(double amount) {
        String name = Thread.currentThread().getName();
        if (this.amount >= amount) {
            System.out.println(name + "取钱" + amount);
            this.amount -= amount;
            System.out.println(name + "余额为"+this.amount);
        }else {
            System.out.println(name + "余额不足，余额为"+this.amount);
        }
    }
}
