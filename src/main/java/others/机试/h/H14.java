package others.机试.h;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 给定n个字符串，请对n个字符串按照字典序排列。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 10:46
 * @since JDK 1.8
 */
public class H14 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int length = scanner.nextInt();
            String[] arr = new String[length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = scanner.next();
            }
            Arrays.sort(arr);
            for (int i = 0; i < length; i++) {
                System.out.println(arr[i]);
            }
        }
    }
}
