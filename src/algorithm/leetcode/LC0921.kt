package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 912.排序数组
 * https://leetcode.cn/problems/sort-an-array/description
 */
@Suppress("unused")
class LC0921 {
    private lateinit var tmp: IntArray

    fun sortArray(nums: IntArray): IntArray {
        tmp = IntArray(nums.size)
        mergeSort(nums, 0, nums.size - 1)
        return nums
    }

    private fun mergeSort(nums: IntArray, l: Int, r: Int) {
        if (l == r) return
        val mid = l + (r - l) / 2
        mergeSort(nums, l, mid)
        mergeSort(nums, mid + 1, r)

        var p = l
        var q = mid + 1
        var index = l
        while (p <= mid && q <= r) {
            tmp[index++] = if (nums[p] <= nums[q]) nums[p++] else nums[q++]
        }

        while (p <= mid) tmp[index++] = nums[p++]
        while (q <= r) tmp[index++] = nums[q++]
        System.arraycopy(tmp, l, nums, l, r - l + 1)
    }
}
