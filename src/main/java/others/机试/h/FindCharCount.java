package others.机试.h;

import java.util.Scanner;

/**
 * 不区分大小写，得到输入字符中指定字符的数量
 * 写出一个程序，接受一个由字母、数字和空格组成的字符串，和一个字母，
 * 然后输出输入字符串中该字母的出现次数。不区分大小写，字符串长度小于500。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/26 9:19
 * @since JDK 1.8
 */
public class FindCharCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().toUpperCase();
        String t = scanner.nextLine().toUpperCase();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(0)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
