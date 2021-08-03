package others.机试.h;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 根据绝对值进行比较
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/18 21:00
 * @since JDK 1.8
 */
public class Test02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int h = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] array = new int[200][n];
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            StringBuilder builder = new StringBuilder();
            for (Integer s : arr) {
                int i = s - h;
                if (i < 0) {
                    i = i * -1;
                }
                for (int j = 0; j < n; j++) {
                    if (array[i][j] == 0) {
                        array[i][j] = s;
                        break;
                    }
                }
            }
            for (int i = 0; i < 200; i++) {
                int[] ints = new int[n];
                int l = 0;
                if (array[i][0] == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if (array[i][j] > 0) {
                        ints[l] = array[i][j];
                        l++;
                    }
                }
                Arrays.sort(ints);
                for (int anInt : ints) {
                    if (anInt > 0) {
                        builder.append(anInt + " ");
                    }
                }
            }
            System.out.println(builder.toString().trim());

        }
    }
}
