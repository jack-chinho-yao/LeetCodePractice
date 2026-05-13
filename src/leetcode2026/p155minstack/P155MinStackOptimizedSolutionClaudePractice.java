package leetcode2026.p155minstack;

import java.util.ArrayDeque;
import java.util.Deque;

public class P155MinStackOptimizedSolutionClaudePractice  {

    // Two stacks approach, O(1) time for all operations, O(n) space
    class MinStack {
        private ArrayDeque<Integer> stack = new ArrayDeque<>();
        private ArrayDeque<Integer> min = new ArrayDeque<>();

        public void push(Integer input){
            this.stack.push(input);
            int minCurrent = min.isEmpty() ? input: Math.min(min.peek(), input);
            this.min.push(minCurrent);
        }
        public void pop(){
            this.stack.pop();
            this.min.pop();
        }
        public int top(){
            return this.stack.peek();
        }
        public int getMin(){
            return this.min.peek();
        }

    }

    public static void main(String[] args) {
        MinStack minStack = new P155MinStackOptimizedSolutionClaudePractice().new MinStack();
        ArrayDeque<Object> deque = new ArrayDeque<>();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); // -3
        minStack.pop();
        System.out.println(minStack.top());    // 0
        System.out.println(minStack.getMin()); // -2
    }
}
