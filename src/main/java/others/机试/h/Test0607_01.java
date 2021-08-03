package others.机试.h;

/**
 * 一、实现不精确二分查找：
 * 入参： b[]为已排序数组(有重复)、长度为n，a[]为待查找目标， 长度为m
 * 返回： b中a>=bi的下标i最大值（下标从0开始）;
 * 要求：算法复杂度O(mlog(n))
 * 示例
 * 当b没有小于a元素时返回-1；所有b都小于a时返回n-1；bi1,bi2都满足条件且i1<i2时，返回i2；
 * <p>
 * b=[2, 3, 8, 8, 20], n=5
 * a依次输入：1, 5, 2, 10, 5, 20, 27
 * 输出： -1, 1, 0, 3, 1, 4, 4
 * <p>
 * 比较范围
 * 二分查找 输出
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/6/7 19:35
 * @since JDK 1.8
 */
public class Test0607_01 {

    public static void main(String[] args) {
        int[] a = {2, 3, 8, 8, 20};
        int[] b = {1, 5, 2, 10, 5, 20, 27};
        for (int i : b) {
            System.out.print(find(a, 0, 4, i) + " ");
        }

    }

    /**
     * 二分查找
     *
     * @param arr arr
     * @param i 下界
     * @param j  上界
     * @param target 对象
     * @return int int
     */
    private static int find(int[] arr, int i, int j, int target) {
        if (target > arr[j]) {
            return j;
        } else if (target < arr[i]) {
            return i - 1;
        } else if (target == arr[j]) {
            return j;
        } else if (target == arr[i]) {
            return i;
        } else {
            if (i >= j) {
                return i - 1;
            } else {
                if (arr[(i + j) / 2] > target) {
                    return find(arr, i, (i + j) / 2 - 1, target);
                } else if (arr[(i + j) / 2] < target) {
                    return find(arr, (i + j) / 2 + 1, j, target);
                } else {
                    return find(arr, (i + j) / 2, j, target);
                }
            }

        }
    }
}
