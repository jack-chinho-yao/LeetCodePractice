package leetcode2026.p153findminimuminrotatedsortedarray;

// Linear scan for the smallest element. Time O(n), Space O(1).
public class P153V1FindMinimumInRotatedSortedArrayNaiveSolution {

    public int findMin(int[] nums) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P153V1FindMinimumInRotatedSortedArrayNaiveSolution solution =
                new P153V1FindMinimumInRotatedSortedArrayNaiveSolution();
        System.out.println(solution.findMin(new int[]{3, 4, 5, 1, 2}));        // 1
        System.out.println(solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));  // 0
        System.out.println(solution.findMin(new int[]{11, 13, 15, 17}));       // 11
    }

    /*
    Problem 153. Find Minimum in Rotated Sorted Array

    Suppose an ascending sorted array of UNIQUE integers was rotated between 1
    and n times. Given such an array `nums`, return the minimum element.
    You must write an algorithm that runs in O(log n) time.

    Example 1:
        Input : nums = [3, 4, 5, 1, 2]
        Output: 1
    Example 2:
        Input : nums = [4, 5, 6, 7, 0, 1, 2]
        Output: 0
    Example 3:
        Input : nums = [11, 13, 15, 17]   (rotated n times = back to sorted)
        Output: 11

    Naive approach:
    - Track the running minimum over a single left-to-right pass.
    - Time  : O(n)  — ignores the rotated-sorted structure.
    - Space : O(1)
    - Passes correctness but NOT O(log n) — that is V2.
    */
}
