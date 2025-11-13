package algorithm.leetcode

/**
 * @author ： liyunfei
 * @date ： 2025-11-09 1:54
 * @description： 543.二叉树的直径
 * https://leetcode.cn/problems/diameter-of-binary-tree/description/
 */
var diameter = 0
@Suppress("unused")
fun diameterOfBinaryTree(root: TreeNode?): Int {
    dfs(root)
    return diameter
}

fun dfs(root: TreeNode?): Int {
    if (root == null) return 0
    val leftDepth = dfs(root.left)
    val rightDepth = dfs(root.right)
    diameter = diameter.coerceAtLeast(leftDepth + rightDepth)
    return leftDepth.coerceAtLeast(rightDepth) + 1
}