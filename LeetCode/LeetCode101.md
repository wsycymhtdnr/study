来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/symmetric-tree

给定一个二叉树，检查它是否是镜像对称的。

 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
```` java
    1
   / \
  2   2
 / \ / \
3  4 4  3
````
 

但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
```` java
    1
   / \
  2   2
   \   \
   3    3
````

这题可以用递归的思路来作答
递归必须要有三个要素：
①、边界条件   
1.左节点为空，右节点不为空，不对称，return false
2.左不为空，右为空，不对称 return false
3.左右都为空，对称，返回true

②、递归函数功能
左右两个节点为根的子树是否对称
③、等价关系式
1.根节点是否相等
2.左节点的左孩子为根的子树是否和右节点的右孩子为根的子树对称 
3.左节点的右孩子为根的子树是否和右节点的左孩子为根的子树

```` java
**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left; 
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
     public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }
}
````
