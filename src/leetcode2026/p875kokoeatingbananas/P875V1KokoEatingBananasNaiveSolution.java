package leetcode2026.p875kokoeatingbananas;

// Try every speed k = 1, 2, 3, ... until one fits in h hours. Time O(max(piles) * n), Space O(1).
public class P875V1KokoEatingBananasNaiveSolution {

    public int minEatingSpeed(int[] piles, int h) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P875V1KokoEatingBananasNaiveSolution solution = new P875V1KokoEatingBananasNaiveSolution();
        System.out.println(solution.minEatingSpeed(new int[]{3, 6, 7, 11}, 8));           // 4
        System.out.println(solution.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));     // 30
        System.out.println(solution.minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));     // 23
    }

    /*
    Problem 875. Koko Eating Bananas

    Koko has n piles of bananas, piles[i] in the i-th pile. She eats at a fixed
    speed of k bananas/hour: each hour she picks a pile and eats k from it; if a
    pile has fewer than k left, she eats it all and does not move on that hour.
    Given `piles` and h hours before the guards return, return the MINIMUM
    integer k such that she can finish all bananas within h hours.
    (Guaranteed h >= piles.length.)

    Example 1:
        Input : piles = [3, 6, 7, 11], h = 8
        Output: 4
    Example 2:
        Input : piles = [30, 11, 23, 4, 20], h = 5
        Output: 30
    Example 3:
        Input : piles = [30, 11, 23, 4, 20], h = 6
        Output: 23

    Naive approach:
    - The answer k is somewhere in [1, max(piles)].
    - Try k = 1, then 2, then 3 ... For each k, compute the total hours needed
      (sum over piles of ceil(pile / k)); return the first k whose hours <= h.
    - Time  : O(max(piles) * n)  — linear scan over every candidate speed.
    - Space : O(1)
    - Correct but slow — V2 binary-searches k instead of scanning it.

    Helper hint (shared with V2): hours for a pile at speed k = ceil(pile / k),
    which in integer math is (pile + k - 1) / k.
    */
}
