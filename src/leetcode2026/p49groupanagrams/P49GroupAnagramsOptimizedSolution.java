package leetcode2026.p49groupanagrams;

import java.util.*;

public class P49GroupAnagramsOptimizedSolution {

    // int[26] counting as key, Time O(n * k), Space O(n * k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(int i = 0; i < strs.length; i++){
            String str = strs[i];
            char[] charArray = str.toCharArray();
            int[] intArray = new int[26];

            for (int j = 0; j < charArray.length; j++) {
                char c = charArray[j];
                intArray[c-'a']++;
            }
            String string = Arrays.toString(intArray);
            map.putIfAbsent(string, new ArrayList<>());
            map.get(string).add(str);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        P49GroupAnagramsOptimizedSolution solution = new P49GroupAnagramsOptimizedSolution();

    }
}

/*
 * Approach:
 *   For each word, count character frequency with int[26].
 *   Convert int[26] to a string via Arrays.toString() and use it as HashMap key.
 *   All anagrams produce the same frequency array -> same key -> same group.
 *   e.g. "eat" and "tea" both -> [0,0,0,0,1,0,...,1,...,0] -> same key.
 *
 * Key learnings:
 *   - Arrays.toString(int[]) converts an array to a string like "[1, 0, 0, ...]".
 *   - This avoids sorting (O(k) vs O(k log k) per word).
 *   - putIfAbsent + get().add() is cleaner than if/else containsKey pattern.
 *   - new ArrayList<>(map.values()) directly returns the grouped result.
 *
 * Connection between P242 and P49:
 *   - P242 teaches frequency counting to COMPARE two strings (boolean result).
 *   - P49 extends this to GROUP many strings by using frequency as a HashMap key.
 *   - Same core technique (int[26] counting), different application.
 */
