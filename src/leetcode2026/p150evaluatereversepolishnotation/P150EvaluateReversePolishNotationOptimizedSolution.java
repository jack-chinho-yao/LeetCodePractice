package leetcode2026.p150evaluatereversepolishnotation;

public class P150EvaluateReversePolishNotationOptimizedSolution {

    // Array as stack to avoid Stack/Vector synchronization overhead. Time: O(n), Space: O(n)
    public int evalRPN(String[] tokens) {
        //優化解 有空再實作 20260321 -> 還沒實作
        int[] stack = new int[tokens.length];
        int top = -1;
        for (String token : tokens) {
            if ("/".equals(token) || "+".equals(token) || "*".equals(token) || "-".equals(token)) {
                int s1 = stack[top--];
                int s2 = stack[top--];
                if ("/".equals(token)) {
                    stack[++top] = s2 / s1;
                } else if ("+".equals(token)) {
                    stack[++top] = s2 + s1;
                } else if ("*".equals(token)) {
                    stack[++top] = s2 * s1;
                } else {
                    stack[++top] = s2 - s1;
                }
            } else {
                stack[++top] = Integer.parseInt(token);
            }
        }
        return stack[top];
    }

    public static void main(String[] args) {
        P150EvaluateReversePolishNotationOptimizedSolution solution = new P150EvaluateReversePolishNotationOptimizedSolution();

        // Example 1: (2 + 1) * 3 = 9
        System.out.println(solution.evalRPN(new String[]{"2", "1", "+", "3", "*"})); // Expected: 9

        // Example 2: (4 + (13 / 5)) = 6
        System.out.println(solution.evalRPN(new String[]{"4", "13", "5", "/", "+"})); // Expected: 6

        // Example 3: ((2 + 1) * 3) = 9, then ((10 * (6 / -132 + ...)))
        System.out.println(solution.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"})); // Expected: 22
    }
}

/*
 * === Discussion Notes (Optimized Solution) ===
 *
 * Approach: int[] array as stack with top pointer
 * Time: O(n), Space: O(n) — same Big-O, but lower constant factor
 *
 * Optimization over V2 (Stack<String>):
 * 1. Stack<String> -> Stack<Integer>: eliminates String <-> Integer conversion overhead
 * 2. Stack<Integer> -> int[]: eliminates Stack/Vector synchronization overhead and Integer boxing
 *    - Stack extends Vector, which has synchronized methods (thread-safe but unnecessary here)
 *    - int[] with a top pointer is the lightest-weight stack possible
 *    - ++top = push, top-- = pop
 *    - Max stack size never exceeds tokens.length, so array size is safe
 *
 * Interview note:
 * - The Stack<Integer> version (V2 logic with Integer stack) is sufficient for interviews
 * - Being able to explain the approach clearly and handle edge cases matters more
 * - The int[] optimization is for runtime ranking on LeetCode, not a make-or-break in interviews
 */
