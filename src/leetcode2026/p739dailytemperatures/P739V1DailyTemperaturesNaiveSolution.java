package leetcode2026.p739dailytemperatures;

public class P739V1DailyTemperaturesNaiveSolution {
    // Brute force - O(n^2) Time, O(n) Space
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] result = new int[length];

        for(int i = 0; i < length ; i++){
            if (i == length - 1){
                result[i] = 0;
                continue;
            }
            for(int j = i + 1; j < length; j++){
                if ((temperatures[j] - temperatures[i]) > 0){
                    result[i] = j - i;
                    break;
                }else if(j == length - 1){
                    result[i] = 0;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        P739V1DailyTemperaturesNaiveSolution solution = new P739V1DailyTemperaturesNaiveSolution();

        // Example 1: expected [1,1,4,2,1,1,0,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        // Example 2: expected [1,1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,40,50,60})));
        // Example 3: expected [1,1,0]
        System.out.println(java.util.Arrays.toString(solution.dailyTemperatures(new int[]{30,60,90})));
    }
}

/*
    Naive: Brute Force O(n^2)
    - For each element, scan all elements after it to find the first warmer temperature.
    - Inner loop breaks when found; if none found, result stays 0 (default).
    - Simplification notes:
      1. int[] defaults to 0, so no need to explicitly set result[i] = 0 for edge cases.
      2. temperatures[j] > temperatures[i] is cleaner than (temperatures[j] - temperatures[i]) > 0.
    - TLE on large inputs due to O(n^2) worst case.

    Optimized: Monotonic Stack O(n) — see V2 / OptimizedSolution
    - Stack stores indices (not values). Use temperatures[index] to get the temperature.
    - Stack maintains a decreasing order of temperatures from bottom to top.
    - When a new temperature is higher than stack top, pop and compute the day difference.
    - Why decreasing order is guaranteed:
      If a larger value were sitting on top of a smaller one, the smaller one would have
      already been popped when the larger one was pushed. So the stack can never be
      [small, big, small] — it is always [big, ..., small] from bottom to top.
    - Indices left in the stack at the end have no warmer future day → result stays 0.

    Walkthrough with [73, 74, 75, 71, 69, 72, 76, 73]:
    i=0 73 → stack:[0]
    i=1 74 → 74>73, pop 0, result[0]=1 → stack:[1]
    i=2 75 → 75>74, pop 1, result[1]=1 → stack:[2]
    i=3 71 → 71<75, push → stack:[2,3]
    i=4 69 → 69<71, push → stack:[2,3,4]
    i=5 72 → 72>69, pop 4, result[4]=1; 72>71, pop 3, result[3]=2 → stack:[2,5]
    i=6 76 → 76>72, pop 5, result[5]=1; 76>75, pop 2, result[2]=4 → stack:[6]
    i=7 73 → 73<76, push → stack:[6,7]
    Result: [1,1,4,2,1,1,0,0]
*/
