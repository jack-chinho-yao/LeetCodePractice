package leetcode2026.p238productofarrayexceptself;

public class P238ProductOfArrayExceptSelfOptimizedSolution {

    // Prefix + Suffix in-place, Time: O(n), Space: O(1)
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] result = new int[length];
        int leftProduct = 1;
        int rightProduct = 1;
        for(int i = 0; i < nums.length; i++){
            result[i] = leftProduct;
            leftProduct = leftProduct * nums[i];

        }
        for(int i = 0; i < nums.length; i++){
            result[length-i-1] = result[length-i-1] * rightProduct;
            rightProduct = rightProduct * nums[length-i-1];
        }
        return result;
    }

    public static void main(String[] args) {
        P238ProductOfArrayExceptSelfOptimizedSolution solution = new P238ProductOfArrayExceptSelfOptimizedSolution();

    }
}

/*
    Discussion:

    Optimization over Naive: eliminate the two extra arrays (leftProductArray, rightProductArray).
    Instead, store left products directly into the result array, then multiply right products in-place.

    Step 1 — Left pass (left → right): store left product at each index into result
        nums   = [1,  2,  3,  4]
        result = [1,  1,  2,  6]

    Step 2 — Right pass (right → left): multiply right product into result in-place
        i=3: result[3] = 6 × 1  = 6,   rightProduct = 4
        i=2: result[2] = 2 × 4  = 8,   rightProduct = 12
        i=1: result[1] = 1 × 12 = 12,  rightProduct = 24
        i=0: result[0] = 1 × 24 = 24,  rightProduct = 24

        result = [24, 12, 8, 6] ✅

    Why two loops cannot be merged into one:
        - The left pass goes left → right, the right pass goes right → left.
        - The right pass needs the left products to be fully computed first.
        - Two separate O(n) loops = O(n) + O(n) = O(n), already optimal.

    Naive vs Optimized:
        Naive:     Time O(n), Space O(n) — 3 extra arrays
        Optimized: Time O(n), Space O(1) — only result array + 2 variables
*/