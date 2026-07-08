package leetcode2026.p225implementstackusingqueues;

import java.util.LinkedList;
import java.util.Queue;

public class P225V1ImplementStackUsingQueuesNaiveSolution {

    // Two Queues. Time: push O(1), pop O(n). Space: O(n)
    class MyStack {
        Queue<Integer> queue = new LinkedList<>();

        public MyStack() {
        }

        public void push(int x) {
            queue.offer(x);
        }

        public int pop() {
            return queue.remove();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        MyStack myStack = new P225V1ImplementStackUsingQueuesNaiveSolution().new MyStack();
        myStack.push(1);
        myStack.push(2);
        System.out.println(myStack.top());   // 2
        System.out.println(myStack.pop());   // 2
        System.out.println(myStack.empty()); // false
    }
}

/*
 * Approach / 思路:
 *
 *
 * 過程示意:
 *
 *
 * 比較:
 *
 *
 * Real-world usage:
 *
 */