package others.机试.h;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 将一个英文语句以单词为单位逆序排放。例如“I am a boy”，逆序排放后为“boy a am I”
 * 所有单词之间用一个空格隔开，语句中除了英文字母外，不再包含其他字符
 *
 * 使用链表
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 10:33
 * @since JDK 1.8
 */
public class H13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] arr = s.split(" ");
            StringBuilder builder = new StringBuilder();
            LinkedList<String> list = new LinkedList<>();
            for (int i = 0; i < arr.length; i++) {
                list.addFirst(arr[i]);
            }
            while (list.size() > 0) {
                String last = list.pollFirst();
                if (last != null) {
                    builder.append(last + " ");
                }
            }
            System.out.println(builder);
        }
    }
}
