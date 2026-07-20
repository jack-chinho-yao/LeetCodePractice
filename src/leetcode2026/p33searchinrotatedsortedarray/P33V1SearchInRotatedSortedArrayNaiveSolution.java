package leetcode2026.p33searchinrotatedsortedarray;

// Linear scan for the target. Time O(n), Space O(1).
public class P33V1SearchInRotatedSortedArrayNaiveSolution {

    public int search(int[] nums, int target) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P33V1SearchInRotatedSortedArrayNaiveSolution solution =
                new P33V1SearchInRotatedSortedArrayNaiveSolution();
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));  // 4
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));  // -1
        System.out.println(solution.search(new int[]{1}, 0));                    // -1
    }

    /*
    Problem 33. Search in Rotated Sorted Array

    There is an ascending sorted array of UNIQUE integers that was possibly
    rotated at an unknown pivot. Given the array `nums` and an integer `target`,
    return the index of target if present, otherwise -1.
    You must write an algorithm with O(log n) runtime complexity.

    Example 1:
        Input : nums = [4, 5, 6, 7, 0, 1, 2], target = 0
        Output: 4
    Example 2:
        Input : nums = [4, 5, 6, 7, 0, 1, 2], target = 3
        Output: -1

    Naive approach:
    - Walk the array, return the first index where nums[i] == target, else -1.
    - Time  : O(n)  — ignores the rotated-sorted structure.
    - Space : O(1)
    - Passes correctness but NOT O(log n) — that is V2.
    */
}
