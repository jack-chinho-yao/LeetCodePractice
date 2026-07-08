package leetcode2026.p225implementstackusingqueues;

import java.util.LinkedList;
import java.util.Queue;

public class P225V2ImplementStackUsingQueuesOptimizedSolution {

    // One Queue (rotate on push). Time: push O(n), pop O(1), top O(1). Space: O(n)
    class MyStack {

        Queue<Integer> queue = new LinkedList<>();

        public MyStack() {

        }

        public void push(int x) {
            queue.offer(x);
            // Rotate so the newly pushed element sits at the front (queue head = stack top)
            int size = queue.size();
            while (size > 1) {
                queue.offer(queue.poll());
                size--;
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
    // offer() - Add element to back, poll() - Remove from front
    public static void main(String[] args) {
        MyStack myStack = new P225V2ImplementStackUsingQueuesOptimizedSolution().new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.top());   // 2
        System.out.println(myStack.pop());   // 2
        System.out.println(myStack.empty()); // false
    }
}

/*
 * Approach / 思路:
 * Use a single FIFO queue to simulate a LIFO stack. The trick is on push:
 * after enqueuing the new element at the tail, rotate the queue by dequeuing
 * and re-enqueuing the other (size - 1) elements. This moves the newest element
 * to the front, so the queue head always equals the stack top. pop/top then just
 * read the head directly.
 *
 * 用單一 FIFO queue 模擬 LIFO stack。關鍵在 push:把新元素放到隊尾後,
 * 把前面 (size - 1) 個元素依序 poll 出來再 offer 回去,旋轉一圈,
 * 讓最新加入的元素轉到隊首。如此一來隊首永遠是 stack 的 top,
 * pop / top 直接讀隊首即可。
 *
 * 過程示意 / Walkthrough:
 *   push(1): offer 1            -> [1]            (rotate 0 次)
 *   push(2): offer 2 -> [1,2]   rotate poll 1 offer 1 -> [2,1]
 *   top()  -> peek = 2
 *   pop()  -> poll = 2          -> [1]
 *   empty()-> false
 *
 * 比較 / Comparison:
 *   V1 Naive (two queues): push O(1), pop O(n) — cost paid on every pop.
 *   V2 Optimized (one queue, rotate on push): push O(n), pop/top O(1).
 *   兩者總體複雜度相當,差別在於「成本付在 push 還是 pop」。當 top/pop
 *   呼叫較頻繁時,V2 把成本前移到 push,讀取端更快,且只用一個 queue 較省空間。
 *
 * Real-world usage / 真實應用:
 *   - Undo / redo 堆疊:在只提供 FIFO message queue 的環境(如某些
 *     message broker、訊息中介層)上模擬 LIFO 的「最後操作優先回復」。
 *   - Browser / editor 的上一步功能,當底層儲存只支援 FIFO append 時的轉接層。
 *   - 教學上展示如何用一種抽象資料結構(queue)重建另一種(stack),
 *     是 adapter pattern 在資料結構層級的典型例子。
 */