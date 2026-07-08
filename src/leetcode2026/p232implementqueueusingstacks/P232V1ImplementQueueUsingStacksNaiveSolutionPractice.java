package leetcode2026.p232implementqueueusingstacks;

import java.util.Stack;

public class P232V1ImplementQueueUsingStacksNaiveSolutionPractice {

    // Two Stacks. Time: push O(n), pop O(1). Space: O(n)
    class MyQueue {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> temp = new Stack<>();

        public MyQueue() {
        }

        public void push(int x) {
            while(!stack.isEmpty()){
                temp.push(stack.pop());
            }
            stack.push(x);
            while (!temp.isEmpty()){
                stack.push(temp.pop());
            }
        }

        public int pop() {
            return stack.pop();
        }

        public int peek() {
            return stack.peek();
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }

    public static void main(String[] args) {
        MyQueue myQueue = new P232V1ImplementQueueUsingStacksNaiveSolutionPractice().new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        System.out.println(myQueue.peek());  // 1
        System.out.println(myQueue.pop());   // 1
        System.out.println(myQueue.empty()); // false
    }
}

/*
 * Approach / 思路:
 * Use one main stack to keep elements already in queue order (front element on top).
 * On push, dump the whole stack into temp, push the new element, then pour temp back —
 * so the newest element ends up at the bottom and the oldest stays on top.
 * pop/peek/empty then just delegate to the main stack's top.
 * 用一個主 stack 維持「佇列順序」（最舊的在頂端）。push 時把整個 stack 倒進
 * temp，壓入新元素後再倒回來，讓最新的沉到底、最舊的留在頂端。pop/peek/empty
 * 直接操作主 stack 頂端即可。
 *
 * 過程示意:
 * push(1): stack=[1]
 * push(2): 1->temp, push 2 -> stack=[2], 倒回 -> stack=[2,1] (top=1)
 * peek() -> 1, pop() -> 1, stack=[2]
 *
 * 比較:
 * Naive 把成本全壓在 push (每次 O(n))，pop/peek 是 O(1)。
 * 適合 pop 遠多於 push 的情境；反之則用 V2 的 amortized O(1) 較佳。
 *
 * Real-world usage:
 * - 用受限的原語（只有 LIFO stack 的 API）模擬 FIFO，常見於只暴露 stack 介面的
 *   嵌入式 / VM 環境。
 * - Undo/redo 與指令緩衝中以 stack 重組順序的橋接邏輯。
 */