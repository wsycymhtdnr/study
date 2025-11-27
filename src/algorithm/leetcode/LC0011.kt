package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description:
 */
@Suppress("unused")
class LC0011 {
    fun maxArea(height: IntArray): Int {
        var ans = 0
        var l = 0
        var r = height.lastIndex

        while (l < r) {
            if (height[l] < height[r]) {
                ans = maxOf(ans, height[l] * (r - l))
                l++
            } else {
                ans = maxOf(ans, height[r] * (r - l))
                r--
            }
        }
        return ans
    }
}