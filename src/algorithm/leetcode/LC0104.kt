package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 104.二叉树的最大深度
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/
 */
@Suppress("unused")
class LC0104 {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        return maxDepth(root.left).coerceAtLeast(maxDepth(root.right)) + 1
    }
}
