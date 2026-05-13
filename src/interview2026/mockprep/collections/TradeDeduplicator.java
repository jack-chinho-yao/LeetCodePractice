package interview2026.mockprep.collections;

import java.util.*;

/**
 * Q4 核心邏輯 — 去重並找出重複的 ID
 */
public class TradeDeduplicator {

    /** 找出重複的 trade ID */
    public Set<String> findDuplicates(List<String> incoming) {
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String id : incoming) {
            if (!seen.add(id)) {
                duplicates.add(id);
            }
        }
        return duplicates;
    }

    /** 取得不重複的 trade ID（保持順序） */
    public Set<String> getUnique(List<String> incoming) {
        return new LinkedHashSet<>(incoming);
    }
}
