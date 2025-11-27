package algorithm.base

/**
 * @author     ： liyunfei
 * @date       ： 2025/11/16 02:09
 * @description：
 */
@Suppress("unused")
class InsertSort {
    fun insertSort(nums: IntArray): IntArray {
        val size = nums.size
        for (i in 1..<size) {
            val tmp = nums[i]
            var j = i
            while (j > 0 && nums[j - 1] > tmp) {
                nums[j] = nums[j - 1]
                j--
            }
            nums[j] = tmp // 插入
        }
        return nums
    }
}