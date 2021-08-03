package others.机试.h;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/10 21:32
 * @since JDK 1.8
 */
public class H45 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int N = scan.nextInt();
            String[] str = new String[N];
            for (int i = 0; i < N; i++) {
                str[i] = scan.next().toLowerCase();
            }
            for (int i = 0; i < N; i++) {
                int[] count = new int[26];
                int len = str[i].length();
                for (int j = 0; j < len; j++) {
                    int c = str[i].charAt(j);
                    count[c - 'a']++;
                }
                Arrays.sort(count);
                int max = 0;
                int weight = 26;
                for (int k = count.length - 1; k >= 0; k--) {
                    if (count[k] != 0) {
                        max += weight * count[k];
                        weight--;
                    }
                }
                System.out.println(max);
            }
        }
        scan.close();
    }
}
