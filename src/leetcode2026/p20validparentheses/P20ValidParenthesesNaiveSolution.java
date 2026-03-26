package leetcode2026.p20validparentheses;

import java.util.Stack;

public class P20ValidParenthesesNaiveSolution {
 // 5ms
    public boolean isValid(String s) {
        Stack<String> stack1 = new Stack<>();
        if (s.length() == 1) return false;
        for (int i = 0; i < s.length(); i++) {
            String substring = s.substring(i, i + 1);
            if (substring.equals("(") || substring.equals("[") || substring.equals("{")){
                stack1.push(substring);
            }else if (substring.equals(")") && !stack1.empty()  && stack1.peek().equals("(")){
                stack1.pop();
            }else if (substring.equals("]") && !stack1.empty() && stack1.peek().equals("[")){
                stack1.pop();
            }else if (substring.equals("}") && !stack1.empty() && stack1.peek().equals("{")){
                stack1.pop();
            }else {
                return false;
            }
        }
        return stack1.empty();
    }

    public static void main(String[] args) {
        P20ValidParenthesesNaiveSolution sol = new P20ValidParenthesesNaiveSolution();

        // Example 1: s = "()", expected = true
//        System.out.println(sol.isValid("()"));
        System.out.println("()".length());
        System.out.println("()".substring(0, 1));
        System.out.println("()".substring(0, 2));
        System.out.println("=======");
        for (int i = 0; i < "()".length(); i++) {
            System.out.println(i);
        }
        // Example 2: s = "()[]{}", expected = true
        System.out.println(sol.isValid("){"));
        System.out.println(sol.isValid("()[]{}"));
        // Example 3: s = "(]", expected = false
        System.out.println(sol.isValid("(]"));
        // Example 4: s = "([])", expected = true
        System.out.println(sol.isValid("([])"));
        // Test: nested "{[()]}", expected = true
        System.out.println(sol.isValid("{[()]}"));
        // Test: wrong order "([)]", expected = false
        System.out.println(sol.isValid("([)]"));
        // Test: single char "(", expected = false
        System.out.println(sol.isValid("("));
    }
}

