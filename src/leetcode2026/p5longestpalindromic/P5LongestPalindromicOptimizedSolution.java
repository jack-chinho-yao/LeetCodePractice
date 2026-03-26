package leetcode2026.p5longestpalindromic;

public class P5LongestPalindromicOptimizedSolution {
    public String longestPalindrome(String s) {
        int start = 0, maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            // 奇數長度：以 i 為中心
            int len1 = expand(s, i, i);
            // 偶數長度：以 i 和 i+1 為中心
            int len2 = expand(s, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }

        return s.substring(start, start + maxLen);
    }

    private int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    public static void main(String[] args) {
        System.out.println("0123456789".charAt(5));
        new P5LongestPalindromicOptimizedSolution().longestPalindrome("abcba");
        System.out.println("0123456789".substring(0, 5));
    }
}
