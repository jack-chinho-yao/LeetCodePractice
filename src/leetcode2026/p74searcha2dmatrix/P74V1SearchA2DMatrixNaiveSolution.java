package leetcode2026.p74searcha2dmatrix;

// Scan every cell (ignores the sorted structure). Time O(m*n), Space O(1).
public class P74V1SearchA2DMatrixNaiveSolution {

    public boolean searchMatrix(int[][] matrix, int target) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        P74V1SearchA2DMatrixNaiveSolution solution = new P74V1SearchA2DMatrixNaiveSolution();
        int[][] m = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println(solution.searchMatrix(m, 3));   // true
        System.out.println(solution.searchMatrix(m, 13));  // false
        System.out.println(solution.searchMatrix(new int[][]{{1}}, 1)); // true
    }

    /*
    Problem 74. Search a 2D Matrix

    You are given an m x n integer matrix with these two properties:
    - Each row is sorted in non-decreasing order.
    - The first integer of each row is greater than the last integer of the
      previous row.
    Given an integer target, return true if target is in the matrix, else false.
    You must write an algorithm in O(log(m*n)) time.

    Example 1:
        Input : matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
        Output: true
    Example 2:
        Input : same matrix, target = 13
        Output: false

    Naive approach:
    - Loop over every row, every column, return true on the first match.
    - Time  : O(m*n)  — ignores both sorted properties.
    - Space : O(1)
    - Passes correctness but NOT the O(log(m*n)) requirement — that is V2.
    */
}
