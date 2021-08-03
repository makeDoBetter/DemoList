package others.机试.h;

import java.util.Scanner;

/**
 * 1、对输入的字符串进行加解密，并输出。
 * <p>
 * 2、加密方法为：
 * 当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；
 * 当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
 * 其他字符不做变化。
 * <p>
 * 3、解密方法为加密的逆过程。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 14:16
 * @since JDK 1.8
 */
public class H29 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String in = scanner.next();
            String out = scanner.next();
            System.out.println(processOn(in));
            System.out.println(processOff(out));
        }
    }

    private static String processOn(String string) {
        StringBuilder builder = new StringBuilder();
        char[] array = string.toCharArray();
        for (char c : array) {
            if (c > 'Z' && c < 'z') {
                builder.append((char) (c - 31));
                continue;
            }
            if (c >= 'A' && c < 'Z') {
                builder.append((char) (c + 33));
                continue;
            }
            if (c == 'Z') {
                builder.append('a');
                continue;
            }
            if (c == 'z') {
                builder.append('A');
                continue;
            }
            if (c >= '0' && c < '9') {
                builder.append(Integer.parseInt(String.valueOf(c)) + 1);
                continue;
            }
            if (c == '9') {
                builder.append('0');
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private static String processOff(String string) {
        StringBuilder builder = new StringBuilder();
        char[] array = string.toCharArray();
        for (char c : array) {
            if (c > 'a' && c <= 'z') {
                builder.append((char) (c - 33));
                continue;
            }
            if (c > 'A' && c <= 'Z') {
                builder.append((char) (c + 31));
                continue;
            }
            if (c == 'a') {
                builder.append('Z');
                continue;
            }
            if (c == 'A') {
                builder.append('z');
                continue;
            }
            if (c > '0' && c <= '9') {
                builder.append(Integer.parseInt(String.valueOf(c)) - 1);
                continue;
            }
            if (c == '0') {
                builder.append('9');
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
