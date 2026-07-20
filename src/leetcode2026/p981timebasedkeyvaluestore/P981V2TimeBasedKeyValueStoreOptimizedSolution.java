package leetcode2026.p981timebasedkeyvaluestore;

import java.util.HashMap;

public class P981V2TimeBasedKeyValueStoreOptimizedSolution {

    // Map<key, list of (timestamp, value)> kept sorted by insertion; get() binary-searches. set O(1), get O(log n).
    class TimeMap {

        TimeMap() {
            // TODO
        }

        public void set(String key, String value, int timestamp) {
            // TODO
        }

        public String get(String key, int timestamp) {
            // TODO
            return "";
        }
    }

    public static void main(String[] args) {
        TimeMap tm = new P981V2TimeBasedKeyValueStoreOptimizedSolution().new TimeMap();
        tm.set("foo", "bar", 1);
        System.out.println(tm.get("foo", 1));   // "bar"
        System.out.println(tm.get("foo", 3));   // "bar"
        tm.set("foo", "bar2", 4);
        System.out.println(tm.get("foo", 4));   // "bar2"
        System.out.println(tm.get("foo", 5));   // "bar2"
        System.out.println(tm.get("foo", 0));   // ""
    }

    /*
    === Guiding questions ===

    這題把「二分」藏在一個資料結構設計題裡，是很典型的組合考法（跟你做過的
    146 LRU 是同一種「Map + 另一個結構」的思路）。

    1. 題目保證「同一個 key 的 set 呼叫 timestamp 嚴格遞增」。
       這代表你 append 進去的 list，天生就是「按 timestamp 排好序」的 ——
       你完全不用自己排序。這個保證是能用二分的前提，先確認你有吃到它。

    2. get(key, t) 要找的是「timestamp <= t 之中最大的那一筆」的 value。
       把它翻譯成二分的語言：在一條排序好的 timestamp list 裡，找
       「最後一個 <= t 的位置」。這是二分的一種經典變體 —— 想想它跟
       「第一個 > t 的位置」是什麼關係（找到後退一格）。

    3. 資料結構：Map<String, List<int[]>> 或自己開一個小 Pair class 存
       (timestamp, value)。set 就是 computeIfAbsent 後 append。

    4. get 的二分收縮方向要想清楚：
         - list[mid].timestamp <= t → 這筆是「候選答案」，先記住它，再往右找有沒有更大的還 <= t
         - list[mid].timestamp >  t → 太新了，往左收縮
       迴圈結束後，你記住的最後一個候選就是答案；從頭到尾都沒有候選 → 回傳 ""。

    5. 三個邊界一定要自測：
         - t 比所有 timestamp 都小（例：get("foo", 0)）→ 應回 ""
         - t 剛好等於某個 timestamp → 應回那一筆
         - t 比所有 timestamp 都大 → 應回最後一筆
       跟 V1 用同一組 main 對拍，輸出要完全一致。
    */
}
