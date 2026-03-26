package leetcode2026.p1twosum;

import java.util.Arrays;

public class P1TwoSumNaiveSolution {
    public int[] twoSum(int[] nums, int target) {
        for (int left = 0; left < nums.length; left++){
            for(int right = left + 1; right < nums.length; right++){
                if ((nums[left] + nums[right] == target)){
                    return new int[]{left, right};
                }
            }
        }
        return null;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                new P1TwoSumNaiveSolution().twoSum(new int[]{3, 2, 4}, 6)
        ));
    }
}

