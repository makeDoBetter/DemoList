package others.机试.h;

import java.util.Scanner;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/18 22:10
 * @since JDK 1.8
 */
public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] arr = new int[m][n];
            int a = 0, b = 0, c = 0, d = 0;
            int z = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    arr[i][j] = scanner.nextInt();
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (arr[i][j] == 5 && z == 0) {
                        a = i;
                        b = j;
                        z++;
                    } else if (arr[i][j] == 5 && z == 1) {
                        c = i;
                        d = j;
                    }
                }
            }
            int x = c - a;

            int y = d - b;
            if (x == y) {
                if (x >= 3) {
                    System.out.println(x - 2);
                } else {
                    System.out.println(0);
                }
            } else {
                if (x > y) {
                    x = y;
                    if (x >= 3) {
                        System.out.println(x - 2);
                    } else {
                        System.out.println(0);
                    }
                }
            }
        }
    }
}
