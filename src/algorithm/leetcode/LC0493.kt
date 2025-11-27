package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 493.翻转对
 * https://leetcode.cn/problems/reverse-pairs/description/
 */
@Suppress("unused")
class LC0493 {
    private lateinit var tmp: IntArray

    fun reversePairs(nums: IntArray): Int {
        tmp = IntArray(nums.size)
        return divide(nums, 0, nums.lastIndex)
    }

    private fun divide(nums: IntArray, l: Int, r: Int): Int {
        if (l == r) return 0
        val mid = l + (r - l) / 2
        return divide(nums, l, mid) +
                divide(nums, mid + 1, r) +
                merge(nums, l, mid, r)
    }

    private fun merge(nums: IntArray, l: Int, mid: Int, r: Int): Int {
        var p = l
        var q = mid + 1
        var index = l
        var ans = 0

        // 统计翻转对（利用左右区间已有序的性质）
        while (p <= mid) {
            while (q <= r && nums[p].toLong() > 2L * nums[q]) {
                q++
            }
            ans += q - (mid + 1)
            p++
        }

        // 归并排序
        p = l
        q = mid + 1
        while (p <= mid && q <= r) {
            if (nums[p] <= nums[q]) {
                tmp[index++] = nums[p++]
            } else {
                tmp[index++] = nums[q++]
            }
        }

        while (p <= mid) tmp[index++] = nums[p++]
        while (q <= r) tmp[index++] = nums[q++]

        for (i in l..r) {
            nums[i] = tmp[i]
        }
        return ans
    }
}