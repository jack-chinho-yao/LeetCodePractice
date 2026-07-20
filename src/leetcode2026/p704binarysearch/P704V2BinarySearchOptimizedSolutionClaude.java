package leetcode2026.p704binarysearch;

// Binary search, half-open interval [lo, hi). Time O(log n), Space O(1).
public class P704V2BinarySearchOptimizedSolutionClaude {

    public int search(int[] nums, int target) {
        int lo = 0;             // inclusive: first index that could still be the answer
        int hi = nums.length;   // exclusive: one past the last candidate (a fence, never an index)
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;   // overflow-safe midpoint
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                lo = mid + 1;   // mid ruled out; lo is inclusive, so step past it
            } else {
                hi = mid;       // mid ruled out; hi is exclusive, so mid is already outside
            }
        }
        return -1;              // loop ends when lo == hi → range empty → not found
    }

    public static void main(String[] args) {
        P704V2BinarySearchOptimizedSolutionClaude solution = new P704V2BinarySearchOptimizedSolutionClaude();
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));  // 4
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));  // -1
        System.out.println(solution.search(new int[]{5}, 5));                   // 0
    }

    /*
    === Discussion Notes ===

    這是 704 的參考解,跟你自己寫的 P704V2...OptimizedSolution 本質相同,
    都是「半開區間 [lo, hi)」流派。差別只有兩個小 polish:

    1. mid 用 lo + (hi - lo) / 2,不用 (lo + hi) / 2。
       兩者算出來一樣,但前者中間值永遠不超過 hi → 不會整數溢位。
       (lo + hi 在 lo/hi 接近 Integer.MAX_VALUE 時會溢位成負數 → mid 爆掉。
        Java 官方 Arrays.binarySearch 早年就出過這個 bug,之後才改成這寫法。)

    2. mid 在迴圈「一進去就算」,而不是放結尾。邏輯等價,只是讀起來
       「每一輪都重算中點」的意圖更明顯。

    === 半開區間的三件套(綁死,要一致) ===
      hi = nums.length     (exclusive fence)
      while (lo < hi)       (相等時範圍為空 → 結束)
      hi = mid             (不用 mid - 1,因為 hi 本來就不包含)
      lo = mid + 1         (要 +1,因為 lo 包含,得把已排除的 mid 踢掉)

    左右不對稱的原因:lo 是「包含」的邊界、hi 是「不包含」的邊界。
    兩個分支都在做同一件事 —— 把比較過的 mid 踢出範圍 ——
    只是 hi 那側「不含」的性質幫你免費踢掉了 mid,lo 那側得自己 +1。

    === 另一派:閉區間 [lo, hi] ===
      hi = nums.length - 1
      while (lo <= hi)
      hi = mid - 1
      lo = mid + 1
    兩派都自洽,但絕對不能混用(混用 = 無限迴圈 / 越界)。

    Complexity:
      Time : O(log n) — 每輪砍掉一半
      Space: O(1)
    */
}
