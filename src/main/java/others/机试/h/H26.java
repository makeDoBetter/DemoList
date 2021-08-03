package others.机试.h;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * 编写一个程序，将输入字符串中的字符按如下规则排序。
 * 规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
 * 如，输入： Type 输出： epTy
 *
 * 规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
 * 如，输入： BabA 输出： aABb
 *
 * 规则 3 ：非英文字母的其它字符保持原来的位置。
 * 如，输入： By?e 输出： Be?y
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 10:10
 * @since JDK 1.8
 */
public class H26 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] array = s.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            int index = 0;
            List<Character> list = new ArrayList<>();
            for (char c : array) {
                if (Character.isLetter(c)) {
                    list.add(c);
                }
            }
            Collections.sort(list, new Comparator<Character>() {
                @Override
                public int compare(Character o1, Character o2) {
                    return Character.toLowerCase(o1) - Character.toLowerCase(o2);
                }
            });
            for (char c : array) {
                if (Character.isLetter(c) && index < list.size()) {
                    stringBuilder.append(list.get(index));
                    index++;
                } else {
                    stringBuilder.append(c);
                }
            }
            System.out.println(stringBuilder);
        }
    }
}

