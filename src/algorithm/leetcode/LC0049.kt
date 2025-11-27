package algorithm.leetcode

/**
 * author     : liyunfei
 * date       : 2025/11/27
 * description: 49.字母异位词分组
 * https://leetcode.cn/problems/group-anagrams/description/
 */
@Suppress("unused")
class LC0049 {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        return strs.groupBy { it.toCharArray().sorted().joinToString("") }
            .values
            .toList()
    }
}