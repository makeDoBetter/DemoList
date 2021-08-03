package others.机试.h;

import java.util.Scanner;

/**
 * 输入一个int型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
 * 保证输入的整数最后一位不是0。
 *
 * 关键，int只有转成string才能转换城char数组
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 9:59
 * @since JDK 1.8
 */
public class H09 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()){
            int left = scanner.nextInt();
            String s = left + "";
            StringBuilder right = new StringBuilder();
            char[] array = s.toCharArray();
            for (int length = array.length - 1; length >= 0; length--) {
                if (right.indexOf(String.valueOf(array[length])) == -1){
                    right.append(array[length]);
                }
            }
            System.out.println(right);
        }
    }
}
