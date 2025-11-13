package algorithm.leetcode

import java.util.*

/**
 * @author ： liyunfei
 * @date ： 2025-11-08 2:23
 * @description： 225.用队列实现栈
 * https://leetcode.cn/problems/implement-stack-using-queues/description/
 */
@Suppress("unused")
class MyStack() {
    private val queue = LinkedList<Int>()

    fun push(x: Int) {
        val size = queue.size
        queue.offer(x)
        for (i in 0 until size) {
            queue.offer(queue.poll())
        }
    }

    fun pop(): Int {
        return queue.poll()
    }

    fun top(): Int {
        return queue.peek()
    }

    fun empty(): Boolean {
        return queue.isEmpty()
    }

}

