package leetcode2026.p167twosumii;

import java.util.Arrays;

// Two pointers on the sorted array. Time O(n), Space O(1).
public class P167V2TwoSumIIOptimizedSolution {

    public int[] twoSum(int[] numbers, int target) {
        int right = numbers.length - 1;
        int left = 0;
        while (numbers[left] + numbers[right] != target){
            if (numbers[left] + numbers[right] > target){
                right--;
            }else if (numbers[left] + numbers[right] < target){
                left++;
            }


        }
        return new int[]{left + 1, right + 1};

    }

    public static void main(String[] args) {
        P167V2TwoSumIIOptimizedSolution solution = new P167V2TwoSumIIOptimizedSolution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 7, 11, 15}, 9))); // [1, 2]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 3, 4}, 6)));      // [1, 3]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-1, 0}, -1)));       // [1, 2]
    }

    /*
    Optimized approach — Two Pointers:

    Because the array is sorted in non-decreasing order, we can point one index
    at each end and shrink the window based on how the current sum compares to
    the target:

        left  ──►                        ◄── right
        [ 2,   3,   4,   7,   11,   15 ]        target = 18

        sum = numbers[left] + numbers[right]
          if sum == target  -> done, return [left + 1, right + 1]
          if sum <  target  -> left++   (need a larger value)
          if sum >  target  -> right--  (need a smaller value)

    Why it works:
    - Moving `left` rightward only increases the sum (values are non-decreasing).
    - Moving `right` leftward only decreases the sum.
    - So each move eliminates exactly one index from needing further consideration,
      which means we visit each index at most once.

    Complexity:
    - Time  : O(n)  — pointers traverse the array once.
    - Space : O(1)  — meets the problem's constant-space requirement.

    Comparison vs. Naive:
    - Naive ignores sortedness → O(n^2).
    - Hash map (Two Sum I style) would also be O(n) time but O(n) space,
      which violates the "constant extra space" constraint of this problem.
    - Two pointers is the textbook fit here: exploits sortedness AND stays O(1) space.
    */
}
