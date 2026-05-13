package interview2026.mockprep.collections;

import java.util.*;

/**
 * Q1 - HashMap 統計：計算每個 trader 的總交易金額，找出最高的 trader
 */
public class Q1V2HashMapTraderSum {

    public static void main(String[] args) {
        List<String[]> transactions = Arrays.asList(
            new String[]{"T001", "500"},
            new String[]{"T002", "300"},
            new String[]{"T001", "200"},
            new String[]{"T003", "800"},
            new String[]{"T002", "150"}
        );

        // "I'll use a HashMap to sum the total amount per trader."
        Map<String, Integer> traderTotals = new HashMap<>();
        // "I'll iterate through each transaction, parse the amount, and accumulate it using getOrDefault to handle the first occurrence."
        for (String[] txn : transactions) {
            String traderId = txn[0];
            int amount = Integer.parseInt(txn[1]);
            traderTotals.put(traderId, amount + traderTotals.getOrDefault(traderId, 0));
        }

        // "Now I need to find the trader with the highest total. I'll track that while printing the results."
        String maxTrader = null;
        int maxAmount = 0;

        // "I'll use entrySet to iterate, so I get both key and value without an extra map lookup."
        for (Map.Entry<String, Integer> entry : traderTotals.entrySet()) {
            String traderId = entry.getKey();
            int total = entry.getValue();
            System.out.println("Trader " + traderId + " : " + total);
            // "While iterating, I compare each total to the current max to find the highest trader."
            if (total >= maxAmount) {
                maxTrader = traderId;
                maxAmount = total;
            }
        }

        // "Finally, I print the trader with the highest total amount."
        System.out.println("The highest balance trader is " + maxTrader);


        System.out.println("==========VVVVVVVVVV order by key asc");
        traderTotals.entrySet().stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
        System.out.println("==========VVVVVVVVVV order by key desc");
        traderTotals.entrySet().stream()
                .sorted((a, b) -> b.getKey().compareTo(a.getKey()))
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
        System.out.println("==========VVVVVVVVVV order by value asc");
        traderTotals.entrySet().stream().sorted((a, b) -> a.getValue().compareTo(b.getValue()))
                        .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
        System.out.println("==========VVVVVVVVVV order by value desc");
        traderTotals.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));


    }
}
