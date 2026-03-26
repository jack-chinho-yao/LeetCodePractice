package leetcode2026.p88mergesortedarray;

public class P88MergeSortedArrayPractice {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = m + n - 1;
        int i = m - 1;
        int j = n - 1;
        while(j >= 0){
            if (i < 0 || nums1[i] < nums2[j]){
                nums1[k] = nums2[j];
                j--;
            }else {
                nums1[k] = nums1[i];
                i--;
            }
            k--;
        }

    }

    public static void main(String[] args) {
        P88MergeSortedArrayPractice solution = new P88MergeSortedArrayPractice();
//        solution.merge(new int[]{1,2,3,0,0,0},3, new int[]{2,5,6},3);
//        solution.merge(new int[]{1},1, new int[1],0);
        solution.merge(new int[]{0},0, new int[1],1);
//        solution.merge(new int[]{2,0},1, new int[]{1},1);
    }
}