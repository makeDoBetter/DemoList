package others.机试.h;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 将输入的两个字符串合并。
 * <p>
 * 对合并后的字符串进行排序，要求为：下标为奇数的字符和下标为偶数的字符分别从小到大排序。这里的下标意思是字符在字符串中的位置。
 * 对排序后的字符串进行操作，如果字符为‘0’——‘9’或者‘A’——‘F’或者‘a’——‘f’，
 * 则对他们所代表的16进制的数进行BIT倒序的操作，并转换为相应的大写字符。
 * 如字符为‘4’，为0100b，则翻转后为0010b，也就是2。转换后的字符为‘2’；
 * 如字符为‘7’，为0111b，则翻转后为1110b，也就是e。转换后的字符为大写‘E’。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 15:25
 * @since JDK 1.8
 */
public class H30 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            StringBuilder strb1 = new StringBuilder();
            String s1 = scanner.next();
            String s2 = scanner.next();
            strb1.append(s1);
            strb1.append(s2);
            char[] array = strb1.toString().toCharArray();
            char[] arr1 = new char[array.length];
            char[] arr2 = new char[array.length];
            char[] arr3 = new char[array.length];
            int j = 0;
            //奇偶拆分
            for (int i = 0; i < array.length; i++) {
                if (i % 2 == 0) {
                    arr1[j] = array[i];
                } else {
                    arr2[j] = array[i];
                }
                j++;
            }
            //排序
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            int k = 0;
            //拼接
            for (char c : arr1) {
                if (c == 0) {
                    continue;
                }
                if (k > arr3.length) {
                    break;
                }
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
                    arr3[k] = get(c);
                    k += 2;
                } else {
                    arr3[k] = c;
                    k += 2;
                }
            }
            int m = 1;
            for (char c : arr2) {
                if (c == 0) {
                    continue;
                }
                if (m > arr3.length) {
                    break;
                }
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
                    arr3[m] = get(c);
                    m += 2;
                } else {
                    arr3[m] = c;
                    m += 2;
                }
            }
            System.out.println(new String(arr3));
        }
    }

    private static char get(char c) {
        //16进制转10进制
        int i = Integer.parseInt(String.valueOf(c), 16);
        //10进制转2进制
        String str = Integer.toBinaryString(i);
        StringBuilder b = new StringBuilder();
        //不足4位添加0
        if (str.length() < 4) {
            for (int j = 0; j < 4 - str.length(); j++) {
                b.append('0');
            }
        }
        b.append(str);
        //使用StringBuilder逆序字符
        String str1 = new StringBuilder(b).reverse().toString();
        //2进制转10进制
        int anInt = Integer.parseInt(str1, 2);
        //10进制转16进制
        return Integer.toHexString(anInt).toUpperCase().toCharArray()[0];
    }
}
