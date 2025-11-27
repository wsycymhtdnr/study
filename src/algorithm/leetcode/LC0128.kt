package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 128.最长连续序列
 * https://leetcode.cn/problems/longest-consecutive-sequence/description/
 */
@Suppress("unused")
class LC0128 {
    fun longestConsecutive(nums: IntArray): Int {
        val set = nums.toHashSet()
        var ans = 0

        for (x in set) {
            if (x - 1 !in set) {
                var y = x
                while (y in set) y++
                ans = maxOf(ans, y - x)
            }
        }
        return ans
    }
}