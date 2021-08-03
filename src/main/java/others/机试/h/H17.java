package others.机试.h;

import java.util.Scanner;

/**
 * 坐标问题
 * 使用正则表达式进行匹配
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 11:39
 * @since JDK 1.8
 */
public class H17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String regex = "^[WASD][0-9]{1,2}$";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int x = 0;
            int y = 0;
            String[] array = line.split(";");
            for (int i = 0; i < array.length; i++) {
                if (array[i].matches(regex)) {
                    String s2 = array[i].substring(1);
                    if ('W'== (array[i].charAt(0))) {
                        y += Integer.parseInt(s2);
                    }
                    if ('S'== (array[i].charAt(0))) {
                        y -= Integer.parseInt(s2);
                    }
                    if ('A'== (array[i].charAt(0))) {
                        x -= Integer.parseInt(s2);
                    }
                    if ('D'== (array[i].charAt(0))) {
                        x += Integer.parseInt(s2);
                    }
                }
            }
            System.out.println(x + "," + y);
        }
    }
}
