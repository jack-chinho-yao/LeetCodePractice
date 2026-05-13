package leetcode2026.p9palindromenumber;

public class P9V4PalindromeNumberOptimizedSolutionPractice {

    // 流派 B：只反轉一半 - Time: O(log₁₀ n), Space: O(1)
    public boolean isPalindrome(int x) {
        if(x < 0 || (x != 0 && x % 10 == 0)){
            return false;
        }else if(x == 0){
            return true;
        }
        int reverse = 0;
        while(x > reverse){
            reverse = reverse * 10 + x % 10;
            x /= 10;
        }
        return reverse == x || reverse / 10 == x;
    }

    public static void main(String[] args) {
        P9V4PalindromeNumberOptimizedSolutionPractice solution = new P9V4PalindromeNumberOptimizedSolutionPractice();

        // Example 1: x = 121 -> true
        System.out.println(solution.isPalindrome(121));

        // Example 2: x = -121 -> false
//        System.out.println(solution.isPalindrome(-121));

        // Example 3: x = 10 -> false
//        System.out.println(solution.isPalindrome(1001));
//        System.out.println(solution.isPalindrome(10));
//        System.out.println(solution.isPalindrome(0));
    }
}

/*
 * 思路：只反轉下半部 digit，跟上半部在中間相遇就停。
 *
 * 兩邊同時進行：
 * - reverseX：從 x 摘個位累積，愈來愈大
 * - x：不斷 /10，愈來愈小
 * 當 reverseX >= x（超過或追上）→ 已處理至少一半 digit，停止。
 *
 * 過程示意：
 *
 * x = 1221（偶數位）
 *   起始：x=1221, reverseX=0
 *   iter1：x=122,  reverseX=1     (reverseX < x)
 *   iter2：x=12,   reverseX=12    (reverseX >= x，退出)
 *   判斷：reverseX == x → 12 == 12 ✓ true
 *
 * x = 12321（奇數位，中間位是 3）
 *   iter1：x=1232, reverseX=1
 *   iter2：x=123,  reverseX=12
 *   iter3：x=12,   reverseX=123   (退出，中間位 3 在 reverseX 的個位)
 *   判斷：reverseX / 10 == x → 12 == 12 ✓ true
 *
 * x = 123（奇數位非回文）
 *   iter1：x=12, reverseX=3
 *   iter2：x=1,  reverseX=32      (退出)
 *   判斷：reverseX == x？32==1 ✗; reverseX/10 == x？3==1 ✗ → false
 *
 * Pre-check 為什麼要擋 x % 10 == 0：
 *   尾數 0（10, 100, 1230）反轉後首位變 0，不可能等於原數。
 *   x != 0 是為了放行 x == 0（0 是合法回文）。
 *
 * 複雜度：
 *   Time  O(log₁₀ n) ≈ O(d/2)，d = digit 數。只跑一半。
 *   Space O(1)。
 *
 * V-progression：
 *   V1 String reverse      — Time O(n), Space O(n)
 *   V2 流派 A bug          — 邏輯繞 + dead code
 *   V3 流派 A 乾淨版       — Time O(d), Space O(1) + long 防溢位
 *   V4 流派 B（此檔）      — Time O(d/2), Space O(1)，無溢位風險（賣點！不需要 long）
 *
 * 流派 B vs A 的兩個賣點（面試要講）：
 *   1. 迭代次數少一半
 *   2. 不需要 long 防溢位（因為 reverseX 永遠 ≤ 原數一半）
 */
