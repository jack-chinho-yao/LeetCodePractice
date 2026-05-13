package leetcode2026.p20validparentheses;

import java.util.ArrayDeque;
import java.util.Deque;

public class P20ValidParenthesesOptimizedSolutionPractice {
    public boolean isValid(String s) {
        Deque<Character> deque = new ArrayDeque<>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '('){
                deque.push(')');
            }else if(c == '['){
                deque.push(']');
            }else if(c == '{'){
                deque.push('}');
            }else if(deque.isEmpty() || deque.pop() != c){
                return false;
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        P20ValidParenthesesOptimizedSolutionPractice sol = new P20ValidParenthesesOptimizedSolutionPractice();
        System.out.println(sol.isValid("["));

//        // Example 1: simple pair, expected = true
//        System.out.println(sol.isValid("()"));
//        // Example 2: all three types, expected = true
//        System.out.println(sol.isValid("()[]{}"));
//        // Example 3: mismatched types, expected = false
//        System.out.println(sol.isValid("(]"));
//        // Example 4: nested, expected = true
//        System.out.println(sol.isValid("([])"));
//        // Example 5: deep nesting, expected = true
//        System.out.println(sol.isValid("{[()]}"));
//        // Example 6: interleaved (wrong order), expected = false
//        System.out.println(sol.isValid("([)]"));
//        // Example 7: single open bracket, expected = false
//        System.out.println(sol.isValid("("));
//        // Example 8: single close bracket, expected = false
//        System.out.println(sol.isValid(")"));
//        // Example 9: close before open, expected = false
//        System.out.println(sol.isValid("){"));
//        // Example 10: empty string, expected = true
//        System.out.println(sol.isValid(""));
//        // Example 11: only opens, expected = false
//        System.out.println(sol.isValid("((("));
//        // Example 12: only closes, expected = false
//        System.out.println(sol.isValid(")))"));
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
