package others.机试.h;

import java.util.Scanner;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/12 16:35
 * @since JDK 1.8
 */
public class H75 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String nextLine1 = scanner.nextLine();

            String lStr = nextLine.length() > nextLine1.length() ? nextLine : nextLine1;
            String sStr = nextLine.length() < nextLine1.length() ? nextLine : nextLine1;

            int max = 0;
            for (int i = 0; i < sStr.length(); i++) {
                for (int j = i+1; j <= sStr.length(); j++) {
                    String substring = sStr.substring(i, j);
                    if (lStr.contains(substring)) {
                        max = max > substring.length() ? max : substring.length();
                    }
                }
            }
            System.out.println(max);
        }
    }
}
