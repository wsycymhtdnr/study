package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 641.设计循环双端队列
 * https://leetcode.cn/problems/design-circular-deque/description/
 */
@Suppress("unused")
class MyCircularDeque(k: Int) {
    val size = k + 1
    val deque = IntArray(k + 1)
    var l = 0
    var r = 0

    fun insertFront(value: Int): Boolean {
        if (isFull()) return false
        l = (l - 1 + size) % size
        deque[l] = value
        return true
    }

    fun insertLast(value: Int): Boolean {
        if (isFull()) return false
        deque[r] = value
        r = (r + 1) % size
        return true
    }

    fun deleteFront(): Boolean {
        if (isEmpty()) return false
        l = (l + 1) % size
        return true
    }

    fun deleteLast(): Boolean {
        if (isEmpty()) return false
        r = (r - 1 + size) % size
        return true
    }

    fun getFront(): Int {
        if (isEmpty()) return -1
        return deque[l]
    }

    fun getRear(): Int {
        if (isEmpty()) return -1
        return deque[(r - 1 + size) % size]
    }

    fun isEmpty(): Boolean {
        return l == r
    }

    fun isFull(): Boolean {
        return (r + 1) % size == l
    }

}
