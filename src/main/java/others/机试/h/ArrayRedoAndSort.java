package others.机试.h;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 输入数据，“去重”与“排序”
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/4/26 11:29
 * @since JDK 1.8
 */
public class ArrayRedoAndSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> copy = new ArrayList<>();

        while (scanner.hasNext()){
            int length = scanner.nextInt();
            int[] arr = new int[length];
            for (int k = 0; k < length; k++){
                arr[k] = scanner.nextInt();
            }
            Arrays.sort(arr);
            for (int i : arr) {
                if (copy.indexOf(i) == -1){
                    copy.add(i);
                }
            }
            copy.forEach(integer -> System.out.println(integer));
            copy.clear();
        }

    }
}
