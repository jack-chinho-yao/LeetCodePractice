package leetcode2026.p3longestsubstringwithoutrepeatingcharacters;

import java.util.HashSet;
import java.util.Set;

public class P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolution {

    // Given a string s, find the length of the longest substring
    // without repeating characters.
    //
    // Example 1: Input: s = "abcabcbb"  -> Output: 3   ("abc")
    // Example 2: Input: s = "bbbbb"     -> Output: 1   ("b")
    // Example 3: Input: s = "pwwkew"    -> Output: 3   ("wke")
    //
    // Constraints:
    //   0 <= s.length <= 5 * 10^4
    //   s consists of English letters, digits, symbols and spaces.

    public int lengthOfLongestSubstring(String s) {
        int right = 0;
        int left = 0;
        int max = 0;
        Set<Character> set = new HashSet<>();
        for(int i = 0; i < s.length(); i++){
            while(set.contains(s.charAt(right))){
                set.remove(s.charAt(left));
                left++;
            }

            set.add(s.charAt(right));
            right++;
            max = Math.max(set.size(), max);
        }
        return max;
    }

    public static void main(String[] args) {
        P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolution solution =
                new P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolution();
//        System.out.println(solution.lengthOfLongestSubstring("abcabcbb")); // 3
//        System.out.println(solution.lengthOfLongestSubstring("bbbbb"));    // 1
//        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));   // 3
//        System.out.println(solution.lengthOfLongestSubstring(""));         // 0
        System.out.println(solution.lengthOfLongestSubstring("qrsvbspk"));         // 0
    }
}

/*
    === Discussion Notes ===

    [Approach]
    -

    [Complexity]
    - Time:
    - Space:

    [Real-world Usage]
    -
*/

/*
*
* 對，HashSet 是這題最自然的工具，因為核心需求就是 O(1) 檢查「這個字元在目前的視窗裡有沒有出現過」。

  兩個版本可以這樣分：

  V1 Naive — 暴力 + HashSet 驗證
  - 雙層迴圈窮舉所有 substring s[i..j]
  - 每段都丟進一個 HashSet，遇到重複就 break，記錄當下長度
  - Time: O(n²) 或 O(n³) 看實作；Space: O(min(n, charset))

  V2 Optimized — Sliding Window + HashSet
  - 兩個指標 left、right，HashSet 維護「目前視窗內的字元」
  - right 往右擴，若 s[right] 已在 set 中，就一直 remove(s[left]) 並 left++，直到衝突解除
  - 每次更新 max = Math.max(max, right - left + 1)
  - Time: O(n)（每個字元最多進出 set 一次）；Space: O(min(n, charset))

  ▎ 進階版可用 HashMap<Character, Integer> 存「字元 → 最後出現的索引」，遇到重複時 left 直接跳到 重複位置 + 1，省掉一個一個 remove 的步驟，但概念是一樣的 sliding window。

  要不要我先讓你自己試 V1 / V2，卡住再問？還是想先討論 sliding window 的 invariant（[left, right] 區間內永遠無重複）這個關鍵不變式？

✻ Cooked for 16s

❯ 我先用英文解釋一遍 等等幫我修正我的問題

  for the continious substring , I would use slide windows do this question.
  so I declare two fields, right and left for the sliding window.
  max for the result to return.
  And a HashSet to deduplicate char
  ⎿  ⧉ Selected 15 lines from src/leetcode2026/p3longestsubstringwithoutrepeatingcharacters/P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolution.java in IntelliJ IDEA

⏺ 幫你修正英文 + 用詞精準度：

  原文問題：

  ┌──────────────────────────────┬───────────────────────────────────────────────┬─────────────────────────────────────────────────────────────────────────────────┐
  │            你寫的            │                     修正                      │                                      原因                                       │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ continious                   │ contiguous                                    │ 拼字錯誤；連續子字串的標準術語                                                  │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ slide windows                │ sliding window                                │ 固定片語，單數                                                                  │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ do this question             │ to solve this problem                         │ LeetCode 慣例叫 "problem" 不是 "question"；do 太口語                            │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ fields                       │ variables / pointers                          │ Java 裡 field 專指 class 成員變數；這裡是 local variable，雙指標通常叫 pointers │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ max for the result to return │ max to track the result                       │ 動詞要明確                                                                      │
  ├──────────────────────────────┼───────────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────┤
  │ deduplicate char             │ track unique characters in the current window │ HashSet 不是「去重」，它是用來快速判斷視窗內有沒有重複字元                      │
  └──────────────────────────────┴───────────────────────────────────────────────┴─────────────────────────────────────────────────────────────────────────────────┘

  潤飾後的版本（面試時可以這樣講）：

  ▎ "Since we need a contiguous substring, I'll use a sliding window approach to solve this problem.
  ▎
  ▎ I declare two pointers, left and right, to mark the boundaries of the window, and a variable max to track the longest valid length so far.
  ▎
  ▎ I also use a HashSet<Character> to keep track of the unique characters currently inside the window — this gives me O(1) lookup to detect duplicates."

  面試加分小技巧 — 開場再補一句「invariant 不變式」：

  ▎ "The invariant I maintain is: the window [left, right) always contains only distinct characters. Whenever a duplicate is about to enter, I shrink from the left until the invariant holds again."

  準備好我看 code 邏輯了，要我繼續嗎？


* */