package others.机试.h;

import java.util.Scanner;

/**
 * 原理：ip地址的每段可以看成是一个0-255的整数，把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成
 * 一个长整数。
 * 举例：一个ip地址为10.0.3.193
 * 每段数字             相对应的二进制数
 * 10                   00001010
 * 0                    00000000
 * 3                    00000011
 * 193                  11000001
 * <p>
 * 组合起来即为：00001010 00000000 00000011 11000001,转换为10进制数就是：167773121，即该IP地址转换后的数字就是它了。
 * <p>
 * 本题含有多组输入用例，每组用例需要你将一个ip地址转换为整数、将一个整数转换为ip地址
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/8 10:04
 * @since JDK 1.8
 */
public class H33 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String ip = scanner.next();
            String theInt = scanner.next();
            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            StringBuilder builder3 = new StringBuilder();
            //ip转整数
            String[] split = ip.split("[\\D]+");
            for (String s : split) {
                String string = Integer.toBinaryString(Integer.parseInt(s));
                int l = string.length() % 8;
                if (l > 0) {
                    for (int i = 0; i < (8 - l); i++) {
                        builder1.append(0);
                    }
                }
                builder1.append(string);
            }
            long parseInt = Long.parseLong(builder1.toString(), 2);
            //整数转ip
            String str = Long.toBinaryString(Long.parseLong(theInt));
            int l = str.length() % 8;
            if (l > 0) {
                for (int i = 0; i < (8 - l); i++) {
                    builder2.append(0);
                }
            }
            String str1 = builder2.append(str).toString();
            for (int i = 0; i <= str1.length() - 8; i += 8) {
                builder3.append(Integer.parseInt(str1.substring(i, i + 8), 2) + ".");
            }
            System.out.println(parseInt);
            System.out.println(builder3.substring(0, builder3.length() - 1));
        }
    }
}
