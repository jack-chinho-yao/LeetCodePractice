package leetcode2026.p217containsduplicate;

import java.util.ArrayList;
import java.util.List;

public class P217ContainsDuplicateNaiveSolution {

    // Given an integer array nums, return true if any value appears at least twice
    // in the array, and return false if every element is distinct.
    //
    // Example 1: Input: nums = [1,2,3,1]           -> Output: true
    // Example 2: Input: nums = [1,2,3,4]           -> Output: false
    // Example 3: Input: nums = [1,1,1,3,3,4,3,2,4,2] -> Output: true
    //
    // Constraints:
    //   1 <= nums.length <= 10^5
    //   -10^9 <= nums[i] <= 10^9

    public boolean containsDuplicate(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < nums.length; i++){
            if (list.contains(nums[i])){
                return true;
            }
            list.add(nums[i]);
        }
        return false;
    }

    public static void main(String[] args) {
        P217ContainsDuplicateNaiveSolution solution = new P217ContainsDuplicateNaiveSolution();
        System.out.println(solution.containsDuplicate(new int[]{1, 2, 3, 1}));        // true
        System.out.println(solution.containsDuplicate(new int[]{1, 2, 3, 4}));        // false
        System.out.println(solution.containsDuplicate(new int[]{1,1,1,3,3,4,3,2,4,2})); // true
    }
}

/*
    === Discussion Notes ===

    [Approach]
    - Use an ArrayList to track seen elements
    - For each element, check if it already exists via list.contains()
    - If found -> return true (duplicate), otherwise add to list

    [Complexity]
    - Time:  O(n^2) — list.contains() is a linear scan O(n), outer loop O(n)
    - Space: O(n)

    [Why is ArrayList slow?]
    - ArrayList.contains() iterates from start to end, comparing with equals() — no hash mechanism
    - Hash-based structures (HashMap, HashSet) -> O(1) lookup
    - List-based structures (ArrayList, LinkedList) -> O(n) lookup
*/
