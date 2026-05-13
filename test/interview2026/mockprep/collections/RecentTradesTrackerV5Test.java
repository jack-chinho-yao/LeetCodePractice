package interview2026.mockprep.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RecentTradesTrackerV5Test {

    /** 命名 沒固定看團隊 但是要有 測什麼、什麼條件、預期結果
     * EN: Constructor must reject a non-positive limit by throwing IllegalArgumentException.
     *     Catches: a missing input-validation guard. limit=0 would never store any trade,
     *     and a negative limit makes the eviction check (size > limit) always false,
     *     leading to unbounded growth.
     * <p>
     * 中: Constructor 必須對 limit ≤ 0 拋 IllegalArgumentException。
     *     抓的 bug: 缺少輸入檢查 — limit=0 會導致永遠存不到任何 trade，
     *     limit 為負數會讓 eviction 檢查 (size > limit) 永遠是 false，
     *     導致無限制增長。
     */
    @Test
    void test_constructor_throwsIllegalArgumentException_whenLimitIsZeroOrNegative(){
        assertThrows(IllegalArgumentException.class, () -> new RecentTradesTrackerV5(0));
        assertThrows(IllegalArgumentException.class, () -> new RecentTradesTrackerV5(-1));
    }

    /**
     * EN: recordTrade must reject a null traderId by throwing NullPointerException
     *     (Effective Java + JDK convention via Objects.requireNonNull).
     *     Catches: a missing null-check that would silently insert a null key into the
     *     HashMap (HashMap allows null keys), causing data corruption downstream.
     * <p>
     * 中: recordTrade 必須對 null traderId 拋 NullPointerException
     *     (依照 Effective Java + JDK 慣例，使用 Objects.requireNonNull)。
     *     抓的 bug: 缺少 null check 會讓 HashMap 默默接受 null key
     *     (HashMap 允許 null key)，造成下游資料污染。
     */
    @Test
    void test_recordTrade_throwsNullPointerException_whenTraderIdIsNull(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        assertThrows(NullPointerException.class, () -> tracker.recordTrade(null, "TR001"));
    }

    /**
     * EN: recordTrade must reject a null tradeId EXPLICITLY via Objects.requireNonNull,
     *     not by relying on ArrayDeque's incidental null-rejection.
     *     Catches: a "passing test for the wrong reason" — if someone later swaps
     *     ArrayDeque for LinkedList (which permits nulls), the implicit guarantee
     *     vanishes silently. An explicit requireNonNull keeps the contract independent
     *     of the underlying collection choice.
     * <p>
     * 中: recordTrade 必須**明確**透過 Objects.requireNonNull 拒絕 null tradeId，
     *     不能依賴 ArrayDeque 拒絕 null 元素這個副作用。
     *     抓的 bug: 「test 因為錯誤的理由而綠燈」— 如果之後有人把 ArrayDeque 換成
     *     LinkedList (允許 null)，原本的隱含保證會悄悄消失。明確的 requireNonNull
     *     讓 contract 獨立於底層資料結構的選擇。
     */
    @Test
    void test_recordTrade_throwsNullPointerException_whenTradeIdIsNull(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        assertThrows(NullPointerException.class, () -> tracker.recordTrade("T1", null));
    }

    /**
     * EN: Recording the same tradeId twice for the same trader must throw
     *     IllegalStateException — the arguments are valid but the object's state
     *     does not allow the operation. This is a defensive check against upstream
     *     matching-engine bugs.
     *     Catches: silent acceptance of duplicate trades, which in an exchange clearing
     *     system would corrupt end-of-day reconciliation.
     * <p>
     * 中: 同一個 trader 兩次 record 同一個 tradeId 必須拋 IllegalStateException —
     *     參數本身合法，但**物件目前的狀態**不允許這個操作。這是對上游
     *     matching engine bug 的防禦性檢查。
     *     抓的 bug: 默默接受重複交易 — 在 交易所清算系統會搞壞日終對帳。
     */
    @Test
    void test_recordTrade_throwsIllegalStateException_whenTradeIdIsDuplicate(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);

        assertThrows(IllegalStateException.class, () -> {
            tracker.recordTrade("T1", "TR001");
            tracker.recordTrade("T1", "TR001");
        });
    }

    /**
     * EN: When fewer trades than the limit are recorded, all trades must be returned
     *     in insertion order (oldest → newest), with NO eviction triggered.
     *     Catches: premature eviction or wrong return order on the happy path.
     * <p>
     * 中: 當 record 的交易筆數**少於** limit 時，必須按插入順序 (oldest → newest)
     *     回傳全部交易，且**不觸發** eviction。
     *     抓的 bug: 在 happy path 上提前 evict，或回傳順序錯誤。
     */
    @Test
    void test_recordTrade_preservesInsertionOrder_whenBelowLimit(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        assertEquals(List.of("TR001", "TR002"), tracker.getRecentTrades("T1"));
    }

    /**
     * EN: When EXACTLY `limit` trades are recorded, all of them must remain — no eviction.
     *     This is the boundary test that distinguishes `size > limit` (correct)
     *     from `size >= limit` (off-by-one bug).
     *     Catches: the classic off-by-one error where the oldest trade gets evicted
     *     one step too early. With limit=3 and 3 inserts, a buggy `>=` check would
     *     evict TR001 and return [TR002, TR003] instead of [TR001, TR002, TR003].
     * <p>
     * 中: 當 record 剛好等於 limit 筆交易時，必須**全部保留** — 不能 evict。
     *     這是區分 `size > limit` (正確) 和 `size >= limit` (差一錯誤) 的邊界 test。
     *     抓的 bug: 經典差一錯誤 — 最舊的交易被提前一步 evict。
     *     在 limit=3 + 3 筆插入下，有 bug 的 `>=` 檢查會 evict TR001，
     *     回傳 [TR002, TR003] 而不是 [TR001, TR002, TR003]。
     */
    @Test
    void test_recordTrade_keepsAllTrades_whenSizeEqualsLimit(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        tracker.recordTrade("T1", "TR003");
        assertEquals(List.of("TR001", "TR002", "TR003"), tracker.getRecentTrades("T1"));
    }

    /**
     * EN: When more than `limit` trades are recorded, the OLDEST trade must be evicted
     *     and the remaining trades must stay in insertion order.
     *     Catches: wrong eviction direction (e.g. evicting newest instead of oldest
     *     by calling removeLast() instead of removeFirst()), or order corruption
     *     after eviction (e.g. accidentally re-sorting the deque).
     * <p>
     * 中: 當 record 的交易超過 limit 時，**最舊**的交易必須被 evict，
     *     其餘交易必須保持插入順序。
     *     抓的 bug: evict 方向錯誤 (例如不小心用 removeLast() 而非 removeFirst()
     *     導致 evict 最新而非最舊)，或 evict 後順序錯亂 (例如不小心重新排序 deque)。
     */
    @Test
    void test_recordTrade_evictsOldestTrade_whenExceedsLimit(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        tracker.recordTrade("T1", "TR003");
        tracker.recordTrade("T1", "TR004");
        assertEquals(List.of("TR002", "TR003", "TR004"), tracker.getRecentTrades("T1"));
    }

    /**
     * EN: Each trader's recent-trade list must be fully isolated. Even when one trader
     *     overflows and triggers eviction, other traders' lists must remain untouched.
     *     This is the strongest version of the multi-trader contract — T1 deliberately
     *     overflows (4 inserts at limit=3), and T2 must still hold its untouched [TR001, TR002].
     *     Catches: shared-state bugs — e.g. accidentally declaring the deque `static`,
     *     using a single shared deque across traders, or a key collision in the map.
     * <p>
     * 中: 每個 trader 的最近交易 list 必須**完全隔離**。即使某個 trader overflow
     *     觸發 eviction，其他 trader 的 list 也必須完全不受影響。
     *     這是 multi-trader contract 的**最強版本** — T1 故意 overflow
     *     (limit=3 但插入 4 筆)，T2 仍然必須持有完整的 [TR001, TR002]。
     *     抓的 bug: 共享狀態的 bug — 例如不小心宣告 deque 為 `static`、
     *     多個 trader 共用同一個 deque、或 map 的 key collision。
     */
    @Test
    void test_recordTrade_keepsTradersIsolated_whenOneTraderOverflows(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        tracker.recordTrade("T1", "TR003");
        tracker.recordTrade("T1", "TR004");

        tracker.recordTrade("T2", "TR001");
        tracker.recordTrade("T2", "TR002");

        assertEquals(List.of("TR002", "TR003", "TR004"), tracker.getRecentTrades("T1"));
        assertEquals(List.of("TR001", "TR002"), tracker.getRecentTrades("T2"));
    }

    /**
     * EN: When limit is 1 (the extreme boundary), every new trade must immediately
     *     evict the previous one. Stepwise assertions verify the invariant after
     *     EACH insert, so a failure points directly to the exact insert that broke it.
     *     Catches: off-by-one bugs at the smallest possible limit. limit=1 is an
     *     "off-by-one magnet" — many implementations work for limit=3 but break at limit=1
     *     because the eviction window is exactly 1 step wide.
     * <p>
     * 中: 當 limit 為 1 (極端邊界) 時，每筆新交易都必須**立刻** evict 前一筆。
     *     用 stepwise assertion 在**每次**插入後驗證 invariant，
     *     如此一來失敗時可以直接定位是哪一次插入壞掉。
     *     抓的 bug: 最小可能 limit 下的差一錯誤。limit=1 是「差一錯誤的磁鐵」—
     *     很多實作在 limit=3 時正常，但在 limit=1 時壞掉，
     *     因為 eviction window 只有 1 步寬。
     */
    @Test
    void test_recordTrade_keepsOnlyLatestTrade_whenLimitIsOne(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(1);
        tracker.recordTrade("T1", "TR001");
        assertEquals(List.of("TR001"), tracker.getRecentTrades("T1"));
        tracker.recordTrade("T1", "TR002");
        assertEquals(List.of("TR002"), tracker.getRecentTrades("T1"));
        tracker.recordTrade("T1", "TR003");
        assertEquals(List.of("TR003"), tracker.getRecentTrades("T1"));
    }

    /**
     * EN: getRecentTrades for a trader who has NEVER been recorded must return an
     *     empty list — not null, not throw. Querying must NOT have any side effect
     *     on the internal map (no entry created for the unknown trader).
     *     Catches: (a) returning null instead of empty list (NPE downstream),
     *     (b) accidentally using computeIfAbsent in the read path which would silently
     *     create an empty entry for every unknown query (memory leak + state pollution).
     * <p>
     * 中: 對**從未** record 過的 trader 呼叫 getRecentTrades 必須回傳空 list —
     *     不能是 null、不能 throw。query 操作**不能**對內部 map 有任何副作用
     *     (不能因為 query 就為這個 unknown trader 建立 entry)。
     *     抓的 bug: (a) 回傳 null 而非空 list (下游 NPE)，
     *     (b) 在 read path 不小心用了 computeIfAbsent，會為每個 unknown query
     *     默默建立空 entry (記憶體洩漏 + state 污染)。
     */
    @Test
    void test_getRecentTrades_returnsEmptyList_whenTraderNeverRecorded(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        List<String> t1 = tracker.getRecentTrades("UNKNOWN");
        assertNotNull(t1);
        assertEquals(List.of(), t1);
    }

    /**
     * EN: getRecentTrades must return an immutable snapshot — TWO contracts in one test:
     *     (1) Immutability: caller cannot mutate the returned list — t1.add(...) must
     *         throw UnsupportedOperationException.
     *     (2) Snapshot stability: subsequent recordTrade calls must NOT mutate the
     *         previously returned list — it is a copy, not a live view.
     *     The third assertion confirms the internal state still updates correctly,
     *     proving the snapshot was a copy and not a stale alias.
     *     Catches: returning the internal deque directly (mutable AND a live view),
     *     returning a wrapper that exposes mutation methods (e.g. Collections.unmodifiableList
     *     of a live deque — immutable but still a live view, fails contract #2),
     *     or a no-op getRecentTrades that always returns the same stale list.
     * <p>
     * 中: getRecentTrades 必須回傳一個**不可變的快照** — 一個 test 涵蓋兩個 contract：
     *     (1) 不可變性: 呼叫者不能 mutate 回傳的 list — t1.add(...) 必須拋
     *         UnsupportedOperationException。
     *     (2) 快照穩定性: 後續的 recordTrade 不能影響之前回傳的 list —
     *         它是 copy，不是 live view。
     *     第三個 assertion 確認內部 state 仍然有正確更新，
     *     證明 snapshot 確實是 copy 而不是 stale 的 alias。
     *     抓的 bug: 直接回傳內部 deque (mutable 且是 live view)，
     *     回傳一個暴露 mutation 方法的 wrapper (例如對 live deque 做
     *     Collections.unmodifiableList — 不可變但仍是 live view，違反 contract #2)，
     *     或一個總是回傳同一個 stale list 的 no-op getRecentTrades。
     */
    @Test
    void test_getRecentTrades_returnsImmutableSnapshot(){
        RecentTradesTrackerV5 tracker = new RecentTradesTrackerV5(3);
        tracker.recordTrade("T1", "TR001");
        tracker.recordTrade("T1", "TR002");
        tracker.recordTrade("T1", "TR003");
        List<String> t1 = tracker.getRecentTrades("T1");
        assertThrows(UnsupportedOperationException.class, () -> t1.add("Test"));
        assertEquals(List.of("TR001", "TR002", "TR003"), t1);

        tracker.recordTrade("T1", "TR004");
        assertEquals(List.of("TR001", "TR002", "TR003"), t1);
        assertEquals(List.of("TR002", "TR003", "TR004"), tracker.getRecentTrades("T1"));
    }


}

         /*
⏺ 歡迎回來。Q5 — Problem 1 Thread Safety，純口頭問答。

        ---
規則先講

  1. 不要動 V5 的 code — 純口頭討論
  2. 像在白板前面對 interviewer 一樣回答 — 完整句子，不要只丟關鍵字
  3. 詞彙必須精準 — 講錯詞我會立刻打斷糾正（昨天的「stackoverflow」教訓還記得吧）
        4. 我會給一個主問題 + 幾個追問。你回答完一個我才追下一個。

        ---
        🎤 主問題

  ▎ "OK, you've shown me your RecentTradesTrackerV5. Now imagine this runs in a real exchange clearing system where multiple
          ▎ matching-engine threads call recordTrade simultaneously for different traders, and reporting threads call getRecentTrades at
  ▎  the same time.
  ▎
          ▎ Walk me through what happens. Is your current implementation thread-safe? If not, tell me specifically what breaks, what
  ▎ you'd change, and why."

        ---

        ▎ 「OK，你給我看了你的 RecentTradesTrackerV5。現在想像這個在真實的交易所清算系統裡跑，會有多個 matching engine thread
  ▎ 同時對不同 trader 呼叫 recordTrade，同時又有 reporting thread 在呼叫 getRecentTrades。
        ▎
        ▎ 跟我講一遍會發生什麼事。你現在的實作是 thread-safe 的嗎？如果不是，具體哪裡會壞**、你會改什麼、為什麼？」**

        ---
我希望你的回答涵蓋這些（不要全部一次講出來，我會追問）

        - ❓ 哪一行/哪個物件是 unsafe 的（精確指出，不是「整個都不安全」）
        - ❓ HashMap 在並發下的具體 failure mode 叫什麼
  - ❓ 「即使我換成 concurrent collection 也不夠」的理由是什麼（這是 senior 跟 junior 的分水嶺）
        - ❓ 你的 fix 用什麼鎖策略，這個策略有名字叫什麼
  - ❓ 為什麼不用 ConcurrentLinkedDeque（要有具體技術理由，不是「我比較喜歡 ArrayDeque」）
        - ❓ getRecentTrades 要不要動？為什麼？

        ---
開始口頭回答。 慢慢來，想清楚再講。我會抓你的詞彙跟邏輯漏洞。      */