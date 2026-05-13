package interview2026.mockprep.collections;

import java.util.*;

/**
 * Q4 - HashSet 去重：去除重複交易 ID，並印出哪些是重複的
 * 情境：訊息佇列 (Message Queue) 重複收到交易訊息
 */
public class Q4HashSetDeduplicate {

    public static void main(String[] args) {
        List<String> incoming = Arrays.asList(
            "T001", "T002", "T001", "T003", "T002", "T004", "T001"
        );

        // TODO: 找出重複的 trade ID
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();


        // TODO: 印出 Duplicates found: T001, T002


        // TODO: 印出 Unique trades: T001, T002, T003, T004

    }
}
