package others.机试.h;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 定义一个单词的“兄弟单词”为：交换该单词字母顺序，而不添加、删除、修改原有的字母就能生成的单词。
 * 兄弟单词要求和原来的单词不同。例如：ab和ba是兄弟单词。ab和ab则不是兄弟单词。
 * 现在给定你n个单词，另外再给你一个单词str，让你寻找str的兄弟单词里，字典序第k大的那个单词是什么？
 * 注意：字典中可能有重复单词。本题含有多组输入数据
 * <p>
 * 先输入单词的个数n，再输入n个单词。
 * 再输入一个单词，为待查找的单词x
 * 最后输入数字k
 * <p>
 * 输出查找到x的兄弟单词的个数m
 * 然后输出查找到的按照字典顺序排序后的第k个兄弟单词，没有符合第k个的话则不用输出。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 10:43
 * @since JDK 1.8
 */
public class H27 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int nextInt = scanner.nextInt();
            String[] arr = new String[nextInt];
            List<String> list2 = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = scanner.next();
            }
            String target = scanner.next();
            char[] taChars = target.toCharArray();
            Map<Character, Integer> map = new HashMap<>();
            //记录各个字符出现的次数
            for (char taChar : taChars) {
                if (map.containsKey(taChar)) {
                    map.put(taChar, map.get(taChar) + 1);
                } else {
                    map.put(taChar, 1);
                }
            }
            int k = scanner.nextInt();
            boolean flag;
            for (String s : arr) {
                //排除一模一样
                if (!s.equals(target)) {
                    char[] chars = s.toCharArray();
                    //长度不相等不做后续处理
                    if (chars.length == target.length()) {
                        flag = false;
                        Map<Character, Integer> map1 = new HashMap<>();
                        //各个字符分组
                        for (char aChar : chars) {
                            if (map1.containsKey(aChar)) {
                                map1.put(aChar, map1.get(aChar) + 1);
                            } else {
                                map1.put(aChar, 1);
                            }
                        }
                        for (char aChar : chars) {
                            //比较字符存在并数据量相等
                            if (map.containsKey(aChar)) {
                                if (!map.get(aChar).equals(map1.get(aChar))) {
                                    flag = true;
                                    break;
                                }
                            } else {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            list2.add(s);
                        }
                    }
                }
            }
            System.out.println(list2.size());
            if (k <= list2.size()) {
                Collections.sort(list2, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
                System.out.println(list2.get(k - 1));
            }
        }
    }
}
