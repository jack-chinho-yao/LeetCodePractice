package leetcode2026.p49groupanagrams;

import java.util.*;

public class P49GroupAnagramsNaiveSolution {
    // Sorting as key, Time O(n * k log k), Space O(n * k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(int i = 0; i < strs.length; i++){
            String str = strs[i];
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String sortedStr = String.valueOf(charArray);
            if (!map.containsKey(sortedStr)){
                map.put(sortedStr, new ArrayList<>(Arrays.asList(str)));
            }else {
                map.get(sortedStr).add(str);
            }

        }
        List<List<String>> result = new ArrayList<>();
        for (String s : map.keySet()) {
            result.add(map.get(s));
        }
        return result;
    }

    public static void main(String[] args) {
        P49GroupAnagramsNaiveSolution solution = new P49GroupAnagramsNaiveSolution();
        solution.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
    }
}

/*
 * Approach:
 *   Sort each word alphabetically -> all anagrams produce the same sorted string.
 *   Use the sorted string as HashMap key, group original words into List<String> values.
 *   e.g. "eat" -> "aet", "tea" -> "aet", "ate" -> "aet" -> all in the same group.
 *
 * Key learnings:
 *   - "Design a custom key for grouping" is a common HashMap pattern.
 *   - toCharArray() + Arrays.sort() + String.valueOf() to sort a string.
 *   - putIfAbsent(key, new ArrayList<>()) simplifies the containsKey + put pattern.
 *   - return new ArrayList<>(map.values()) collects all groups without manual iteration.
 *
 * Comparison with Optimized:
 *   - Naive: O(n * k log k) due to sorting each word.
 *   - Optimized: O(n * k) using int[26] counting as key, avoids sorting.
 *   - In practice, both perform similarly on short strings because Arrays.sort() is well-optimized.
 */
