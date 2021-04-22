package others.proxy;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/3/26 14:42
 * @since JDK 1.8
 */
public class Sell implements SellSup{

    @Override
    public void print(){
        System.out.println("售卖货品");
    }
}
