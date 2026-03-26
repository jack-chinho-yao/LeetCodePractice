package leetcode2026.p169majorityelement;

import java.util.HashMap;
import java.util.Map;

public class P169MajorityElementNaiveSolution {

    // HashMap counting, Time: O(n), Space: O(n)
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        for(int i = 0; i < length; i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        for (Integer i : map.keySet()) {
            Integer i1 = map.get(i);
            int i2 = (length + 1) / 2;
            if(i1 >= i2){
                return i;
            }
        }

        return 0;
    }
/*
    Discussion:

    Approach: use HashMap to count occurrences of each number, then find the one with count > n/2.

    Example: nums = [3, 2, 3]
        map = {3: 2, 2: 1}
        length = 3, threshold = 3/2 = 1
        3 appears 2 times > 1 → return 3

    Java Integer cache note:
        - Java caches Integer objects for -128 to 127.
        - Comparing two Integer with == works in that range, but fails for values >= 128.
        - Always use .equals() or unbox to int when comparing Integer objects.
        - In this solution, "i1 >= i2" auto-unboxes to int, so no issue here.

    Time:  O(n)
    Space: O(n) — HashMap to store counts
*/

    public static void main(String[] args) {
        P169MajorityElementNaiveSolution solution = new P169MajorityElementNaiveSolution();
        solution.majorityElement(new int[]{3,2,3});
//        System.out.println(3/2);
    }
}