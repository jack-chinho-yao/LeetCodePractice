package leetcode2026.p167twosumii;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Brute force: try every pair. Time O(n^2), Space O(1).
public class P167V2TwoSumIINaiveSolution {

    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> complementToIndex = new HashMap<>();
        for(int i = 0; i < numbers.length; i++){
            int complement = target - numbers[i];
            if(complementToIndex.containsKey(complement)){
                return new int[]{complementToIndex.get(complement)+1, i+1};
            }
            complementToIndex.put(numbers[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        P167V2TwoSumIINaiveSolution solution = new P167V2TwoSumIINaiveSolution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 7, 11, 15}, 9))); // [1, 2]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 3, 4}, 6)));      // [1, 3]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-1, 0}, -1)));       // [1, 2]
    }

    /*
    Problem 167. Two Sum II - Input Array Is Sorted

    Given a 1-indexed array of integers `numbers` that is already sorted in
    non-decreasing order, find two numbers such that they add up to a specific
    target number. Return the indices of the two numbers (1-indexed) as an
    integer array [index1, index2] of length 2.

    Constraints:
    - There is exactly one solution.
    - You may not use the same element twice.
    - Your solution must use only constant extra space.

    Example 1:
        Input : numbers = [2, 7, 11, 15], target = 9
        Output: [1, 2]

    Example 2:
        Input : numbers = [2, 3, 4], target = 6
        Output: [1, 3]

    Example 3:
        Input : numbers = [-1, 0], target = -1
        Output: [1, 2]

    Naive approach:
    - Ignore the "sorted" property entirely.
    - Check every pair (i, j) with i < j and return when numbers[i] + numbers[j] == target.
    - Indices must be returned as 1-indexed, so add 1 to the 0-indexed positions.
    - Time  : O(n^2) — two nested loops.
    - Space : O(1)   — no auxiliary storage.
    */
}