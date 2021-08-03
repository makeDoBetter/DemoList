package demo.jvm;

/**
 * 验证栈溢出，调用需要的栈深度大于实际jvm拥有的栈深度
 *
 * 使用无返回值的递归可验证
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/17 11:39
 * @since JDK 1.8
 */
public class StackOverflowErrorTest {
    private void get(int i){
        int demo = i;
        get(demo);
    }

    public static void main(String[] args) {
        StackOverflowErrorTest test = new StackOverflowErrorTest();
        test.get(0);
    }
}
