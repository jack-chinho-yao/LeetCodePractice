package leetcode2026.p20validparentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class P20ValidParenthesesOptimizedSolution {
    public boolean isValid(String s) {


        Deque<Character> stack1 = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '('){
                stack1.push(')');
            }else if (c  ==  '['){
                stack1.push(']');
            }else if (c == '{'){
                stack1.push('}');
            }else if (stack1.isEmpty() || stack1.pop() != c){
                return false;
            }
        }
        return stack1.isEmpty();
    }

    public static void main(String[] args) {
        P20ValidParenthesesOptimizedSolution sol = new P20ValidParenthesesOptimizedSolution();
        System.out.println(sol.isValid("["));

    }
}

/*
 * Real-world use cases:
 * 1. Code editors / IDEs — check if brackets are properly closed in source code (syntax validation)
 * 2. Compilers / interpreters — validate parentheses, braces, brackets before parsing
 * 3. HTML/XML parsers — check if tags are properly nested and closed
 * 4. Math expression evaluators — validate expressions like "((1+2)*(3+4))"
 *
 * Approach: Stack-based
 * - When encountering an open bracket, push the expected closing bracket onto the stack
 * - When encountering a close bracket, pop and check if it matches
 * - This trick of pushing the expected close bracket (instead of the open bracket) simplifies the comparison
 *
 * Time: O(n), Space: O(n)
 */
