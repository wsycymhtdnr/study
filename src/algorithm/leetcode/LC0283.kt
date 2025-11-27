package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 283.移动零
 * https://leetcode.cn/problems/move-zeroes/description/
 */
@Suppress("unused")
class LC0283 {
    fun moveZeroes(nums: IntArray) {
        var slow = 0
        for (fast in nums.indices) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast]
            }
        }
        while (slow < nums.size) nums[slow++] = 0
    }
}