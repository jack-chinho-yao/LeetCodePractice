package leetcode2026.p53maximumsubarray;

public class P53MaximumSubarrayOptimizedSolution {

    // Kadane's Algorithm, O(n) time, O(1) space
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        P53MaximumSubarrayOptimizedSolution solution = new P53MaximumSubarrayOptimizedSolution();
        System.out.println(solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(solution.maxSubArray(new int[]{1})); // 1
        System.out.println(solution.maxSubArray(new int[]{5, 4, -1, 7, 8})); // 23
    }
}

/*
 * === Discussion Notes ===
 *
 * Kadane's Algorithm (proposed by Jay Kadane, recorded in Bentley's 1984 ACM article):
 *
 * Key idea - define M(j) = "max subarray sum ending at index j":
 *   M(j) = max(M(j-1) + nums[j], nums[j])
 *
 * Two choices at each index:
 *   1. Extend the previous best subarray: M(j-1) + nums[j]
 *   2. Start fresh from current element: nums[j]
 *
 * When does "start fresh" win?
 *   M(j-1) + nums[j] < nums[j]  =>  M(j-1) < 0
 *   i.e. when the previous best accumulation is negative, it's a burden — drop it.
 *
 * Why dropping negative accumulation is always safe:
 *   If M(j-1) < 0, then for ANY future subarray, including M(j-1) only makes the sum smaller.
 *   So there's no scenario where "keeping a negative prefix" leads to a better answer later.
 *
 * Two variables:
 *   - currentSum: M(j), the best subarray sum ending at the current index (explorer)
 *   - maxSum: the global best across all M(0)..M(j) (recorder)
 *
 * Walkthrough with [-2, 1, -3, 4, -1, 2, 1, -5, 4]:
 *   index:       0   1   2   3   4   5   6   7   8
 *   currentSum: -2   1  -2   4   3   5   6   1   5
 *   maxSum:     -2   1   1   4   4   5   6   6   6
 *   At index 3: currentSum was -2 (negative), so drop it, start fresh from 4.
 *   Answer = 6, which is subarray [4, -1, 2, 1].
 *
 * Edge cases handled:
 *   - All negative: currentSum keeps restarting, maxSum holds the least negative. e.g. [-3,-5,-1,-4] => -1
 *   - All positive: currentSum never restarts, just keeps growing.
 *   - Large negative in the middle: splits the array, algorithm naturally restarts after it.
 */