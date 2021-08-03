package others.机试.h;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 计数，比较
 * a-A>0 ==32
 * b-A>0 !=32
 * a-b = -1
 * A-b=-33
 * A-B=-1
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/18 20:29
 * @since JDK 1.8
 */
public class Test01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            HashMap<Character, Integer> map = new HashMap<>();
            String s = scanner.next();
            char[] array = s.toCharArray();
            for (char c : array) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }

            Set<Map.Entry<Character, Integer>> set = map.entrySet();
            ArrayList<Map.Entry<Character, Integer>> list = new ArrayList<>(set);
            Iterator<Map.Entry<Character, Integer>> iterator = list.stream().sorted(new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                    int dec = o2.getValue() - o1.getValue();
                    if (dec == 0) {
                        Character key2 = o2.getKey();
                        Character key1 = o1.getKey();
                        if (key1 >= 'a' && key2 >= 'a') {
                            return key1 - key2;
                        } else if (key1 < 'a' && key2 < 'a') {
                            return key1 - key2;
                        } else {
                            return key2 - key1;
                        }
                        /*if (key2>='a'){
                            key2 = (char) (key2-32);
                        }else {
                            key2 = (char) (key2+32);
                        }
                        if (key1>='a'){
                            key1 = (char) (key1-32);
                        }else {
                            key1 = (char) (key1+32);
                        }*/
                    } else {
                        return dec;
                    }
                }
            }).collect(Collectors.toList()).iterator();
            while (iterator.hasNext()) {
                Map.Entry<Character, Integer> next = iterator.next();
                System.out.print(next.getKey() + ":" + next.getValue() + ";");
            }
        }
    }
}
