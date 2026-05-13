package leetcode2026.p88mergesortedarray;

import java.util.Arrays;

public class P88MergeSortedArrayNaiveSolution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] result = new int[m+n];
        int length = m + n;
        int i = 0;
        int j = 0;
        int r = 0;
        if(n != 0){
            for(int l = 0; l < length; l++){
                if (i >= m){
                    result[r] = nums2[j];
                    r++;
                    j++;
                }else if(j >= n){
                    result[r] = nums1[i];
                    r++;
                    i++;
                }else if((nums1[i] <= nums2[j] ) && i < m){
                    result[r] = nums1[i];
                    r++;
                    i++;
                } else {
                    result[r] = nums2[j];
                    r++;
                    j++;
                }
            }
            for(int k = 0; k < m + n ; k++){
                nums1[k] = result[k];
            }
        }
/*⏺ 邏輯是對的，能跑就是好的。幾個可以簡化的地方：

  1. r 其實永遠等於 l，可以直接用 l 當 index
  2. n != 0 的外層 if 不需要 — 如果 n=0，迴圈裡 j >= n 會一直成立，自然只複製 nums1
  3. 第 23 行的 && i < m 多餘了，因為前面 i >= m 已經被擋掉了

  但這些都是小事，不影響正確性。要我幫你整理一下嗎，還是你想自己先改？*/



    }

    public static void main(String[] args) {
        P88MergeSortedArrayNaiveSolution solution = new P88MergeSortedArrayNaiveSolution();
        solution.merge(new int[]{1,2,3,0,0,0},3, new int[]{2,5,6},3);
//        solution.merge(new int[]{1},1, new int[1],0);
//        solution.merge(new int[]{2,0},1, new int[]{1},1);
    }
}