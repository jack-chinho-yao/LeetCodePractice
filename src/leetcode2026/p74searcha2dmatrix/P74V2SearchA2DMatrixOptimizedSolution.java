package leetcode2026.p74searcha2dmatrix;

// Binary search treating the matrix as one sorted list. Time O(log(m*n)), Space O(1).
public class P74V2SearchA2DMatrixOptimizedSolution {

    public boolean searchMatrix(int[][] matrix, int target) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        P74V2SearchA2DMatrixOptimizedSolution solution = new P74V2SearchA2DMatrixOptimizedSolution();
        int[][] m = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println(solution.searchMatrix(m, 3));   // true
        System.out.println(solution.searchMatrix(m, 13));  // false
        System.out.println(solution.searchMatrix(new int[][]{{1}}, 1)); // true
    }

    /*
    === Guiding questions ===

    這題的關鍵洞察就一句話，先想通它，程式碼幾乎就是 704 原封不動。

    1. 兩個條件（每行遞增 + 每行首 > 上一行尾）合起來，代表如果你把整個矩陣
       「一行接一行攤平成一維陣列」，這條陣列是不是完全排序的？

    2. 既然攤平後是排序陣列，你就可以對 index k ∈ [0, m*n - 1] 做二分。
       問題只剩：拿到 mid = k，怎麼把這個「一維 index」換算回矩陣的 (row, col)？
       （提示：row 和 col 各跟 n（每行幾個）有什麼除法/取餘關係？）

    3. 換算出 (row, col) 後拿 matrix[row][col] 跟 target 比 —— 剩下的收縮邏輯
       跟 704 完全一樣。你甚至可以直接呼叫你 704 的思路。

    4. 另一種寫法：先對「每行最後一個元素」二分找出 target 可能落在哪一行，
       再在那一行內二分。兩種都寫得出來的話，想想哪種邊界比較不容易錯。

    自測：target=13 落在 11 和 16 之間（不存在），你的二分結束時會不會誤報 true？
    */
}
