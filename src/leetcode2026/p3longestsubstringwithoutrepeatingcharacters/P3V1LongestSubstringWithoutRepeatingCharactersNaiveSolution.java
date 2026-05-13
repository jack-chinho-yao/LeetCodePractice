package leetcode2026.p3longestsubstringwithoutrepeatingcharacters;

public class P3V1LongestSubstringWithoutRepeatingCharactersNaiveSolution {

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
        return 0;
    }

    public static void main(String[] args) {
        P3V1LongestSubstringWithoutRepeatingCharactersNaiveSolution solution =
                new P3V1LongestSubstringWithoutRepeatingCharactersNaiveSolution();
        System.out.println(solution.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(solution.lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));   // 3
        System.out.println(solution.lengthOfLongestSubstring(""));         // 0
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