package others.机试.h;

import java.util.Scanner;

/**
 * 大家都知道手机上的字母：
 * 1--1， abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8 wxyz--9, 0--0,
 * 就这么简单，渊子把密码中出现的小写字母都变成对应的数字，数字和其他的符号都不做变换，
 * <p>
 * 声明：密码中没有空格，而密码中出现的大写字母则变成小写之后往后移一位，
 * 如：X，先变成小写，再往后移一位，不就是y了嘛，简单吧。记住，z往后移是a哦。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 15:34
 * @since JDK 1.8
 */
public class H21 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] array = s.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char item : array) {
                if (item >= 'a' && item <= 'z') {
                    if ("abc".contains(String.valueOf(item))) {
                        builder.append(2);
                        continue;
                    }
                    if ("def".contains(String.valueOf(item))) {
                        builder.append(3);
                        continue;
                    }
                    if ("ghi".contains(String.valueOf(item))) {
                        builder.append(4);
                        continue;
                    }
                    if ("jkl".contains(String.valueOf(item))) {
                        builder.append(5);
                        continue;
                    }
                    if ("mno".contains(String.valueOf(item))) {
                        builder.append(6);
                        continue;
                    }
                    if ("pqrs".contains(String.valueOf(item))) {
                        builder.append(7);
                        continue;
                    }
                    if ("tuv".contains(String.valueOf(item))) {
                        builder.append(8);
                        continue;
                    }
                    if ("wxyz".contains(String.valueOf(item))) {
                        builder.append(9);
                    }
                    continue;
                }
                if (item >= 'A' && item < 'Z') {
                    builder.append((char) (item + 33));
                    continue;
                }
                if (item == 'Z') {
                    builder.append('a');
                    continue;
                }
                builder.append(item);
            }
            System.out.println(builder);
        }
    }
}
