package leetcode2026.p9palindromenumber;


/*
*
*   流派 A：反轉整個數字再比對
  - 邏輯直覺，跟 V1 思路一致，只是換成用數字不是字串
  - 缺點：反轉後可能溢位（例如 1534236469 反轉會超過 Integer.MAX_VALUE）
  - 處理：用 long 存反轉結果，或溢位時直接 return false

  流派 B：只反轉一半（LeetCode 官方解）
  - 當 reversed >= x 時停止 → 只處理一半 digit
  - 無溢位風險（reversed 永遠 ≤ 原數一半）
  - 奇數位數要處理中間那位：x == reversed / 10
  - 偶數位數：x == reversed

* */
public class P9V3PalindromeNumberOptimizedSolution {
// 這題做流派Ａ
    public boolean isPalindrome(int x) {
        int temp = x;
        if (temp < 0){
            return false;
        }
        long reverseX = 0;
        while(temp > 0){
            reverseX = reverseX * 10 + temp%10;
            temp = temp/10;
        }
        return reverseX == x;
    }

    public static void main(String[] args) {
        P9V3PalindromeNumberOptimizedSolution solution = new P9V3PalindromeNumberOptimizedSolution();

        // Example 1: x = 121 -> true
//        System.out.println(solution.isPalindrome(121));

        // Example 2: x = -121 -> false
//        System.out.println(solution.isPalindrome(-121));

        // Example 3: x = 10 -> false
        System.out.println(solution.isPalindrome(1001));
    }
}
