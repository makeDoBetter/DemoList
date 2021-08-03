package others.机试.h;

import java.util.Scanner;

/**
 * 输入一个整数，将这个整数以字符串的形式逆序输出
 * 程序不考虑负数的情况，若数字含有0，则逆序形式也含有0，如输入为100，则输出为001
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 10:26
 * @since JDK 1.8
 */
public class H12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String left = scanner.nextLine();
            char[] array = left.toCharArray();
            StringBuilder right = new StringBuilder();
            for (int i = array.length - 1; i >= 0; i--) {
                right.append(array[i]);
            }
            System.out.println(right);
        }
    }
}
