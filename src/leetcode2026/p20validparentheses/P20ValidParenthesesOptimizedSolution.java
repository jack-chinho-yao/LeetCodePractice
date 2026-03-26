package leetcode2026.p20validparentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class P20ValidParenthesesOptimizedSolution {
    public boolean isValid(String s) {
        Deque<Character> d = new ArrayDeque<>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (c == '('){
                d.push(')');
            }else if (c == '['){
                d.push(']');
            }else if (c == '{'){
                d.push('}');
            }else if (d.isEmpty() || d.pop() != c){
                return false;
            }
        }
        return true;

//        Deque<Character> stack1 = new ArrayDeque<>();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (c == '('){
//                stack1.push(')');
//            }else if (c  ==  '['){
//                stack1.push(']');
//            }else if (c == '{'){
//                stack1.push('}');
//            }else if (stack1.isEmpty() || stack1.pop() != c){
//                return false;
//            }
//        }
//        return stack1.isEmpty();
    }

    public static void main(String[] args) {
        Character c = 'H';
        System.out.println(c.equals("H"));
        System.out.println(c.equals('H'));
        // Example 1: s = "()", expected = true
//        System.out.println(sol.isValid("()"));
        System.out.println("()".length());
        System.out.println("()".substring(0, 1));
        System.out.println("()".substring(0, 2));
        System.out.println("=======");
        for (int i = 0; i < "()".length(); i++) {
            System.out.println(i);
        }
//        // Example 2: s = "()[]{}", expected = true
//        System.out.println(sol.isValid("){"));
//        System.out.println(sol.isValid("()[]{}"));
//        // Example 3: s = "(]", expected = false
//        System.out.println(sol.isValid("(]"));
//        // Example 4: s = "([])", expected = true
//        System.out.println(sol.isValid("([])"));
//        // Test: nested "{[()]}", expected = true
//        System.out.println(sol.isValid("{[()]}"));
//        // Test: wrong order "([)]", expected = false
//        System.out.println(sol.isValid("([)]"));
//        // Test: single char "(", expected = false
//        System.out.println(sol.isValid("("));
    }
}
