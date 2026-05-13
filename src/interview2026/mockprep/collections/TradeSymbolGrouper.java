package interview2026.mockprep.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
 * ============================================================================
 * Problem 2 — Group Trades by Symbol
 * ============================================================================
 *
 * CONTEXT:
 *   An exchange clears trades across multiple energy commodity symbols
 *   (e.g. "NGX-AB-E" = Alberta Electricity, "NGX-AB-G" = Alberta Gas,
 *   "WTI" = West Texas Intermediate crude).
 *
 *   For end-of-day reporting, the back office needs to:
 *   - Look up all trades for a given symbol
 *   - Know the total traded volume (sum of amounts) per symbol
 *   - List all active symbols
 *
 * REQUIREMENTS:
 *   1. Constructor takes no arguments.
 *   2. recordTrade(String tradeId, String symbol, double amount)
 *        - Records a trade with its ID, commodity symbol, and dollar amount.
 *   3. getTradeIdsBySymbol(String symbol) -> List<String>
 *        - Returns all tradeIds for that symbol in INSERTION ORDER.
 *        - Returns an empty list if no trades exist for that symbol.
 *   4. getTotalVolume(String symbol) -> double
 *        - Returns the sum of amounts for all trades of that symbol.
 *        - Returns 0.0 if no trades exist for that symbol.
 *   5. getAllSymbols() -> Set<String>
 *        - Returns the set of all symbols that have at least one trade.
 *
 * EXAMPLE:
 *   TradeSymbolGrouper grouper = new TradeSymbolGrouper();
 *   grouper.recordTrade("TR001", "NGX-AB-E", 150000.0);
 *   grouper.recordTrade("TR002", "NGX-AB-G", 230000.0);
 *   grouper.recordTrade("TR003", "NGX-AB-E", 175000.0);
 *   grouper.recordTrade("TR004", "WTI",      500000.0);
 *   grouper.recordTrade("TR005", "NGX-AB-E", 120000.0);
 *
 *   grouper.getTradeIdsBySymbol("NGX-AB-E");  // -> [TR001, TR003, TR005]
 *   grouper.getTradeIdsBySymbol("WTI");       // -> [TR004]
 *   grouper.getTradeIdsBySymbol("UNKNOWN");   // -> []
 *
 *   grouper.getTotalVolume("NGX-AB-E");       // -> 445000.0
 *   grouper.getTotalVolume("UNKNOWN");        // -> 0.0
 *
 *   grouper.getAllSymbols();                   // -> {NGX-AB-E, NGX-AB-G, WTI}
 *
 * DESIGN DECISIONS (think about these before coding):
 *   - Do you need ONE map or TWO? (tradeIds in a list vs. running volume total)
 *   - For getTotalVolume: iterate and sum at query time, or maintain a
 *     running total on each recordTrade? What's the trade-off?
 *   - Should you validate inputs? (null tradeId, null symbol, negative amount?)
 *   - Should returned List/Set be immutable snapshots?
 *
 * WHAT TO IMPLEMENT:
 *   public TradeSymbolGrouper()
 *   public void recordTrade(String tradeId, String symbol, double amount)
 *   public List<String> getTradeIdsBySymbol(String symbol)
 *   public double getTotalVolume(String symbol)
 *   public Set<String> getAllSymbols()
 *
 * DON'T FORGET:
 *   - main() with the example above for local sanity check (per CLAUDE.md).
 *   - After you finish, I'll ask you to:
 *       1. Walk me through your approach out loud (pair-coding rehearsal)
 *       2. Complexity analysis
 *       3. List edge cases
 *       4. Name >= 3 JUnit test cases
 *       5. Thread safety discussion
 * ============================================================================
 */
public class TradeSymbolGrouper {
    private ConcurrentHashMap<String, List<ConcurrentHashMap<String, Double>>> symbolToTradeIdMap;

    // TODO: your fields here

    public TradeSymbolGrouper() {
        symbolToTradeIdMap = new ConcurrentHashMap();
    }

    public void recordTrade(String tradeId, String symbol, double amount) {
        List<ConcurrentHashMap<String, Double>> list = symbolToTradeIdMap.computeIfAbsent(symbol, k -> new ArrayList<>());
        ConcurrentHashMap<String, Double> tradeIdToAmount = new ConcurrentHashMap<>();
        tradeIdToAmount.put(tradeId, amount);
        list.add(tradeIdToAmount);
        symbolToTradeIdMap.put(symbol, list);
    }

    public List<String> getTradeIdsBySymbol(String symbol) {
        List<String> list = new ArrayList<>();
        for(Map.Entry<String, List<ConcurrentHashMap<String, Double>>> entry: symbolToTradeIdMap.entrySet()){
            String symbolKey = entry.getKey();
            if (symbolKey.equals(symbol)){
                List<ConcurrentHashMap<String, Double>> value = entry.getValue();
                for (ConcurrentHashMap<String, Double> stringDoubleConcurrentHashMap : value) {
                    for (Map.Entry<String, Double> stringDoubleEntry : stringDoubleConcurrentHashMap.entrySet()) {
                        String tradeId = stringDoubleEntry.getKey();
                        list.add(tradeId);
                    }
                }

            }
        }
        return list;
    }

    public double getTotalVolume(String symbol) {
        Double result = 0.0;
        for(Map.Entry<String, List<ConcurrentHashMap<String, Double>>> entry1: symbolToTradeIdMap.entrySet()){
            String symbolKey = entry1.getKey();
            if (symbolKey.equals(symbol)){
                List<ConcurrentHashMap<String, Double>> entry1Value = entry1.getValue();

                for (ConcurrentHashMap<String, Double> stringDoubleConcurrentHashMap : entry1Value) {
                    for (Map.Entry<String, Double> stringDoubleEntry : stringDoubleConcurrentHashMap.entrySet()) {
                        Double value = stringDoubleEntry.getValue();
                        result += value;
                    }
                }
            }
        }
        return result;
    }

    public Set<String> getAllSymbols() {
        HashSet<String> resultSet = new HashSet<>();
        for (Map.Entry<String, List<ConcurrentHashMap<String, Double>>> entry : symbolToTradeIdMap.entrySet()) {
            resultSet.add(entry.getKey());
        }
        return resultSet;
    }

    public static void main(String[] args) {
        TradeSymbolGrouper grouper = new TradeSymbolGrouper();
        grouper.recordTrade("TR001", "NGX-AB-E", 150000.0);
        grouper.recordTrade("TR002", "NGX-AB-G", 230000.0);
        grouper.recordTrade("TR003", "NGX-AB-E", 175000.0);
        grouper.recordTrade("TR004", "WTI",      500000.0);
        grouper.recordTrade("TR005", "NGX-AB-E", 120000.0);

        System.out.println(grouper.getTradeIdsBySymbol("NGX-AB-E"));  // -> [TR001, TR003, TR005]
        System.out.println(grouper.getTradeIdsBySymbol("WTI"));       // -> [TR004]
        System.out.println(grouper.getTradeIdsBySymbol("UNKNOWN"));   // -> []

        System.out.println(grouper.getTotalVolume("NGX-AB-E"));       // -> 445000.0
        System.out.println(grouper.getTotalVolume("UNKNOWN"));        // -> 0.0

        System.out.println(grouper.getAllSymbols());                   // -> [NGX-AB-E, NGX-AB-G, WTI]
    }
}
