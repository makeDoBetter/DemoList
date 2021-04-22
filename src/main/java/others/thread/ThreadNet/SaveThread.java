package others.thread.ThreadNet;

/**
 * 取钱线程
 * @author fengjirong
 * @date 2020/9/23 21:10
 */
public class SaveThread extends Thread {
    private Account account;
    private double amount;

    public SaveThread(Account account, String name, double amount){
        super(name);
        this.account = account;
        this.amount = amount;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(3000);
                account.saveMoney(this.amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
