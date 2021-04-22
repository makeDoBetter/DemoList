package others.thread.ThreadSafe;

/**
 * 取钱类
 *
 * @author fengjirong
 * @date 2020/9/23 16:33
 */
public class AccountDirtyRead {
    private double amount = 2000;
    private String name = "Chinese";

    public AccountDirtyRead() {
    }

    public AccountDirtyRead(double amount, String name) {
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

    public synchronized void setAccountDirtyRead(double amount, String name) {
        try {
            this.amount = amount;
            Thread.sleep(2000);
            this.name = name;
            System.out.println(Thread.currentThread().getName() + "amount=" + this.amount + "name=" + this.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getInfo(){
        System.out.println(Thread.currentThread().getName() + "amount=" + this.amount + "name=" + this.name);
    }
}
