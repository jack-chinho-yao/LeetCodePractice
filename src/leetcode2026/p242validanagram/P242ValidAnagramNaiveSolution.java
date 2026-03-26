package leetcode2026.p242validanagram;

import java.util.HashMap;
import java.util.Map;

public class P242ValidAnagramNaiveSolution {

    // HashMap approach, Time O(n), Space O(n)
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        if (s.length() != t.length()) return false;
        for (int i = 0; i < s.length(); i++) {
            char sChar = s.charAt(i);

            if (map.containsKey(sChar)){
                map.put(sChar, map.get(sChar) + 1);
            }else {
                map.put(sChar, 1);
            }

            char tChar = t.charAt(i);

            if (map.containsKey(tChar)){
                map.put(tChar, map.get(tChar) - 1);
            }else {
                map.put(tChar, -1);
            }
        }

        for (Character c : map.keySet()) {
            if (map.get(c) != 0) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        P242ValidAnagramNaiveSolution solution = new P242ValidAnagramNaiveSolution();

    }
}

/*
 * Approach:
 *   Use a single HashMap<Character, Integer> to track character frequency.
 *   Iterate through both strings simultaneously: +1 for chars in s, -1 for chars in t.
 *   If all values in the map are 0 at the end, the two strings are anagrams.
 *
 * Key learnings:
 *   - HashMap is good for frequency counting, but has overhead from boxing (Character, Integer).
 *   - getOrDefault(key, 0) can simplify the containsKey + get pattern.
 *   - Early return when lengths differ avoids unnecessary work.
 *
 * Comparison with Optimized:
 *   - This approach uses HashMap -> works for any characters (unicode, etc.)
 *   - Optimized uses int[26] -> only works for lowercase a-z, but faster (no boxing).
 */
