package others.机试.h;

import java.util.Scanner;

/**
 * 最小公倍数 = a*b/最大公约数
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/17 14:20
 * @since JDK 1.8
 */
public class H108 {
    public static void main(String[] args) {
        H108 h108 = new H108();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(a * b / h108.gcd(a, b));
        }

    }

    /**
     * 最大公约数
     *
     * @param a 参数a
     * @param b 参数b
     * @return int
     */
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
