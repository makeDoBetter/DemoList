package others.机试.h;

import java.util.Scanner;

/**
 * •连续输入字符串，请按长度为8拆分每个字符串后输出到新的字符串数组；
 * •长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/26 16:48
 * @since JDK 1.8
 */
public class StringSplit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            StringBuilder str = new StringBuilder();
            String s = scanner.nextLine();
            str.append(s);
            int length = str.length();
            int zeroCount = 8 - length%8;
            while (zeroCount > 0){
                if (zeroCount == 8){
                    break;
                }
                str.append(0);
                zeroCount--;
            }
            String string = str.toString();
            int len = string.length();
            int from = 0;
            int to = 8;
            while (len > 0) {
                System.out.println(string.substring(from, to));
                len = len - 8;
                from += 8;
                to += 8;
            }
        }
    }
}
