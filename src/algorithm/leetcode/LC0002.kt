package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-08 0:37
 * @description： 2.两数相加
 * <a href="https://leetcode.cn/problems/add-two-numbers/description/"/>
 */
@Suppress("unused")
class LC0002 {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var carry = 0
        val dummy = ListNode(0)
        var cur = dummy
        var p = l1
        var q = l2

        while (p != null || q != null) {
            val sum = (p?.`val` ?: 0) + (q?.`val` ?: 0) + carry
            cur.next = ListNode(sum % 10)
            carry = sum / 10
            cur = cur.next!!
            p = p?.next
            q = q?.next
        }

        if (carry != 0) cur.next = ListNode(1)
        return dummy.next
    }
}
