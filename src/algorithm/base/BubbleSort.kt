package algorithm.base

/**
 * @author     ： liyunfei
 * @date       ： 2025/11/16 01:27
 * @description：
 */
@Suppress("unused")
class BubbleSort {
    fun bubbleSort(nums: IntArray): IntArray {
        val size = nums.size
        for (i in 0..<size - 1) {
            var swapped = false
            for (j in 0..<size - 1 - i) {
                if (nums[j] > nums[j + 1]) {
                    swapped = true
                    nums.swap(j, j + 1)
                }
            }
            if (!swapped) break
        }
        return nums
    }

    fun IntArray.swap(a: Int, b: Int) {
        val tmp = this[a]
        this[a] = this[b]
        this[b] = tmp
    }
}