package leetcode2026.p53maximumsubarray;

public class P53MaximumSubarrayNaiveSolution {

    // Brute force: check all subarrays, O(n^2) time, O(1) space
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        P53MaximumSubarrayNaiveSolution solution = new P53MaximumSubarrayNaiveSolution();
        System.out.println(solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(solution.maxSubArray(new int[]{1})); // 1
        System.out.println(solution.maxSubArray(new int[]{5, 4, -1, 7, 8})); // 23
    }
}

/*
 * === Discussion Notes ===
 *
 * Brute Force Approach:
 * - Enumerate all possible subarrays using two loops: i = start, j = end.
 * - For each starting index i, accumulate the sum as j expands, and track the global max.
 * - This is the most intuitive approach: just check every subarray.
 *
 * Why this works but is slow:
 * - There are O(n^2) subarrays, and we compute each sum incrementally (not O(n^3)).
 * - Still too slow for large inputs (n up to 10^5).
 *
 * This is NOT a sliding window problem:
 * - Sliding window requires a clear shrink condition (e.g. fixed size or a constraint to maintain).
 * - Here, we don't know the subarray length in advance, so window doesn't directly apply.
 *
 * Optimization path:
 * - Observation: when computing "max subarray ending at index j", we repeat a lot of work.
 * - This leads to Kadane's Algorithm (see OptimizedSolution).
 */