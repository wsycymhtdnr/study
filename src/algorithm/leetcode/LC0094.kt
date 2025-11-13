package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-09 1:28
 * @description： 94.二叉树的中序遍历
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/description/
 */
val ans = mutableListOf<Int>()
fun inorderTraversal(root: TreeNode?): List<Int> {
    if (root == null) {
        return ans
    }
    inorderTraversal(root.left)
    ans.add(root.`val`)
    inorderTraversal(root.right)
    return ans
}
