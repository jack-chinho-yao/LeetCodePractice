package leetcode2026.p3longestsubstringwithoutrepeatingcharacters;

import java.util.HashSet;
import java.util.Set;

public class P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolutionClaude {

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

    // Sliding Window + HashSet | Time: O(n), Space: O(min(n, charset))
    public int lengthOfLongestSubstring(String s) {
        int left = 0;
        int max = 0;
        Set<Character> window = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (window.contains(c)) {
                window.remove(s.charAt(left));
                left++;
            }
            window.add(c);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolutionClaude solution =
                new P3V2LongestSubstringWithoutRepeatingCharactersOptimizedSolutionClaude();
        System.out.println(solution.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(solution.lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));   // 3
        System.out.println(solution.lengthOfLongestSubstring(""));         // 0
        System.out.println(solution.lengthOfLongestSubstring("qrsvbspk")); // 5
        System.out.println(solution.lengthOfLongestSubstring("dvdf"));     // 3
        System.out.println(solution.lengthOfLongestSubstring(" "));        // 1
        System.out.println(solution.lengthOfLongestSubstring("au"));       // 2
    }
}

/*
    === Discussion Notes ===

    [Approach]
    - Sliding window with two pointers `left` and `right`, plus a HashSet that
      stores the characters currently inside the window.
    - Invariant: the window `[left, right]` (inclusive) always contains
      distinct characters only.
    - Expand: move `right` forward, read s[right]. If it is already in the set,
      shrink from the left by removing s[left] and `left++` until the duplicate
      is gone. Then add s[right] to the set.
    - Track the maximum window length `right - left + 1` after each expansion.

    [Why a `while`, not an `if`, when shrinking?]
    - For inputs like "abba", when `right` lands on the second 'a', the duplicate
      could be several positions inside the window, so we have to keep removing
      from the left until the offending character is gone.
    - An `if` would only remove one character and miss the multi-step shrink.

    [Why `right - left + 1` instead of `set.size()`?]
    - They are numerically equal (the window is duplicate-free by invariant), but
      `right - left + 1` directly expresses "window length" and makes the
      sliding-window intent obvious to a reader. `set.size()` works but reads as
      "count of distinct chars" which is one indirection away from the goal.

    [Complexity]
    - Time:  O(n). Each character enters the set at most once and is removed at
      most once, so total work across both pointers is O(2n) = O(n).
    - Space: O(min(n, |charset|)). The set is bounded by the window size, which
      is bounded by both `n` and the size of the alphabet (e.g. 128 for ASCII).

    [Common Pitfalls]
    - Updating `max` BEFORE adjusting `left` for a duplicate -> overcounts.
      Always shrink first, then add, then measure.
    - Using `if` instead of `while` -> fails on "abba" / "tmmzuxt".
    - Returning `set.size()` at the end -> wrong; that is only the size of the
      LAST window, not the maximum seen.

    [Real-world Usage]
    - Network packet stream dedup: longest run of unique packet IDs in a stream
      window, used in idempotency / replay-attack detection.
    - Session activity analysis: longest user session segment without repeating
      a particular action (e.g. "longest streak of distinct pages visited").
    - DNA / bioinformatics: longest substring of a genome without repeated
      nucleotide k-mers, a building block in sequence assembly.
    - Log compression / streaming dedup: maintain a sliding window of distinct
      events to feed a deduplicating sink.
    - Keystroke / typing-pattern analytics: detect the longest unique-key streak
      to characterize user typing rhythm.

    [Sliding Window — General Pattern]
    - Use when the problem asks for the "longest / shortest / count of contiguous
      subarray or substring satisfying property P", and P is monotone: extending
      the window can only break P, shrinking can only restore it.
    - Two pointers move in one direction; each element is touched O(1) amortized
      times -> O(n) overall. Pair with a hash structure for O(1) state checks.
*/