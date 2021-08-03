package others.机试.h;


import java.util.LinkedList;
import java.util.Scanner;

/**
 * 对字符串中的所有单词进行倒排。
 * <p>
 * 说明：
 * 1、构成单词的字符只有26个大写或小写英文字母；
 * 2、非构成单词的字符均视为单词间隔符；
 * 3、要求倒排后的单词间隔符以一个空格表示；如果原字符串中相邻单词间有多个间隔符时，倒排转换后也只允许出现一个空格间隔符；
 * 4、每个单词最长20个字母；
 * <p>
 * 输入：I am a student
 * 输出：student a am I
 * <p>
 * 使用链表
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 18:05
 * @since JDK 1.8
 */
public class H31 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            LinkedList<String> linkedList = new LinkedList<>();
            StringBuilder builder = new StringBuilder();
            StringBuilder builder1 = new StringBuilder();
            char[] array = s.toCharArray();
            int i = 1;
            for (char c : array) {
                if (Character.isLetter(c)) {
                    builder1.append(c);
                    if (i == array.length) {
                        linkedList.addFirst(builder1.toString());
                    }
                } else {
                    if (builder1.length() != 0) {
                        linkedList.addFirst(builder1.toString());
                        builder1.delete(0, builder1.length());
                    }
                }
                i++;
            }
            while (!linkedList.isEmpty()) {
                builder.append(linkedList.pollFirst() + " ");
            }
            System.out.println(builder.toString().trim());
        }
    }
}
