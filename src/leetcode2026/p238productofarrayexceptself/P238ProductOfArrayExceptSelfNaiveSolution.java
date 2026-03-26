package leetcode2026.p238productofarrayexceptself;

public class P238ProductOfArrayExceptSelfNaiveSolution {

    // Prefix + Suffix product arrays, Time: O(n), Space: O(n)
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] leftProductArray = new int[length];
        int[] rightProductArray = new int[length];
        int[] result = new int[length];
        int leftProduct = 1;
        int rightProduct = 1;
        for(int i = 0; i < nums.length; i++){
            leftProductArray[i] = leftProduct;
            leftProduct = leftProduct * nums[i];

            rightProductArray[length-i-1] = rightProduct;
            rightProduct = rightProduct * nums[length-i-1];
        }
        for(int i = 0; i < nums.length; i++){
            result[i] = leftProductArray[i] * rightProductArray[i];
        }

        return result;
    }

    public static void main(String[] args) {
        P238ProductOfArrayExceptSelfNaiveSolution solution = new P238ProductOfArrayExceptSelfNaiveSolution();
        solution.productExceptSelf(new int[]{1,2,3,4});
    }
}

/*
    Discussion:

    The intuition is: "multiply everything together and divide by self",
    but the problem forbids division. So instead, split the product into two parts:
        answer[i] = left product (everything before i) × right product (everything after i)

    Example: nums = [1, 2, 3, 4]

        leftProductArray  = [1, 1, 2, 6]
            i=0: nothing on the left → 1
            i=1: 1 → 1
            i=2: 1×2 → 2
            i=3: 1×2×3 → 6

        rightProductArray = [24, 12, 4, 1]
            i=3: nothing on the right → 1
            i=2: 4 → 4
            i=1: 3×4 → 12
            i=0: 2×3×4 → 24

        result[i] = leftProductArray[i] × rightProductArray[i]
        result    = [24, 12, 8, 6]

    Key insight: save the running product at each position first (one pass left, one pass right),
    then combine them. This avoids nested loops.

    Two separate O(n) loops = O(n) + O(n) = O(2n) = O(n), NOT O(n²).
    Big O drops constants because it only cares about growth rate, not the exact count.

    This is a "prefix / suffix" technique — the same idea appears in prefix sum, prefix max, etc.

    Time:  O(n)
    Space: O(n) — three extra arrays (leftProductArray, rightProductArray, result)
*/