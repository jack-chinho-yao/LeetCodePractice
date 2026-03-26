package leetcode2026.p739dailytemperatures;

import java.util.Stack;

public class P739V4DailyTemperaturesOptimizedSolution {

    // Monotonic Stack (clean final version) - O(n) Time, O(n) Space
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] result = new int[length];
        Stack<Integer> indexStack = new Stack<>();
        for (int i = 0; i < length; i++) {
            while (!indexStack.isEmpty() && temperatures[i] > temperatures[indexStack.peek()]) {
                int idx = indexStack.pop();
                result[idx] = i - idx;
            }
            indexStack.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        P739V4DailyTemperaturesOptimizedSolution solution = new P739V4DailyTemperaturesOptimizedSolution();

        // Example 1: expected [1,1,4,2,1,1,0,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        // Example 2: expected [1,1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,40,50,60})));
        // Example 3: expected [1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,60,90})));
    }
}
