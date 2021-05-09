来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/generate-parentheses

数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。

 

示例 1：
```` java
输入：n = 3
输出：["((()))","(()())","(())()","()(())","()()()"]
示例 2：
````

输入：n = 1
````java
输出：["()"]
````

####思路：
- 1.确定状态
    - 最后一步 (添加最后一对())
    - 化成子问题 (将n-1对括号放入最后一对()内)

- 2.转移方程
    - "(" + 【i=p时所有括号的排列组合】 + ")" + 【i=q时所有括号的排列组合】
       其中 p + q = n-1，且 p q 均为非负整数。

- 3.初始条件和边界情况
    - 初始为空

- 4. 计算顺序 
    - n = 0, 1, 2...n 
````java
class Solution {
    public List<String> generateParenthesis(int n) {
        LinkedList<LinkedList<String>> result = new LinkedList<LinkedList<String>>();
        if (n == 0)
            return result.get(0);
        LinkedList<String> list0 = new LinkedList<String>();
        list0.add("");
        result.add(list0);
        LinkedList<String> list1 = new LinkedList<String>();
        list1.add("()");
        result.add(list1);
        for (int i = 2; i <= n; i++) {
            LinkedList<String> temp = new LinkedList<String>();
            for (int j = 0; j < i; j++) {
                List<String> str1 = result.get(j);
                List<String> str2 = result.get(i - 1 - j);
                for (String s1 : str1) {
                    for (String s2 : str2) {
                        String el = "(" + s1 + ")" + s2;
                        temp.add(el);
                    }
                }

            }
            result.add(temp);
        }
        return result.get(n);
    }
}
````
