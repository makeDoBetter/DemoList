package others.机试.h;

import java.util.Scanner;

/**
 * 计算字符串最后一个单词的长度，单词以空格隔开，字符串长度小于5000。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/26 11:10
 * @since JDK 1.8
 */
public class LastWordLength {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int count = 0;
        for (int i = s.length()-1; i >= 0; i--){
            if (s.charAt(i)==' '){
                break;
            }
            count++;
        }
        System.out.println(count);
    }
}
