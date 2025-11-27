package algorithm.base

/**
 * @author     ： liyunfei
 * @date       ： 2025/11/16 02:07
 * @description：
 */
@Suppress("unused")
class MergeSort {
    lateinit var tmp: IntArray

    fun mergeSort(nums: IntArray): IntArray {
        tmp = IntArray(nums.size)
        divide(nums, 0, nums.lastIndex)
        return nums
    }

    private fun divide(nums: IntArray, l: Int, r: Int) {
        if (l == r) return
        val mid = l + (r - l) / 2
        divide(nums, l, mid)
        divide(nums, mid + 1, r)
        merge(nums, l, mid, r)
    }

    private fun merge(nums: IntArray, l: Int, mid: Int, r: Int) {
        var p = l
        var q = mid + 1
        var index = l
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
    }
}