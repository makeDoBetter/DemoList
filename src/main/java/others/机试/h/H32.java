package others.机试.h;

import java.util.Scanner;

/**
 * Catcher是MCA国的情报员，他工作时发现敌国会用一些对称的密码进行通信，比如像这些ABBA，ABA，A，123321，
 * 但是他们有时会在开始或结束时加入一些无关的字符以防止别国破解。
 * 比如进行下列变化 ABBA->12ABBA,ABA->ABAKK,123321->51233214　。
 * 因为截获的串太长了，而且存在多种可能的情况（abaaab可看作是aba,或baaab的加密形式），
 * Cathcer的工作量实在是太大了，他只能向电脑高手求助，你能帮Catcher找出最长的有效密码串吗？
 * <p>
 * 输入一个字符串
 * 返回有效密码串的最大长度
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/7 22:09
 * @since JDK 1.8
 */
public class H32 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] array = s.toCharArray();
            int j, k, process = 0, max = 0;
            //处理第一个字符
            if (array[0] == array[1]) {
                max = 2;
            }
            for (int i = 1; i < array.length - 1; i++) {
                //处理aBBa型
                if (array[i] == array[i + 1]) {
                    j = i;
                    k = i + 1;
                    while (j >= 0 && k < array.length && array[j] == array[k]) {
                        j--;
                        k++;
                        process += 2;
                    }
                    max = Math.max(max, process);
                    process = 0;
                }
                ///处理aBa型
                if (array[i - 1] == array[i + 1]) {
                    j = i - 1;
                    k = i + 1;
                    while (j >= 0 && k < array.length && array[j] == array[k]) {
                        j--;
                        k++;
                        process += 2;
                    }
                    process += 1;
                    max = Math.max(max, process);
                    process = 0;
                }
            }
            System.out.println(max);
        }
    }

}
