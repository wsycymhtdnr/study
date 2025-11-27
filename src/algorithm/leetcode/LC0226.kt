package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 226.翻转二叉树
 * https://leetcode.cn/problems/invert-binary-tree/description/
 */
@Suppress("unused")
class LC0226 {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null
        val left = invertTree(root.right)
        val right = invertTree(root.left)
        return root.apply {
            this.left = left
            this.right = right
        }
    }
}
