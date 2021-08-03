package others.机试.h;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/23 14:37
 * @since JDK 1.8
 */
public class Test0523_01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            char[] array = next.toCharArray();
            TreeMap<Character, Integer> map = new TreeMap<>();
            for (char c : array) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
            Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Character, Integer> entry = iterator.next();
                System.out.print(entry.getKey() + "" + entry.getValue());

            }
        }
    }
}
