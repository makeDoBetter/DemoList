package others.机试.h;

import java.util.Scanner;

/**
 * 密码校验
 * 密码要求:
 * 1.长度超过8位
 * 2.包括大小写字母.数字.其它符号,以上四种至少三种
 * 3.不能有相同长度大于2的子串重复
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 14:36
 * @since JDK 1.8
 */
public class H20 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String regexA_Z = ".*[A-Z].*";
            String regex_a_z = ".*[a-z].*";
            String regex_0_9 = ".*[0-9].*";
            //匹配非以上情况
            String regex = ".*[\\W]+.*";
            int flag = 0;
            boolean flag1 = false;
            System.out.println(s.matches(regex_0_9));
            if (s.length() <= 8) {
                System.out.println("NG");
                continue;
            }
            if (s.matches(regex)) {
                flag++;
            }
            if (s.matches(regex_0_9)) {
                flag++;
            }
            if (s.matches(regexA_Z)) {
                flag++;
            }
            if (s.matches(regex_a_z)) {
                flag++;
            }
            if (flag < 3) {
                System.out.println("NG");
                continue;
            }
            for (int i = 0; i < s.length() - 2; i++) {
                String s1 = s.substring(i, i + 3);
                if (s.substring(i + 3).contains(s1)) {
                    flag1 = true;
                    break;
                }
            }
            if (!flag1) {
                System.out.println("OK");
            } else {
                System.out.println("NG");
            }
        }
    }
}
