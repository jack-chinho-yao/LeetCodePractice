package leetcode2026.p155minstack;

import java.util.ArrayDeque;
import java.util.Deque;

public class P155MinStackOptimizedSolutionClaude {

    // Two stacks approach, O(1) time for all operations, O(n) space
    class MinStack {

        private Deque<Integer> stack;
        private Deque<Integer> minStack;

        public MinStack() {
            stack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }

        public void push(int val) {
            stack.push(val);
            int min = minStack.isEmpty() ? val : Math.min(minStack.peek(), val);
            minStack.push(min);
        }

        public void pop() {
            stack.pop();
            minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new P155MinStackOptimizedSolutionClaude().new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); // -3
        minStack.pop();
        System.out.println(minStack.top());    // 0
        System.out.println(minStack.getMin()); // -2
    }
}
