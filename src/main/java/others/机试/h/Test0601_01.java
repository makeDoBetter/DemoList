package others.机试.h;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *  
 *
 * 提示：
 *
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * 只会存在一个有效答案
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/6/1 19:51
 * @since JDK 1.8
 */
public class Test0601_01 {
    public static void main(String[] args) {
        int[] ints = doMain(new int[]{1,2,3,4,5,6,33,15,232,44}, 45);
        for (int item : ints) {
            System.out.print(item + " ");
        }
    }

    private static int[] doMain(int[] arr, int target) {
        int[] array = new int[2];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                sum = arr[i] + arr[j];
                if (sum == target){
                    array[0] = i;
                    array[1] = j;
                }
            }
        }
        return array;
    }
}
