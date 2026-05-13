package leetcode2026.p155minstack;

import java.util.ArrayList;
import java.util.List;

public class P155MinStackOptimizedSolution {
    class MinStack {

        private List<Integer> list;
        private List<Integer> minList;

        public MinStack() {
            list = new ArrayList<>();
            minList = new ArrayList<>();
        }

        public void push(int val) {
            this.list.add(val);
            if (minList.isEmpty()){
                minList.add(val);
            }else {
                this.minList.add(Math.min(this.minList.get(this.minList.size()-1), val));

            }
        }

        public void pop() {
            this.list.remove(this.list.size()-1);
            this.minList.remove(this.minList.size()-1);

        }

        public int top() {
            return this.list.get(this.list.size()-1);
        }

        public int getMin() {
            return minList.get(this.minList.size()-1);
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new P155MinStackOptimizedSolution().new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin()); // -3
        minStack.pop();
        System.out.println(minStack.top());    // 0
        System.out.println(minStack.getMin()); // -2
    }
}

/*
 * === Discussion Notes ===
 *
 * Problem: Design a stack that supports push, pop, top, and getMin — all in O(1) time.
 *
 * Naive approach (NaiveSolution):
 *   - Use a single ArrayList as the stack.
 *   - push/pop/top are all O(1), but getMin() iterates the entire list → O(n).
 *
 * Optimized approach — Two Stacks (this file):
 *   - Use TWO ArrayLists: one for the actual stack, one (minList) to track the minimum at each state.
 *   - On push(val): push val to list, and push Math.min(minList.top, val) to minList.
 *   - On pop(): pop from both lists simultaneously.
 *   - getMin(): just return minList.top → O(1).
 *
 * Key insight — "state snapshot":
 *   Each position in minList records "what is the minimum if the stack has elements up to this point?"
 *   When we pop, the previous state's minimum is already stored — no need to recompute.
 *
 * Walkthrough with push(-2), push(0), push(-3), pop():
 *   push(-2) → list: [-2]        minList: [-2]          (first element, min is itself)
 *   push(0)  → list: [-2, 0]     minList: [-2, -2]      (min(-2, 0) = -2)
 *   push(-3) → list: [-2, 0, -3] minList: [-2, -2, -3]  (min(-2, -3) = -3)
 *   getMin() → minList top = -3 ✓
 *   pop()    → list: [-2, 0]     minList: [-2, -2]       (previous min state restored!)
 *   top()    → list top = 0 ✓
 *   getMin() → minList top = -2 ✓
 *
 * Common pitfall:
 *   Using a single "min" variable instead of a min stack. When you pop the current min,
 *   you can't recover the previous min without re-scanning. The min stack solves this.
 *
 * Complexity:
 *   Time:  O(1) for all operations
 *   Space: O(n) — two lists of size n (constant factor 2x vs naive)
 *
 * This is a classic example of the "space for time" trade-off pattern.
 * The same pattern appears in: monotonic stacks, prefix sum arrays, DP memo tables, etc.
 */
