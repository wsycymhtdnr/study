package algorithm.leetcode

import java.util.Arrays

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 15.三数之和
 * https://leetcode.cn/problems/3sum/description/
 */
@Suppress("unused")
class LC0015 {
    fun threeSum(nums: IntArray): List<List<Int>> {
        Arrays.sort(nums)
        val ans: MutableList<List<Int>> = ArrayList()
        val n = nums.size
        for (i in 0 until n - 2) {
            val x = nums[i]
            if (i > 0 && x == nums[i - 1]) continue
            if (x + nums[i + 1] + nums[i + 2] > 0) break
            if (x + nums[n - 2] + nums[n - 1] < 0) continue

            var l = i + 1
            var r = n - 1
            while (l < r) {
                val s = x + nums[l] + nums[r]
                if (s > 0) {
                    r--
                } else if (s < 0) {
                    l++
                } else {
                    ans.add(listOf(x, nums[l], nums[r]))
                    l++
                    r--

                    while (l < r && nums[l] == nums[l - 1]) {
                        l++
                    }

                    while (r > l && nums[r] == nums[r + 1]) {
                        r--
                    }
                }
            }
        }
        return ans
    }
}