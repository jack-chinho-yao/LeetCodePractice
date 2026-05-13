package interview2026.mockprep.collections;

import java.util.*;

/*
 * ============================================================================
 * Problem 1 — Most Recent N Trades Per Trader
 * ============================================================================
 *
 * CONTEXT:
 *   An exchange clearing system needs to retrieve the N most recent trades for
 *   each trader in INSERTION ORDER (oldest -> newest) for end-of-day
 *   reconciliation. When a trader exceeds the per-trader limit, the OLDEST
 *   trade must be evicted.
 *
 * REQUIREMENTS:
 *   1. Constructor takes `limit` = max recent trades to keep per trader.
 *   2. recordTrade(traderId, tradeId)
 *        - Records a new trade for that trader.
 *        - If the trader already has `limit` trades, EVICT the oldest one
 *          before adding the new one.
 *   3. getRecentTrades(traderId)
 *        - Returns the trader's trades in INSERTION ORDER (oldest -> newest).
 *        - Returns an empty list if the trader has no trades.
 *
 * EXAMPLE:
 *   RecentTradesTracker tracker = new RecentTradesTracker(3);
 *   tracker.recordTrade("T1", "TR001");
 *   tracker.recordTrade("T1", "TR002");
 *   tracker.recordTrade("T1", "TR003");
 *   tracker.recordTrade("T1", "TR004");   // evicts TR001
 *   tracker.getRecentTrades("T1");        // -> [TR002, TR003, TR004]
 *
 *   tracker.recordTrade("T2", "TR100");
 *   tracker.getRecentTrades("T2");        // -> [TR100]
 *   tracker.getRecentTrades("UNKNOWN");   // -> []
 *
 * HINTS (no spoilers):
 *   - You need TWO levels of structure:
 *       outer = Map<traderId, ?>
 *       inner = something that preserves INSERTION ORDER and can remove the
 *               OLDEST element in O(1).
 *   - Your focus-area list mentions LinkedHashMap and LinkedHashSet — both
 *     can work. Pick one and be ready to defend WHY in the explain step.
 *
 * WHAT TO IMPLEMENT:
 *   public RecentTradesTracker(int limit)
 *   public void recordTrade(String traderId, String tradeId)
 *   public List<String> getRecentTrades(String traderId)
 *
 * DON'T FORGET:
 *   - main() with the example above for local sanity check (per CLAUDE.md).
 *   - After you finish, I'll ask you to:
 *       1. Walk me through your approach out loud (pair-coding rehearsal)
 *       2. List edge cases you may have missed
 *       3. Name >= 3 JUnit test cases you'd write
 * ============================================================================
 */
public class RecentTradesTrackerV2 {
    private final int limit;
    private final Map<String, LinkedList<String>> map;

    public RecentTradesTrackerV2(int limit){
        if (limit <= 0){
            throw new IllegalArgumentException("limit should be > 0");
        }
        this.limit = limit;
        this.map = new HashMap<>();
    }

    public void recordTrade(String traderId, String tradeId){
        LinkedList<String> trades = this.map.computeIfAbsent(traderId, k -> new LinkedList<>());
        trades.add(tradeId);
        if (trades.size() > limit){
            trades.removeFirst();
        }
    }

    public List<String> getRecentTrades(String traderId){
        LinkedList<String> trades = map.get(traderId);
        return trades == null ? new ArrayList<>() : List.copyOf(trades);
    }

    public static void main(String[] args) {
        RecentTradesTrackerV2 tracker = new RecentTradesTrackerV2(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        tracker.recordTrade("T1", "TR003");
        tracker.recordTrade("T1", "TR004");   // evicts TR001

        List<String> t1 = tracker.getRecentTrades("T1");
        System.out.println(t1);        // -> [TR002, TR003, TR004]
        tracker.recordTrade("T2", "TR100");
        List<String> t2 = tracker.getRecentTrades("T2");// -> [TR100]

        System.out.println(tracker.getRecentTrades("UNKNOWN"));// -> []
    }
}
