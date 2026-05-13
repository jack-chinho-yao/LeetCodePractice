package leetcode2026.p1twosum;

import java.util.HashMap;
import java.util.Map;

public class P1TwoSumOptimizedSolutionPractice {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> complentmentToIndex = new HashMap<>();
        int length = nums.length;
        for(int i = 0; i < length; i++){
            int complement = target - nums[i];
            if(complentmentToIndex.containsKey(complement)){
                return new int[]{complentmentToIndex.get(complement), i};
            }else{
                complentmentToIndex.put(nums[i], i);
            }
        }
        return null;

    }

    public static void main(String[] args) {

        System.out.println( new P1TwoSumOptimizedSolutionPractice().twoSum(new int[]{2,7,11,15}, 9));
//        new P1TwoSumOptimizedSolutionPractice().twoSum(new int[]{3,2,4}, 6);
//        new P1TwoSumOptimizedSolutionPractice().twoSum(new int[]{3,3}, 6);
    }






    /*
    Example 1:

    Input: nums = [2,7,11,15], target = 9
    Output: [0,1]
    Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
    Example 2:

    Input: nums = [3,2,4], target = 6
    Output: [1,2]
    Example 3:

    Input: nums = [3,3], target = 6
    Output: [0,1]

    */
}
