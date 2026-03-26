package leetcode2026.p217containsduplicate;

import java.util.HashSet;
import java.util.Set;

public class P217ContainsDuplicateOptimizedSolution {

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
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < nums.length; i++){
            if (!set.add(nums[i])){
                return true;
            }
        }
        return false;
    }

// easier to read
//    public boolean containsDuplicate(int[] nums) {
//        Set<Integer> set = new HashSet<>();
//        for(int i = 0; i < nums.length; i++){
//            if (set.contains(nums[i])){
//                return true;
//            }
//            set.add(nums[i]);
//        }
//        return false;
//    }

    public static void main(String[] args) {
        P217ContainsDuplicateOptimizedSolution solution = new P217ContainsDuplicateOptimizedSolution();
        System.out.println(solution.containsDuplicate(new int[]{1, 2, 3, 1}));        // true
        System.out.println(solution.containsDuplicate(new int[]{1, 2, 3, 4}));        // false
        System.out.println(solution.containsDuplicate(new int[]{1,1,1,3,3,4,3,2,4,2})); // true
    }
}

/*
    === Discussion Notes ===

    [Approach]
    - Use a HashSet to track seen elements
    - set.add() returns false if the element already exists, so !set.add() means duplicate found

    [Complexity]
    - Time:  O(n) — HashSet add/contains is O(1) on average
    - Space: O(n)

    [HashSet vs HashMap vs ArrayList]
    - HashSet: stores keys only, contains() O(1) — best fit for this problem
    - HashMap: stores key-value pairs, containsKey() O(1) — overkill here since we don't need values
    - ArrayList: contains() O(n) linear scan, no hash mechanism

    [How HashSet works internally]
    - HashSet is backed by a HashMap under the hood
    - add() flow: compute hash -> locate bucket -> if same element exists, don't add (return false); otherwise insert (return true)
    - The hash of a given value never changes; Set does not allow duplicates

    [Two coding styles]
    - contains() + add() separately: hashes and locates bucket twice
    - !set.add() combined: only once, slightly faster (both O(n) overall, difference is just a constant factor)

    [Key takeaway of this problem]
    - This is a HashSet introductory problem — teaches you to pick the right data structure for lookup
    - List O(n) lookup vs HashSet O(1) lookup makes the difference between O(n^2) and O(n)
    - Same pattern extends to: P1 Two Sum (HashMap), P242 Valid Anagram (HashMap), P349 Intersection (HashSet)
*/
