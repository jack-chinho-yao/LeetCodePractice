package interview2026.mockprep.collections;

import java.util.*;

/**
 * Q1 - HashMap 統計：計算每個 trader 的總交易金額，找出最高的 trader
 */
public class Q1HashMapTraderSum {

    public static void main(String[] args) {
        List<String[]> transactions = Arrays.asList(
            new String[]{"T001", "500"},
            new String[]{"T002", "300"},
            new String[]{"T001", "200"},
            new String[]{"T003", "800"},
            new String[]{"T002", "150"}
        );

        // TODO: 用 HashMap 統計每個 trader 的總金額
        Map<String, Integer> map = new HashMap<>();
        for(String[] e: transactions){
            String trader = e[0];
            Integer sum = Integer.valueOf(e[1]);
            map.put(trader, sum + map.getOrDefault(trader, 0));
        }


        String maxTrader = null;
        Integer max = 0;

        // TODO: 印出每個 trader 的總金額
        for(String key : map.keySet()){
            Integer balance = map.get(key);
            System.out.println("Trader " + key + " : " + balance);
            if (balance >= max){
                maxTrader = new String(key);
                max = balance;
            }
        }

        // TODO: 找出金額最高的 trader
        System.out.println("The highest balance trader is " + maxTrader);

    }
}
