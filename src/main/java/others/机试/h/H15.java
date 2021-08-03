package others.机试.h;

import java.util.Scanner;

/**
 * 输入一个int型的正整数，计算出该int型数据在内存中存储时1的个数。
 * 转成2进制字符串再看1的数量
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 10:57
 * @since JDK 1.8
 */
public class H15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()){
            int nextInt = scanner.nextInt();
            String string = Integer.toBinaryString(nextInt);
            char[] array = string.toCharArray();
            int count = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] == '1'){
                    count++;
                }
            }
            System.out.println(count);
        }
    }
}
