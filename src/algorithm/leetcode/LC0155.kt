package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-08 2:45
 * @description： 155.最小栈
 * https://leetcode.cn/problems/min-stack/description/
 */
@Suppress("unused")
class MinStack() {
    val stack = java.util.ArrayDeque<IntArray>()

    fun push(`val`: Int) {
        val min = if (stack.isEmpty()) `val` else `val`.coerceAtMost(getMin())
        stack.push(intArrayOf(`val`, min))
    }

    fun pop() {
        stack.pop()
    }

    fun top(): Int {
        return stack.peek()[0]
    }

    fun getMin(): Int {
        return stack.peek()[1]
    }

}