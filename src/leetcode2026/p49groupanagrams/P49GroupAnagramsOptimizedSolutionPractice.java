package leetcode2026.p49groupanagrams;

import java.util.*;

public class P49GroupAnagramsOptimizedSolutionPractice {

    // int[26] counting as key, Time O(n * k), Space O(n * k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(int i = 0; i < strs.length; i++){
            int[] keyArray = new int[26];
            String s = strs[i];
            char[] cArray = s.toCharArray();
            for(int j = 0; j < cArray.length; j++){
                keyArray[cArray[j] - 'a']++;
            }
            String key = Arrays.toString(keyArray);
            map.putIfAbsent(key, new ArrayList());
            map.get(key).add(s);
        }
        return new ArrayList(map.values());
    }

    public static void main(String[] args) {
        P49GroupAnagramsOptimizedSolutionPractice solution = new P49GroupAnagramsOptimizedSolutionPractice();

        // Example 1: expected -> [["bat"], ["nat","tan"], ["ate","eat","tea"]]
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(solution.groupAnagrams(strs1));

        // Example 2: expected -> [[""]]
        String[] strs2 = {""};
        System.out.println(solution.groupAnagrams(strs2));

        // Example 3: expected -> [["a"]]
        String[] strs3 = {"a"};
        System.out.println(solution.groupAnagrams(strs3));
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
