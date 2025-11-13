package algorithm.leetcode

import java.util.*

/**
 * @author ： liyunfei
 * @date ： 2025-11-09 2:04
 * @description： 102.二叉树的层序遍历
 * https://leetcode.cn/problems/binary-tree-level-order-traversal/description/
 */
@Suppress("unused")
fun levelOrder(root: TreeNode?): List<List<Int>> {
    val ans = mutableListOf<List<Int>>()
    if (root == null) return ans
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        val size = queue.size
        val level = mutableListOf<Int>()
        for (i in 0 until size) {
            val node = queue.poll()
            level.add(node.`val`)
            node.left?.let { queue.offer(it) }
            node.right?.let { queue.offer(it) }
        }
        ans.add(level)
    }
    return ans
}
