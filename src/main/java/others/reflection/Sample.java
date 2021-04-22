package others.reflection;

/**
 * @author fengjirong
 * @date 2021/1/7 10:45
 */
public class Sample {
    public static final String I ="静态变量";
    public int  j = 1;
    private int k = 2;

    public Sample(int j) {
        this.j = j;
    }

    public static void method01(int i){
        System.out.println("方法1" + i);
    }

    public void method02(int i, int j){
        System.out.println("方法2" + i + j);
    }

    public void method03(){
        System.out.println("方法3");
    }
}
