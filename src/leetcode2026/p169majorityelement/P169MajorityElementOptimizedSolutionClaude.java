package leetcode2026.p169majorityelement;

public class P169MajorityElementOptimizedSolutionClaude {

    // Boyer-Moore Voting Algorithm, Time: O(n), Space: O(1)
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    public static void main(String[] args) {
        P169MajorityElementOptimizedSolutionClaude solution = new P169MajorityElementOptimizedSolutionClaude();
        System.out.println(solution.majorityElement(new int[]{3, 2, 3}));
    }
}