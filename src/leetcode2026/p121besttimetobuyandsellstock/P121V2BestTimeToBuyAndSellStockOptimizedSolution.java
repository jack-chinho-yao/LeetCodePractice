package leetcode2026.p121besttimetobuyandsellstock;

public class P121V2BestTimeToBuyAndSellStockOptimizedSolution {

    // One-pass scan, tracking running min price. Time: O(n), Space: O(1).
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for(int i = 0; i < prices.length; i++){
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(prices[i] - minPrice, maxProfit);
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        P121V2BestTimeToBuyAndSellStockOptimizedSolution solution =
                new P121V2BestTimeToBuyAndSellStockOptimizedSolution();

        System.out.println(solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // Expected: 5
        System.out.println(solution.maxProfit(new int[]{7, 6, 4, 3, 1}));    // Expected: 0
        System.out.println(solution.maxProfit(new int[]{2,4,1}));    // Expected: 2
    }
}

/*
================================================================================
LeetCode 121 — Best Time to Buy and Sell Stock
================================================================================

Problem in one sentence:
    Given daily stock prices, find the max profit from exactly ONE buy + ONE
    sell, where you must buy before you sell. If no profit is possible, return 0.

--------------------------------------------------------------------------------
What is this problem really testing?
--------------------------------------------------------------------------------
1. Can you AVOID the obvious O(n^2) brute force?
   - Brute force: try every (buy, sell) pair  -> TLE on large inputs (V1).
   - The interviewer wants to see that you realize you don't need to re-scan
     the past; you just need to REMEMBER the best past state.

2. Single-pass / running-state pattern (a core interview pattern):
   - "As I walk the array once, what's the minimum info I must carry?"
   - Here: (a) the lowest price seen so far, (b) the best profit seen so far.
   - This is the same mental move behind Kadane's algorithm (LC 53).

3. Greedy / 1-D DP in disguise:
   - dp[i] = max profit if we sell exactly on day i
          = prices[i] - min(prices[0..i])
   - Answer = max(dp[i]). We compress the "min so far" into a scalar, so
     space drops from O(n) to O(1).

4. Edge-case awareness:
   - Empty array, length 1, strictly decreasing prices -> must return 0,
     NOT a negative number. (Brute force handles this naturally; one-pass
     works because we initialize maxProfit = 0.)

--------------------------------------------------------------------------------
Why does the O(n) trick work? (Intuition)
--------------------------------------------------------------------------------
For any day i, the best sell-on-day-i profit is prices[i] - min(prices[0..i]).
Because min(prices[0..i]) only gets smaller (or stays the same) as i grows,
we can maintain it incrementally with one variable. No need to look back.

--------------------------------------------------------------------------------
Real-world applications of this pattern
--------------------------------------------------------------------------------
- Trading / finance:
    * Max single-trade PnL on a price series.
    * Max drawup / drawdown analysis (the mirror problem uses running max
      and tracks the biggest drop — same algorithm).
- Monitoring / SRE:
    * Largest latency spike above the running baseline.
    * Biggest CPU/memory jump from a recent low.
- Streaming data:
    * Works on an unbounded stream — you only keep O(1) state, so it fits
      real-time pipelines (Kafka consumers, tick data, sensor feeds).
- Any "max (later_value - earlier_value)" question over a 1-D sequence:
    * Max temperature rise in a day, max altitude gain in a hike GPS track,
      max weight gain/loss window, etc.

--------------------------------------------------------------------------------
Follow-up / family of problems (good to know they exist)
--------------------------------------------------------------------------------
- LC 122: multiple transactions allowed       -> greedy, sum positive deltas
- LC 123: at most 2 transactions              -> 2-state DP
- LC 188: at most k transactions              -> k-state DP
- LC 309: with cooldown                       -> state-machine DP
- LC 714: with transaction fee                -> state-machine DP
- LC 53 : Maximum Subarray (Kadane)           -> same single-pass DP spirit

--------------------------------------------------------------------------------
Takeaway
--------------------------------------------------------------------------------
When you see "max/min of (something_later OP something_earlier)" over an
array, ask: "Can I carry the best earlier value as a running scalar?"
If yes, you collapse O(n^2) into O(n) with O(1) extra space.
*/
