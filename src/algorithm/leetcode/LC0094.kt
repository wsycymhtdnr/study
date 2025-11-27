package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 94.二叉树的中序遍历
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/description/
 */
@Suppress("unused")
class LC0094 {
    private val ans = mutableListOf<Int>()

    fun inorderTraversal(root: TreeNode?): List<Int> {
        if (root == null) {
            return ans
        }
        inorderTraversal(root.left)
        ans.add(root.`val`)
        inorderTraversal(root.right)
        return ans
    }
}
