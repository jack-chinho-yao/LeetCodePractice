package leetcode2026.p9palindromenumber;

public class P9V1PalindromeNumberNaiveSolution {

    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        String xString = String.valueOf(x);
        StringBuilder sb = new StringBuilder(xString);
        String reverseX = sb.reverse().toString();
        return reverseX.equals(xString);
    }

    public static void main(String[] args) {
        P9V1PalindromeNumberNaiveSolution solution = new P9V1PalindromeNumberNaiveSolution();
        System.out.println(solution.isPalindrome(10));

        // Example 1: x = 121 -> true
        System.out.println(solution.isPalindrome(121));

        // Example 2: x = -121 -> false
        System.out.println(solution.isPalindrome(-121));

        // Example 3: x = 10 -> false
        System.out.println(solution.isPalindrome(10));
    }
}
