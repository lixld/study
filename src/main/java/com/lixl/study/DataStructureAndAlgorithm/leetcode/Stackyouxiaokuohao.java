package com.lixl.study.DataStructureAndAlgorithm.leetcode;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * <p>
 * 示例 1：
 * 输入：s = "()"
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：s = "()[]{}"
 * 输出：true
 * <p>
 * 示例 3：
 * 输入：s = "(]"
 * 输出：false
 * <p>
 * 示例 4：
 * 输入：s = "([)]"
 * 输出：false
 * <p>
 * 示例 5：
 * 输入：s = "{[]}"
 * 输出：true
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 10⁴
 * s 仅由括号 '()[]{}' 组成
 * Related Topics 栈 字符串 👍 3447 👎 0
 */

public class Stackyouxiaokuohao {

    public static boolean isValid(String source) {
        char[] chars = source.toCharArray();
        Stack<Character> stack = new Stack();
        for (char aChar : chars) {
            if (aChar == '(' || aChar == '[' || aChar == '{') {
                stack.push(aChar);
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    Character peek = stack.peek();//栈顶元素
                    if (peek == '(' && aChar == ')') {
                        stack.pop();
                    } else if (peek == '{' && aChar == '}') {
                        stack.pop();
                    } else if (peek == '[' && aChar == ']') {
                        stack.pop();
                    } else {
                        return false;
                    }
                }
            }
        }

        boolean empty = stack.isEmpty();
        return empty;

    }


    public static void main(String[] args) {

//        boolean valid = isValid("{[]}");
        boolean valid = isValid("()[]{}");
        System.out.println(valid);
    }


}
