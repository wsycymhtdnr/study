package algorithm.nowcoder


/**
 * @author     ： liyunfei
 * @date       ： 2025/11/15 22:22
 * @description： NC349.计算数组的小和
 * <a href="https://www.nowcoder.com/practice/6dca0ebd48f94b4296fc11949e3a91b8?tpId=196&tqId=40415&rp=1&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D196&difficulty=undefined&judgeStatus=undefined&tags=&title=349"/>
 */
@Suppress("unused")
class NC0349 {
    private lateinit var tmp: IntArray

    fun calArray(nums: IntArray): Long {
        tmp = IntArray(nums.size)
        return divide(nums, 0, nums.lastIndex)
    }

    private fun divide(nums: IntArray, l: Int, r: Int): Long {
        if (l == r) return 0L
        val mid = (l + r) / 2
        return divide(nums, l, mid) +
                divide(nums, mid + 1, r) +
                merge(nums, l, mid, r)
    }

    private fun merge(nums: IntArray, l: Int, mid: Int, r: Int): Long {
        var p = l
        var q = mid + 1
        var index = l
        var ans = 0L
        while (p <= mid && q <= r) {
            if (nums[p] <= nums[q]) {
                ans += nums[p].toLong() * (r - q + 1)
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

fun main() {
    val arr = intArrayOf(1, 3, 4, 2, 5)
    println(NC0349().calArray(arr)) // 输出应为 16
}

