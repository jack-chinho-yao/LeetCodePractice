package leetcode2026.p150evaluatereversepolishnotation;

import java.util.Stack;

//  你目前的時間和空間都已經是 O(n)，算法層面沒辦法再降了。
//
//  優化方向是降低常數開銷，你可以想兩個點：
//
//  1. 去掉 String 轉換：你現在每次運算都在 String → Integer → 計算 → String，來回轉。如果 stack 裡直接存 int 呢？
//  2. 去掉 Stack 類本身：Stack 底層是 Vector，每個操作都有 synchronized 鎖的開銷。你其實知道 stack 最大不會超過 tokens.length，那有沒有更輕量的東西可以當 stack 用？
//
public class P150EvaluateReversePolishNotationNaiveSolutionV3 {
//["4","-2","/","2","-3","-","-"]
    public int evalRPN(String[] tokens) {
        Stack<Integer> stacks = new Stack<>();
        for(String token : tokens){
            if ("/".equals(token) || "+".equals(token) || "*".equals(token) || "-".equals(token)){
                Integer s1 = stacks.pop();
                Integer s2 = stacks.pop();
                if("/".equals(token)){
                    stacks.push(s2 / s1);
                }else if ("+".equals(token)){
                    stacks.push(s2 + s1);
                }else if ("*".equals(token)){
                    stacks.push(s2 * s1);
                }else if ("-".equals(token)){
                    stacks.push(s2 - s1);
                }
            }else {
                stacks.push( Integer.valueOf(token));
            }
        }
        return stacks.pop();
    }

    public static void main(String[] args) {
        P150EvaluateReversePolishNotationNaiveSolutionV3 solution = new P150EvaluateReversePolishNotationNaiveSolutionV3();
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
        System.out.println(solution.evalRPN(new String[]{"4","-2","/","2","-3","-","-"})); // Expected: 22
    }
}
