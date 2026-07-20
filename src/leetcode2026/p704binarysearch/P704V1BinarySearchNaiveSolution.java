package leetcode2026.p704binarysearch;

// Linear scan (ignores the sorted property). Time O(n), Space O(1).
public class P704V1BinarySearchNaiveSolution {

    public int search(int[] nums, int target) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P704V1BinarySearchNaiveSolution solution = new P704V1BinarySearchNaiveSolution();
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));  // 4
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));  // -1
        System.out.println(solution.search(new int[]{5}, 5));                   // 0
    }

    /*
    Problem 704. Binary Search

    Given a sorted (ascending) array of integers `nums` and an integer `target`,
    return the index of target if it exists, otherwise return -1.
    You must write an algorithm with O(log n) runtime complexity.

    Constraints:
    - All integers in nums are unique.
    - nums is sorted in ascending order.

    Example 1:
        Input : nums = [-1, 0, 3, 5, 9, 12], target = 9
        Output: 4
    Example 2:
        Input : nums = [-1, 0, 3, 5, 9, 12], target = 2
        Output: -1

    Naive approach:
    - Walk the array left to right, return the first index where nums[i] == target.
    - Return -1 if the loop finishes without a match.
    - Time  : O(n)  — throws away the sorted property.
    - Space : O(1)
    - Note: this passes correctness but NOT the O(log n) requirement — that is V2's job.
    */
}
