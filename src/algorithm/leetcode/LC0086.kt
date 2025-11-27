package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 86.分隔链表
 * https://leetcode.cn/problems/partition-list/description/
 */
@Suppress("unused")
class LC0086 {
    fun partition(head: ListNode?, x: Int): ListNode? {
        val leftDummy = ListNode(0)
        var left = leftDummy
        val rightDummy = ListNode(0)
        var right = rightDummy
        var cur = head

        while (cur != null) {
            if (cur.`val` < x) {
                left.next = cur
                left = left.next!!
            } else {
                right.next = cur
                right = right.next!!
            }
            cur = cur.next
        }

        left.next = rightDummy.next
        right.next = null
        return leftDummy.next
    }
}
