package leetcode2026.p232implementqueueusingstacks;

import java.util.Stack;

public class P232V2ImplementQueueUsingStacksOptimizedSolution {

    // Two Stacks (input/output). Time: push O(1), pop amortized O(1). Space: O(n)
    class MyQueue {
        Stack<Integer> in = new Stack<>();
        Stack<Integer> out = new Stack<>();

        public MyQueue() {
        }

        public void push(int x) {
            in.push(x);
        }

        public int pop() {
            transferIfNeeded();
            return out.pop();
        }

        public int peek() {
            transferIfNeeded();
            return out.peek();
        }

        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }

        // Only refill out when it's empty, reversing in's order once (lazy transfer).
        private void transferIfNeeded() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
        }
    }

    public static void main(String[] args) {
        MyQueue myQueue = new P232V2ImplementQueueUsingStacksOptimizedSolution().new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        System.out.println(myQueue.peek());  // 1
        System.out.println(myQueue.pop());   // 1
        System.out.println(myQueue.empty()); // false
    }
}

/*
 * Approach / 思路:
 * Keep two stacks: `in` receives pushes, `out` serves pops/peeks. Only when `out`
 * is empty do we pour all of `in` into `out`, which reverses the order so the oldest
 * element lands on top. Each element is moved at most once from in->out, giving
 * amortized O(1) per operation.
 * 維持兩個 stack：`in` 負責接收 push，`out` 負責 pop/peek。只有當 `out` 空了才把
 * `in` 整個倒進 `out`，順序剛好翻轉，最舊的元素到頂端。每個元素一生只會被搬一次
 * in->out，因此每個操作 amortized O(1)。
 *
 * 過程示意:
 * push(1), push(2): in=[1,2], out=[]
 * peek(): out 空 -> 倒入 -> out=[2,1] (top=1), 回傳 1
 * pop(): out=[2,1] -> 回傳 1, out=[2]
 * push(3): in=[3], out=[2]  (不需搬移)
 *
 * 比較:
 * 相較 V1 把成本集中在 push (每次 O(n))，V2 用 lazy transfer 讓 push 維持 O(1)、
 * pop/peek amortized O(1)，整體吞吐更穩定，是面試標準解。
 *
 * Real-world usage:
 * - 串流 / 批次處理中「累積進 buffer，批次翻轉後消費」的模式。
 * - 雙緩衝 (double buffering)：一邊寫入、一邊讀取，讀完才交換，降低搬移成本。
 * - 只暴露 stack 原語的環境中以 amortized O(1) 模擬 FIFO 佇列。
 */