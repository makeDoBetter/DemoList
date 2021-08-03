package others.机试.h;

import java.util.BitSet;
import java.util.Scanner;

/**
 * 编写一个函数，计算字符串中含有的不同字符的个数。字符在ACSII码范围内(0~127)，
 * 换行表示结束符，不算在字符里。不在范围内的不作统计。多个相同的字符只计算一次
 * 例如，对于字符串abaca而言，有a、b、c三种不同的字符，因此输出3。
 *
 * 一定记住，去重统计可以使用BitSet
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 10:12
 * @since JDK 1.8
 */
public class H10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            BitSet bitSet = new BitSet(128);
            char[] array = s.toCharArray();
            for (int i = 0; i < array.length; i++) {
                if (!bitSet.get(array[i])){
                    bitSet.set(array[i]);
                }
            }
            System.out.println(bitSet.cardinality());
        }
    }
}
