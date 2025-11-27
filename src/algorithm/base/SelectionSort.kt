package algorithm.base

/**
 * @author     ： liyunfei
 * @date       ： 2025/11/16 01:46
 * @description：
 */
@Suppress("unused")
class SelectionSort {
    fun selectionSort(nums: IntArray): IntArray {
        val size = nums.size
        for (i in 0..<size - 1) {
            var minIndex = i
            for (j in i + 1..<size) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j
                }
            }
            if (minIndex != i) nums.swap(i, minIndex)
        }
        return nums
    }

    private fun IntArray.swap(a: Int, b: Int) {
        val tmp = this[a]
        this[a] = this[b]
        this[b] = tmp
    }
}