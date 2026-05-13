package interview2026.mockprep.hackerrank;

import java.util.*;

/**
 * HackerRank - Get Removable Indices (Optimized O(n))
 * 用 prefix/suffix char 比對找出合法的移除範圍，不用 substring
 */
public class RemovableIndicesV2 {

    public static List<Integer> getRemovableIndices(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        if (len1 - len2 != 1) return new ArrayList<>(List.of(-1));

        // 從前面逐字元比，找到第一個不同的位置
        int prefix = 0;
        while (prefix < len2 && str1.charAt(prefix) == str2.charAt(prefix)) {
            prefix++;
        }

        // 從後面逐字元比
        int suffix = 0;
        while (suffix < len2 && str1.charAt(len1 - 1 - suffix) == str2.charAt(len2 - 1 - suffix)) {
            suffix++;
        }

        // 合法的移除範圍: prefix ~ (len1 - 1 - suffix)
        List<Integer> result = new ArrayList<>();
        for (int i = prefix; i <= len1 - 1 - suffix; i++) {
            result.add(i);
        }
        return result.isEmpty() ? new ArrayList<>(List.of(-1)) : result;
    }

    public static void main(String[] args) {
        System.out.println(getRemovableIndices("aab", "ab"));
        // Expected: [0, 1]

        System.out.println(getRemovableIndices("abc", "ac"));
        // Expected: [1]

        System.out.println(getRemovableIndices("abc", "xyz"));
        // Expected: [-1]

        System.out.println(getRemovableIndices("aaab", "aab"));
        // Expected: [0, 1, 2]

        System.out.println(getRemovableIndices("aaa", "aa"));
        // Expected: [0, 1, 2]
    }
}
