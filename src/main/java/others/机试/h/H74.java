package others.机试.h;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/12 16:28
 * @since JDK 1.8
 */
public class H74 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();

            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<String> arrayList = new ArrayList();
            boolean flag = false;
            for (int i = 0; i < nextLine.length(); i++) {
                char c = nextLine.charAt(i);

                if (String.valueOf(c).equals("\"")) {
                    flag = flag ? false : true;
                    continue;
                }

                if (String.valueOf(c).equals(" ") && !flag) {

                    arrayList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else {
                    stringBuilder.append(c);
                }

            }
            arrayList.add(stringBuilder.toString());
            System.out.println(arrayList.size());
            for (String s : arrayList) {
                System.out.println(s);
            }
        }
    }
}
