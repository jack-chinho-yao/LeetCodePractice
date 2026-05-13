package leetcode2026.p49groupanagrams;

import java.util.*;

public class P49V3GroupAnagramsOptimizedSolution {

    // char[26] counting + new String(count) as key, Time O(n * k), Space O(n * k)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] count = new char[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            String key = new String(count);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        P49V3GroupAnagramsOptimizedSolution solution = new P49V3GroupAnagramsOptimizedSolution();

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
 * Approach (V3 - constant-factor optimization over V2):
 *   Same idea as V2 (per-word frequency counting), but with a much shorter key.
 *     - V2 used Arrays.toString(int[26])  -> ~80-120 char strings like "[1, 0, 0, ...]".
 *     - V3 uses new String(char[26])      -> always exactly 26 chars.
 *   Hash + equals on a 26-char string is several times cheaper than on an
 *   80-120 char one, which is why this version typically lands in the top
 *   5-10% on LeetCode despite having the same Big-O as V2.
 *
 * Why char[26] works:
 *   - Java char is unsigned 16-bit (0..65535), and the problem caps each word
 *     at length 100, so any single letter's count fits trivially in a char.
 *   - count[c - 'a']++ increments a char as if it were a small int.
 *   - new String(char[]) builds a String whose chars ARE the count values
 *     (often non-printable control characters - that's fine, HashMap doesn't
 *     need the key to be readable, only hash-able and equality-comparable).
 *
 * Key learnings:
 *   - Same Big-O can hide very different constants. Optimal Big-O is necessary
 *     but not sufficient for top-tier LeetCode runtime.
 *   - HashMap key length matters: it affects both hashCode() and equals() cost
 *     on every probe.
 *   - For fixed-alphabet problems (lowercase letters, digits, DNA bases),
 *     prefer fixed-width char[] keys over Arrays.toString(int[]).
 *   - "Optimized" is a relative label, not an absolute one. V2 was optimized
 *     vs. naive sorting; V3 is optimized vs. V2 on the constant-factor axis.
 *
 * Real-world usage:
 *   - Document deduplication: bucket documents by a fixed-width feature
 *     fingerprint (e.g. character-class histogram) before expensive compare.
 *   - Log fingerprinting: group log lines that differ only in literal values
 *     by hashing a normalized character / token histogram.
 *   - Bioinformatics: k-mer frequency vectors as sequence signatures - same
 *     pattern, alphabet size 4 (ACGT) instead of 26.
 *   - Spam / phishing detection: cheap character-distribution fingerprint as
 *     a first filter before deeper similarity scoring.
 *   - Search query normalization: group queries that are token permutations
 *     of each other (e.g. "cheap flights NYC" vs "NYC cheap flights") to
 *     dedupe cache entries.
 */