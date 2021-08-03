package others.机试.h;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 数据表记录包含表索引和数值（int范围的正整数），请对表索引相同的记录进行合并，
 * 即将相同索引的数值进行求和运算，输出按照key值升序进行输出。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 9:42
 * @since JDK 1.8
 */
public class H08 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int i = scanner.nextInt();
            Map<Integer, Integer> map = new TreeMap<>();
            for (int j = 0; j < i; j++) {
                int index = scanner.nextInt();
                int value = scanner.nextInt();
                if (map.containsKey(index)) {
                    map.put(index, map.get(index) + value);
                } else {
                    map.put(index, value);
                }
            }
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
