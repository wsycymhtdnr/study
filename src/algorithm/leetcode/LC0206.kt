package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-08 0:17
 * @description： 206.反转链表
 * https://leetcode.cn/problems/reverse-linked-list/description/
 */
@Suppress("unused")
fun reverseList(head: ListNode?): ListNode? {
    var prev: ListNode? = null
    var cur = head
    while (cur!= null) {
        val next = cur.next
        cur.next = prev
        prev = cur
        cur = next
    }
    return prev
}
