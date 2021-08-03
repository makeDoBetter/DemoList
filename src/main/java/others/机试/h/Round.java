package others.机试.h;

import java.util.Scanner;

/**
 * 输入一个浮点数，进行四舍五入整形输出
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/27 9:39
 * @since JDK 1.8
 */
public class Round {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextFloat()){
            float f = scanner.nextFloat();
            System.out.println( (int)(f+ 0.5f) );
        }
    }
}
