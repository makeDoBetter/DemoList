package others.机试.h;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            //砝码种类
            int n = cin.nextInt();
            int[] weights = new int[n];
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                weights[i] = cin.nextInt();
            }
            for (int i = 0; i < n; i++) {
                numbers[i] = cin.nextInt();
            }
            int result = get(n, weights, numbers);
            System.out.println(result);
        }
        cin.close();
    }

    private static int get(int n, int[] w, int[] c) {
        HashSet<Integer> set = new HashSet<>();
        //处理第一种
        for (int i = 0; i <= c[0]; i++) {
            set.add(i * w[0]);
        }
        //之后每一种砝码等于前一种总和加上当前不同个数去重
        for (int i = 1; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<>(set);
            for (int j = 0; j <= c[i]; j++) {
                for (int k = 0; k < list.size(); k++) {
                    set.add(list.get(k) + j * w[i]);
                }
            }
        }
        return set.size();
    }
}