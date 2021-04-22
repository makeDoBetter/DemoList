package others.thread.ThreadNet;

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
     * 通过同步方法实现取钱操作，如果没有钱，等待爸爸们存钱
     *
     * @param amount
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 17:27
     */
    public synchronized void drawMoney(double amount) {
        String name = Thread.currentThread().getName();
        try {
            if (this.amount >= amount) {
                this.amount -= amount;
                System.out.println(name + "取钱成功，取钱金额为" + amount+"剩余金额为"+this.amount);
                //唤醒其他等待的线程
                this.notifyAll();
                //将当前的线程修改为等待状态
                this.wait();
            }else {
               this.notifyAll();
               this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过同步方法实现取钱操作，如果没有钱，等待爸爸们存钱
     *
     * @param amount
     * @return
     * @Author fengjirong
     * @Date   2020/9/23 17:27
     */
    public synchronized void saveMoney(double amount) {
        String name = Thread.currentThread().getName();
        try {
            if (this.amount > 0) {
                this.notifyAll();
                this.wait();
            }else {
                this.amount += amount;
                System.out.println(name+"存钱成功，存入金额为"+amount+"剩余金额为"+this.amount);
                this.notifyAll();
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
