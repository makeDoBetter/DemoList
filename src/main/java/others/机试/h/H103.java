package others.机试.h;

import java.util.Scanner;

/**
 * 最长递增子序列
 * 使用动态规划 第n个递增个数由n前面的个数+1
 * 假设 1，2，3
 * 设置数组保留每个数据递增个数 如果当前大于前面的，则比较对应下标存的个数
 * 1，4，7
 * 1，2，3
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/17 15:08
 * @since JDK 1.8
 */
public class H103 {
    public static int GetResult(int m, int[] num) {
        int[] dp = new int[m];
        int max = 1;
        for (int i = 0; i < m; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (num[j] < num[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            int m = scan.nextInt();
            int[] num = new int[m];
            for (int i = 0; i < m; i++) {
                num[i] = scan.nextInt();
            }

            System.out.println(GetResult(m, num));
        }
    }
}
