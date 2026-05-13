package leetcode2026.p167twosumii;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Brute force: try every pair. Time O(n^2), Space O(1).
public class P167V1TwoSumIINaiveSolution {

    public int[] twoSum(int[] numbers, int target) {
       return null;
    }

    public static void main(String[] args) {
        P167V1TwoSumIINaiveSolution solution = new P167V1TwoSumIINaiveSolution();
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


/*
*
*  我的想法是right 1 開始 left 0 開始 然後right ++ 測試到 right + left > target 重置 left ++
  right 從left + 1 開始 一樣的loop直到找到答案return

⏺ 你這個想法邏輯是對的，能找到答案，但還是 O(n²)，沒有真的「吃到」sorted 給你的好處。我畫給你看差在哪。

  你的做法（同側雙指針 + 早停）

  left=0, right 從 left+1 開始往右走
    sum > target → break，left++，right 重置到 left+1
    sum == target → return

  用 [2, 3, 4, 7, 11, 15], target = 18：

  left=0(2): right走 3→4→7→11→15 ...  sum永遠沒>18  → 全掃一輪
  left=1(3): right重置到4, 走 4→7→11→15  → 再掃一輪
  left=2(4): right重置到7, 走 7→11→15   → 再掃
  left=3(7): right=11, 7+11=18 ✅ return

  問題：每次 left++，right 就丟掉之前所有資訊，從頭走。這其實就是暴力法只有加個早停優化而已 → worst case 還是 O(n²)。

  ---
  真正的 two pointers（頭尾雙指針）

  關鍵差異：left 從 0，right 從「最後一個」開始，兩邊往中間夾。

  left=0, right=n-1
    sum < target → left++   （左邊那個太小，換大一點的）
    sum > target → right--  （右邊那個太大，換小一點的）
    sum == target → return

  同一個例子 [2, 3, 4, 7, 11, 15], target = 18：

   ↓                            ↓
  [2,  3,  4,  7,  11,  15]  sum=2+15=17 <18  → left++
       ↓                       ↓
  [2,  3,  4,  7,  11,  15]  sum=3+15=18 ✅ return [2, 6]

  只用 2 步，兩個指針加起來最多走 n 步 → O(n)。

  ---
  為什麼頭尾雙指針不用重置？

  核心洞察：當 sum 過大，問題只出在「右邊那個」上，跟左邊無關。

  [2,  3,  4,  7,  11,  15], target = 10
   ↑                     ↑
  left                   right
  sum = 2 + 15 = 17 > 10

  現在你要變小。注意：
  - 如果你移 left++（變 3），sum = 3+15 = 18，反而更大 ❌
  - 只能 right--（變 11），sum = 2+11 = 13，才有可能變小 ✅                                                                                                                                                   ─

  而且當你做 right-- 的瞬間，你等於永久排除了整個 15 配任何東西的可能性——因為 15 配最小的 2 都超了，配更大的更不可能。

  一次移動 = 永久刪掉整排或整列 → 每個 index 最多被「路過」一次 → O(n)。

  ---
  兩種寫法對照表

  ┌──────────────┬───────────────────────────┬─────────────────────┐
  │              │  你的寫法（同側 + 早停）  │     頭尾雙指針      │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ left 起點    │ 0                         │ 0                   │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ right 起點   │ left+1                    │ n-1                 │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ sum 太小怎辦 │ right++                   │ left++              │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ sum 太大怎辦 │ break, left++, right 重置 │ right-- 不重置      │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ 資訊重用     │ ❌ 每次重頭開始           │ ✅ 指針只前進不回頭 │
  ├──────────────┼───────────────────────────┼─────────────────────┤
  │ 最壞時間     │ O(n²)                     │ O(n)                │
  └──────────────┴───────────────────────────┴─────────────────────┘

  ---
  記法

  ▎ 頭尾雙指針的本質是：左指針 = 目前候選的最小值、右指針 = 目前候選的最大值。每次比較完，砍掉其中一邊最極端的那個。
  ▎
  ▎ 你的寫法等於只有一個「最小值候選」，另一個從頭再找 → 沒砍掉東西，只是加速檢查。

  兩邊往中間夾，才是真正吃到 sorted 紅利的方式。
*
* */