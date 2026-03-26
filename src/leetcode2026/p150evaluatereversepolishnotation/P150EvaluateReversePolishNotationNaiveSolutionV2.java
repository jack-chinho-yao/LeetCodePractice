package leetcode2026.p150evaluatereversepolishnotation;

import java.util.Stack;

//  你目前的時間和空間都已經是 O(n)，算法層面沒辦法再降了。
//
//  優化方向是降低常數開銷，你可以想兩個點：
//
//  1. 去掉 String 轉換：你現在每次運算都在 String → Integer → 計算 → String，來回轉。如果 stack 裡直接存 int 呢？
//  2. 去掉 Stack 類本身：Stack 底層是 Vector，每個操作都有 synchronized 鎖的開銷。你其實知道 stack 最大不會超過 tokens.length，那有沒有更輕量的東西可以當 stack 用？
//
public class P150EvaluateReversePolishNotationNaiveSolutionV2 {
//["4","-2","/","2","-3","-","-"]
    public int evalRPN(String[] tokens) {
        Stack<String> stacks = new Stack<>();
        String result = null;
        for(String token : tokens){
            if ("/".equals(token) || "+".equals(token) || "*".equals(token) || "-".equals(token)){
                String s1 = stacks.pop();
                String s2 = stacks.pop();
                if("/".equals(token)){
                    result = String.valueOf(Integer.valueOf(s2) / Integer.valueOf(s1));
                }else if ("+".equals(token)){
                    result = String.valueOf(Integer.valueOf(s2) + Integer.valueOf(s1));
                }else if ("*".equals(token)){
                    result = String.valueOf(Integer.valueOf(s2) * Integer.valueOf(s1));
                }else if ("-".equals(token)){
                    result = String.valueOf(Integer.valueOf(s2) - Integer.valueOf(s1));
                }
                stacks.push(result);
            }else {
                stacks.push(token);
            }
        }
        return Integer.valueOf(stacks.pop());
    }

    public static void main(String[] args) {
        P150EvaluateReversePolishNotationNaiveSolutionV2 solution = new P150EvaluateReversePolishNotationNaiveSolutionV2();
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
 * === Discussion Notes (V2 - Correct Naive Solution) ===
 *
 * Approach: Stack-based evaluation
 * Time: O(n), Space: O(n)
 *
 * Core idea:
 * - Traverse tokens left to right
 * - If it's a number: push onto stack
 * - If it's an operator: pop two values, compute, push result back onto stack
 * - At the end, the single remaining value on the stack is the answer
 *
 * RPN walkthrough with ["4","-2","/","2","-3","-","-"]:
 *   "4"  -> push         -> stack: [4]
 *   "-2" -> push         -> stack: [4, -2]
 *   "/"  -> pop -2 & 4   -> 4 / (-2) = -2, push  -> stack: [-2]
 *   "2"  -> push         -> stack: [-2, 2]
 *   "-3" -> push         -> stack: [-2, 2, -3]
 *   "-"  -> pop -3 & 2   -> 2 - (-3) = 5, push   -> stack: [-2, 5]
 *   "-"  -> pop 5 & -2   -> -2 - 5 = -7, push    -> stack: [-7]
 *   Result: -7
 *
 * Pop order matters:
 * - First pop = right operand (s1), second pop = left operand (s2)
 * - Compute s2 OP s1 (NOT s1 OP s2)
 * - Doesn't matter for + and *, but critical for - and /
 *
 * Distinguishing negative numbers from operators:
 * - Operators are always exactly 1 character: "+", "-", "*", "/"
 * - Negative numbers like "-2", "-3" have length > 1
 * - Using .equals() comparison naturally handles this since "-2" != "-"
 *
 * V1 -> V2 fix:
 * - V1 used a separate `result` variable and only popped one value per operator
 * - V2 always pops two values from the stack and pushes the result back
 * - The stack itself tracks all intermediate results — no extra variable needed
 */
