package interview2026.mockprep.hackerrank;

import java.util.*;

/**
 * HackerRank - Get Removable Indices
 * Given str1 and str2, find all indices i where removing str1[i] results in str2.
 * Return [-1] if no such index exists.
 */
public class RemovableIndicesV1 {

    public static List<Integer> getRemovableIndices(String str1, String str2) {
        int length1 = str1.length();
        int length = str2.length();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < str1.length(); i++) {
            String str1FirstPart = str1.substring(0, i);
            String str2FirstPart = str2.substring(0, i);
            String str1SecondPart = str1.substring(i + 1, length1);
            String str2SecondPart = str2.substring(i, length);
            if (!((str1.length() - str2.length()) == 1)) {
                break;
            }
            if (i == 0 && str1.substring(i + 1).equals(str2SecondPart)) {
                result.add(i);
            } else if (str1FirstPart.equals(str2FirstPart) && str1SecondPart.equals(str2SecondPart)) {
                result.add(i);
            }
        }
        return result.isEmpty() ? new ArrayList<>(Arrays.asList(-1)) : result;
    }

    public static void main(String[] args) {
        System.out.println(getRemovableIndices("aab", "ab"));
        // Expected: [0, 1]

        System.out.println(getRemovableIndices("abc", "ac"));
        // Expected: [1]

        System.out.println(getRemovableIndices("abc", "xyz"));
        // Expected: [-1]
    }
}
