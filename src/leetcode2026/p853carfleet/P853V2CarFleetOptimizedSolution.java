package leetcode2026.p853carfleet;

import java.util.Arrays;

public class P853V2CarFleetOptimizedSolution {

    // Sort by position desc + monotonic stack on arrival time - O(n log n) Time, O(n) Space
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;

        // 1. 把車子按 position 由大到小（最靠近 target 的在前）排序
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> position[b] - position[a]);

        // 2. 從最前面的車往後掃，維護「目前領頭車隊的到站時間」
        int fleets = 0;
        double leadTime = 0.0;   // 當前領頭車隊抵達 target 的時間
        for (int i : idx) {
            double time = (double) (target - position[i]) / speed[i];
            // 後車到站時間 > 領頭時間 → 追不上 → 自成新車隊，成為新的領頭
            if (time > leadTime) {
                fleets++;
                leadTime = time;
            }
            // 否則 → 會在抵達前追上 → 併入同一車隊（不增加數量）
        }

        return fleets;
    }

    public static void main(String[] args) {
        P853V2CarFleetOptimizedSolution solution = new P853V2CarFleetOptimizedSolution();

        // Example 1: expected 3
        System.out.println(solution.carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));
        // Example 2: expected 1
        System.out.println(solution.carFleet(10, new int[]{3}, new int[]{3}));
        // Example 3: expected 1
        System.out.println(solution.carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));
    }
}

/*
    ============================================================================
    LeetCode 853. Car Fleet  —  Optimized
    ============================================================================

    題目同 V1（見 P853V1CarFleetNaiveSolution 底部）。

    ----------------------------------------------------------------------------
    最佳解思路提示（自己想，想不出來再看）：

      1. 把每台車打包成 (position, timeToTarget)，其中
             timeToTarget = (double)(target - position) / speed
      2. 依 position「由大到小」(最靠近 target 的在最前面) 排序。
      3. 從最前面那台車往後掃，維護「目前領頭車隊的到站時間」：
           - 若後車的到站時間 <= 前面領頭車隊的到站時間
             → 它會在抵達前追上 → 併入同一車隊（不增加數量）。
           - 否則 → 它永遠追不上 → 自成新車隊（數量 +1，並成為新的領頭時間）。
      4. 車隊數量即為答案。

      為什麼是單調堆疊 (monotonic stack) 的變形？
        到站時間若比堆疊頂端「小或相等」會被吸收，只有「更大」的才壓入 ——
        堆疊大小 = 車隊數。實作上常只需記一個 "目前最大到站時間" 變數即可。

      小陷阱：用 double 比較；position 要連同 speed 一起排序，別只排 position。
*/
