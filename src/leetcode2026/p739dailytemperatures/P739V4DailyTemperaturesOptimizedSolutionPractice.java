package leetcode2026.p739dailytemperatures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class P739V4DailyTemperaturesOptimizedSolutionPractice {

    // Monotonic Stack (clean final version) - O(n) Time, O(n) Space
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> indexStack = new ArrayDeque<>();
        int length = temperatures.length;
        int[] result = new int[length];
        for(int i = 0; i < length; i++){

            int temperature = temperatures[i];
            while (indexStack.peek() != null){
                Integer peekIndex = indexStack.peek();
                int temperatureOld = temperatures[peekIndex];
                if (temperature > temperatureOld){
                    result[peekIndex] = i - peekIndex;
                    indexStack.pop();
                }else {
                    break;
                }
            }
            indexStack.push(i);
        }
        return result;
    }

    public static void main(String[] args) {
        P739V4DailyTemperaturesOptimizedSolutionPractice solution = new P739V4DailyTemperaturesOptimizedSolutionPractice();

        // Example 1: expected [1,1,4,2,1,1,0,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        // Example 2: expected [1,1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,40,50,60})));
        // Example 3: expected [1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,60,90})));
    }
}
