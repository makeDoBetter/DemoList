package others.机试.leetcode;

import java.util.Scanner;

/**
 * 在计算汉明距离时，我们考虑的是同一比特位上的值是否不同，而不同比特位之间是互不影响的。
 * 对于数组 {nums}nums 中的某个元素 {val}val，若其二进制的第 i 位为 1，我们只需统计 {nums}nums 中有多少元素的第 i 位为 0，
 * 即计算出了 {val}val 与其他元素在第 i 位上的汉明距离之和。
 *
 * 具体地，若长度为 n 的数组 {nums}nums 的所有元素二进制的第 ii 位共有 c 个 1，n-cn−c 个 0，则些元素在二进制的第 ii 位上的汉明距离之和为
 * c⋅(n−c)
 *
 * 我们可以从二进制的最低位到最高位，逐位统计汉明距离。将每一位上得到的汉明距离累加即为答案。
 * 具体实现时，对于整数 \textit{val}val 二进制的第 ii 位，我们可以用代码 (val >> i) & 1 来取出其第 ii 位的值。此外，由于 10^9<2^{30}10
 * 9
 *  <2
 * 30
 *  ，我们可以直接从二进制的第 00 位枚举到第 2929 位。
 *
 * 作者：LeetCode-Solution
 * 链接：https://leetcode-cn.com/problems/total-hamming-distance/solution/yi-ming-ju-chi-zong-he-by-leetcode-solut-t0ev/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/28 10:40
 * @since JDK 1.8
 */
public class Leet477 {
    public int totalHammingDistance(int[] nums) {
        int ans = 0, n = nums.length;
        for (int i = 0; i < 30; ++i) {
            int c = 0;
            for (int val : nums) {
                c += (val >> i) & 1;
            }
            ans += c * (n - c);
        }
        return ans;
    }
}
