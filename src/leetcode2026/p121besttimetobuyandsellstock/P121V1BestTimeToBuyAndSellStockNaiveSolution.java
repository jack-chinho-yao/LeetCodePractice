package leetcode2026.p121besttimetobuyandsellstock;

public class P121V1BestTimeToBuyAndSellStockNaiveSolution {

    public int maxProfit(int[] prices) {
        int profit = 0;
        int temp = 0;
        for(int i = 0; i < prices.length- 1; i++){
            for(int j = i + 1; j < prices.length; j++){
                profit = Math.max(prices[j] - prices[i], profit);
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        P121V1BestTimeToBuyAndSellStockNaiveSolution solution =
                new P121V1BestTimeToBuyAndSellStockNaiveSolution();

        System.out.println(solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // Expected: 5
        System.out.println(solution.maxProfit(new int[]{7, 6, 4, 3, 1}));    // Expected: 0
    }
}