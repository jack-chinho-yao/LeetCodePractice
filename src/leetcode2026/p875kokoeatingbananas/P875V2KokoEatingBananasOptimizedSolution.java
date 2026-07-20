package leetcode2026.p875kokoeatingbananas;

// Binary search on the answer (the eating speed k). Time O(n log(max(piles))), Space O(1).
public class P875V2KokoEatingBananasOptimizedSolution {

    public int minEatingSpeed(int[] piles, int h) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P875V2KokoEatingBananasOptimizedSolution solution = new P875V2KokoEatingBananasOptimizedSolution();
        System.out.println(solution.minEatingSpeed(new int[]{3, 6, 7, 11}, 8));           // 4
        System.out.println(solution.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));     // 30
        System.out.println(solution.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));     // 23
    }

    /*
    === Guiding questions ===

    這題是「二分答案 (binary search on the answer)」的入門題 —— 一個超重要、
    面試很愛考的變體。前面 704/74/153/33 都是對「陣列 index」二分；
    這題二分的對象變成「答案本身」。想通這層，你就解鎖一整類題目。

    1. 為什麼可以二分？先確認單調性：如果速度 k 能在 h 小時內吃完，那 k+1 呢？
       如果 k 吃不完，k-1 呢？把「能不能在時限內吃完」畫成一條 false...false,true...true
       的分界線 —— 你要找的就是「第一個 true 的 k」。

    2. 搜尋範圍：k 最小是 1（不能是 0，為什麼？），最大只要到 max(piles) 就夠了 ——
       為什麼超過 max(piles) 沒意義？（一小時最多吃掉一整堆。）

    3. 寫一個 helper：hoursNeeded(piles, k) 回傳用速度 k 吃完的總時數。
       每堆要 ceil(pile / k) 小時。整數的 ceil 怎麼寫？(pile + k - 1) / k。
       （小心 int 溢位：這裡累加用 long 比較保險。）

    4. 主迴圈就是標準二分，只是判斷式從「nums[mid] 跟 target 比」換成
       「hoursNeeded(piles, mid) <= h 嗎」。想清楚：
         - 條件成立（時間夠）→ 這個 mid 可能是答案，但也許還能更小 → 往哪收縮？
         - 條件不成立（太慢）→ mid 一定不行 → 往哪收縮？
       用 while (lo < hi) 讓結束時 lo 停在「第一個可行的 k」。

    自測：piles=[30,11,23,4,20], h=5 的答案是 30（時間剛好卡死，只能一小時一堆），
    你的邊界會不會少算、回傳 29？
    */
}
