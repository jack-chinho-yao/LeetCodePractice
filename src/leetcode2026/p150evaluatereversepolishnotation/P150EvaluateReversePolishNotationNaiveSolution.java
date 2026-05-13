package leetcode2026.p150evaluatereversepolishnotation;

import java.util.Stack;
// 想得太複雜了 結果直接推回stack就好，這樣isSign變數也用不到
public class P150EvaluateReversePolishNotationNaiveSolution {
//["4","-2","/","2","-3","-","-"]
    public int evalRPN(String[] tokens) {
        Stack<String> stacks = new Stack<>();
        Integer result = null;
        Boolean isSign = false; // 連續出現符號的時候可以拿來判斷
        if (tokens.length == 1) return Integer.valueOf(tokens[0]);
        for(String token : tokens){
            if ("/".equals(token) || "+".equals(token) || "*".equals(token) || "-".equals(token)){
                if (result == null){
                    String s1 = stacks.pop();
                    String s2 = stacks.pop();
                    if("/".equals(token)){
                        result = Integer.valueOf(s2) / Integer.valueOf(s1);
                    }else if ("+".equals(token)){
                        result = Integer.valueOf(s2) + Integer.valueOf(s1);
                    }else if ("*".equals(token)){
                        result = Integer.valueOf(s2) * Integer.valueOf(s1);
                    }else if ("-".equals(token)){
                        result = Integer.valueOf(s2) - Integer.valueOf(s1);
                    }
                }else {
                    String s1 = stacks.pop();
                    if (!isSign){
                        if("/".equals(token)){
                            result = result / Integer.valueOf(s1);
                        }else if ("+".equals(token)){
                            result = result + Integer.valueOf(s1);
                        }else if ("*".equals(token)){
                            result = result * Integer.valueOf(s1);
                        }else if ("-".equals(token)){
                            result = result - Integer.valueOf(s1);
                        }

                    }else {
                        if("/".equals(token)){
                            result = Integer.valueOf(s1) / result;
                        }else if ("+".equals(token)){
                            result = Integer.valueOf(s1) + result;
                        }else if ("*".equals(token)){
                            result = Integer.valueOf(s1) * result;
                        }else if ("-".equals(token)){
                            result = Integer.valueOf(s1) - result;
                        }
                        isSign = false;
                    }
                }
                isSign = true;
            }else {
                isSign = false;
                stacks.push(token);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P150EvaluateReversePolishNotationNaiveSolution solution = new P150EvaluateReversePolishNotationNaiveSolution();
        System.out.println(3/2);
//        // Example 1: (2 + 1) * 3 = 9
//        System.out.println(solution.evalRPN(new String[]{"2", "1", "+", "3", "*"})); // Expected: 9
//
//        // Example 2: (4 + (13 / 5)) = 6
//        System.out.println(solution.evalRPN(new String[]{"4", "13", "5", "/", "+"})); // Expected: 6
//
        // Example 3: ((2 + 1) * 3) = 9, then ((10 * (6 / -132 + ...)))
//        System.out.println(solution.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"})); // Expected: 22
//        System.out.println(solution.evalRPN(new String[]{"18"})); // Expected: 22
//        System.out.println(solution.evalRPN(new String[]{"1","2","+","3","*","4","-"})); // Expected: 22
        System.out.println(solution.evalRPN(new String[]{"4","-2","/","2","-3","-","-"})); // Expected: -7
    }
}

/*
 * === Discussion Notes (V1 - Broken Attempt) ===
 *
 * Initial wrong approach:
 * - Used a separate `result` variable to accumulate computation results
 * - Only popped ONE value from the stack when encountering an operator, then combined it with `result`
 * - Used `isSign` flag to track consecutive operators
 *
 * Why it's wrong:
 * - When an operator appears, it should operate on the two most recent values on the stack,
 *   NOT on a running `result` and one stack value
 * - Example: ["4","-2","/","2","-3","-","-"]
 *   At the second "-", this approach computes (4/(-2)) - (-3) = 1, then 1 - 2 = -1
 *   But correct RPN is: pop -3 and 2 -> compute 2-(-3)=5, then pop 5 and -2 -> compute -2-5=-7
 *
 * Key lesson:
 * - The stack itself stores ALL intermediate results. No need for a separate `result` variable.
 * - When you encounter an operator: pop twice, compute, push result back. That's it.
 *
 * Also tripped up on negative numbers:
 * - "-2" is a negative number (length > 1), "-" is an operator (length == 1)
 * - Operators are always exactly one character: +, -, *, /
 *
 * See V2 for the corrected implementation.
 */
