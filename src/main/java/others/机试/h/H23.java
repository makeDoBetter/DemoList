package others.机试.h;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 实现删除字符串中出现次数最少的字符，若多个字符出现次数一样，则都删除。
 * 输出删除这些单词后的字符串，字符串中其它字符保持原来的顺序。
 * 注意每个输入文件有多组输入，即多个字符串用回车隔开
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 17:07
 * @since JDK 1.8
 */
public class H23 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] array = s.toCharArray();
            Map<String, Integer> map = new HashMap<>();
            StringBuilder builder = new StringBuilder();
            for (char i : array) {
                if (map.containsKey(String.valueOf(i))) {
                    map.put(String.valueOf(i), map.get(String.valueOf(i)) + 1);
                } else {
                    map.put(String.valueOf(i), 1);
                }
            }
            Collection<Integer> values = map.values();
            //找到最小的次数
            Integer min = Collections.min(values);
            //比较字符对应次数是否与最小次数相同，相同则跳过
            for (char item : array) {
                if (!min.equals(map.get(String.valueOf(item)))) {
                    builder.append(item);
                }
            }
            System.out.println(builder);
        }
    }
}
