package leetcode2026.p739dailytemperatures;

import java.util.Stack;

public class P739V2DailyTemperaturesOptimizedSolution {
    // Monotonic Stack (pop then push back) - O(n) Time, O(n) Space
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] result = new int[length];
        Stack<Integer> indexStack = new Stack<>();
        for(int i = 0; i < length; i++){
            while (indexStack.size() >= 1){
                Integer pop = indexStack.pop();
                if (temperatures[i] > temperatures[pop]){
                    result[pop] = i - pop;
                }else {
                    indexStack.push(pop);
                    break;
                }
            }
            indexStack.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        P739V2DailyTemperaturesOptimizedSolution solution = new P739V2DailyTemperaturesOptimizedSolution();

        // Example 1: expected [1,1,4,2,1,1,0,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        // Example 2: expected [1,1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,40,50,60})));
        // Example 3: expected [1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,60,90})));
    }
}
