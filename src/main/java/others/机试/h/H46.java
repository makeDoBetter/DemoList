package others.机试.h;

import java.util.Scanner;

/**
 * 1.输入待截取的字符串
 * 2.输入一个正整数k，代表截取的长度
 *
 * 输出：截取后的字符串
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/10 22:42
 * @since JDK 1.8
 */
public class H46 {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String s = sc.nextLine();
            StringBuilder builder = new StringBuilder();
            int len = Integer.parseInt(sc.nextLine());
            String[] arr = new String[s.length()];
            for (int i = 0; i < s.length(); i++) {
                arr[i] = String.valueOf(s.charAt(i));
            }
            for (String s1 : arr) {
                int length = s1.getBytes("GBK").length;
                if (len>=length){
                    builder.append(s1);
                    len--;
                }else {
                    break;
                }
            }
            System.out.println(builder);
        }
    }
}
