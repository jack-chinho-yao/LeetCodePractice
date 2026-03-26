package leetcode2026.p242validanagram;

public class P242ValidAnagramOptimizedSolutionClaude {

    // int[26] counting, Time O(n), Space O(1)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        P242ValidAnagramOptimizedSolutionClaude solution = new P242ValidAnagramOptimizedSolutionClaude();
        System.out.println(solution.isAnagram("anagram", "nagaram")); // true
        System.out.println(solution.isAnagram("rat", "cat"));         // false
    }
}
