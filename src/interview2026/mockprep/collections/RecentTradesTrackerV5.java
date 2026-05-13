package interview2026.mockprep.collections;

import java.util.*;


public class RecentTradesTrackerV5 {
    private final int limit;
    private final Map<String, ArrayDeque<String>> map;

    public RecentTradesTrackerV5(int limit){
        if (limit <= 0){
            throw new IllegalArgumentException("limit should be > 0, got limit = " + limit);
        }
        this.limit = limit;
        this.map = new HashMap<>();
    }

    public List<String> getRecentTrades(String traderId){
        ArrayDeque<String> trades = map.get(traderId);
        return trades == null ? List.of() : List.copyOf(trades);
    }

    public void recordTrade(String traderId, String tradeId){
        Objects.requireNonNull(traderId, "traderId must not be null");
        Objects.requireNonNull(tradeId, "tradeId must not be null");

        ArrayDeque<String> trades = this.map.computeIfAbsent(traderId, k -> new ArrayDeque<>());
        synchronized (trades){
            if (trades.contains(tradeId)){
                throw new IllegalStateException("Duplicate tradeId : " + tradeId + " ,for trader : " + traderId);
            }
            trades.add(tradeId);
            if (trades.size() > limit){
                trades.removeFirst();
            }
        }


    }

}
