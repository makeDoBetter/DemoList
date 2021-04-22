package others.proxy;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/3/26 14:52
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        SellSup o = (SellSup)myProxy.getObject(new Sell());
        o.print();
    }
}
