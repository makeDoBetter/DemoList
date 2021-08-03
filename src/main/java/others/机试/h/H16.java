package others.机试.h;

import java.util.Scanner;

/**
 * 背包问题
 * 加了限制条件的背包问题
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/6 11:32
 * @since JDK 1.8
 */
public class H16 {

    /**
     * 处理背包
     * @param val the val
     * @param weight the weight
     * @param q the q
     * @param n the n
     * @param w the w
     * @return int
     */
    private static int getMaxValue(int[] val, int[] weight, int[] q, int n, int w) {
        int[][] dp = new int[n + 1][w + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                if (q[i - 1] == 0) {
                    // 主件
                    // 用j这么多钱去买 i 件商品 可以获得最大价值
                    if (weight[i - 1] <= j){
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + val[i - 1]);
                    }
                } else {
                    //附件
                    //附件的话 加上主件一起算
                    if (weight[i - 1] + weight[q[i - 1]] <= j){
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + val[i - 1]);
                    }
                }
            }
        }
        return dp[n][w];
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            // 总钱数
            int n = input.nextInt();
            // 商品个数
            int m = input.nextInt();
            int[] p = new int[m];
            int[] v = new int[m];
            int[] q = new int[m];
            for (int i = 0; i < m; i++) {
                // 价格
                p[i] = input.nextInt();
                // 价值
                v[i] = input.nextInt() * p[i];
                // 主or附件
                q[i] = input.nextInt();
            }
            System.out.println(getMaxValue(v, p, q, m, n));
        }
    }
}
