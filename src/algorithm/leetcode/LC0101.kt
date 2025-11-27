package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 101.对称二叉树
 * https://leetcode.cn/problems/symmetric-tree/description/
 */
@Suppress("unused")
class LC0101 {
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) return true
        return isSame(root.left, root.right)
    }

    private fun isSame(node1: TreeNode?, node2: TreeNode?): Boolean {
        if (node1 == null || node2 == null) {
            return node1 == node2
        }

        return node1.`val` == node2.`val`
                && isSame(node1.left, node2.right)
                && isSame(node1.right, node2.left)
    }
}
