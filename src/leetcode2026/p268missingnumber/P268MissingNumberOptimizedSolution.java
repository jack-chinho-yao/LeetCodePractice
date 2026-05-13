package leetcode2026.p268missingnumber;

public class P268MissingNumberOptimizedSolution {
    public int missingNumber(int[] nums) {
        return -1;
    }

    public static void main(String[] args) {
        P268MissingNumberOptimizedSolution sol = new P268MissingNumberOptimizedSolution();

        // Example 1: nums = [3,0,1], expected = 2
        System.out.println(sol.missingNumber(new int[]{3, 0, 1}));
        // Example 2: nums = [0,1], expected = 2
        System.out.println(sol.missingNumber(new int[]{0, 1}));
        // Example 3: nums = [9,6,4,2,3,5,7,0,1], expected = 8
        System.out.println(sol.missingNumber(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}));
        // Test: single element [0], expected = 1
        System.out.println(sol.missingNumber(new int[]{0}));
        // Test: single element [1], expected = 0
        System.out.println(sol.missingNumber(new int[]{1}));
    }
}
