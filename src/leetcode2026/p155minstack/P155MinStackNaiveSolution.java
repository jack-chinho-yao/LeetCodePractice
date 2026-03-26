package leetcode2026.p155minstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P155MinStackNaiveSolution {

    class MinStack {

        private List<Integer> list;

        public MinStack() {
            list = new ArrayList<>();
        }

        public void push(int val) {
            this.list.add(val);

        }

        public void pop() {
            this.list.remove(this.list.size()-1);
        }

        public int top() {
            return this.list.get(this.list.size()-1);
        }

        public int getMin() {
            Integer min = Integer.MAX_VALUE;
            for (Integer i : this.list) {
                min = Math.min(min, i);
            }

            return min;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
        System.out.println(integers.get(integers.size()));

//
//        MinStack minStack = new P155MinStackNaiveSolution().new MinStack();
//        minStack.push(-2);
//        minStack.push(0);
//        minStack.push(-3);
//        System.out.println(minStack.getMin()); // -3
//        minStack.pop();
//        System.out.println(minStack.top());    // 0
//        System.out.println(minStack.getMin()); // -2
    }
}
