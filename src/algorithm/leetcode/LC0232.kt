package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-08 1:57
 * @description： 232.用栈实现队列
 * https://leetcode.cn/problems/implement-queue-using-stacks/description/
 */
@Suppress("unused")
class MyQueue {
    val inStack = java.util.ArrayDeque<Int>()
    val outStack = java.util.ArrayDeque<Int>()

    fun push(x: Int) {
        inStack.push(x)
    }

    fun pop(): Int {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop())
            }
        }
        return outStack.pop()
    }

    fun peek(): Int {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop())
            }
        }
        return outStack.peek()
    }

    fun empty(): Boolean {
        return inStack.isEmpty() && outStack.isEmpty()
    }

}
