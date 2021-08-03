package others.机试.h;


import java.util.Scanner;

/**
 * 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/26 17:16
 * @since JDK 1.8
 */
public class To16_10translation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();
            if(str.contains("0x")){
                //0x开头的处理
                System.out.println(Integer.valueOf(str.substring(2),16).toString());
            } else {
                System.out.println(Integer.valueOf(str,16).toString());
            }
        }
    }
}
