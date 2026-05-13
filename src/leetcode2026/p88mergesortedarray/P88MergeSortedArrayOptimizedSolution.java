package leetcode2026.p88mergesortedarray;

public class P88MergeSortedArrayOptimizedSolution {

    // Three pointers from the back, Time: O(m+n), Space: O(1)
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
    }

    public static void main(String[] args) {
        P88MergeSortedArrayOptimizedSolution solution = new P88MergeSortedArrayOptimizedSolution();
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        solution.merge(nums1, 3, new int[]{2, 5, 6}, 3);
    }
}

/*
    Discussion:

    Key insight: nums1 has empty space at the BACK. If we merge from the back (largest first),
    we never overwrite unprocessed elements in nums1.

    Three pointers:
        i = m-1  (last valid element in nums1)
        j = n-1  (last element in nums2)
        k = m+n-1 (last position in nums1)

    Example: nums1 = [1, 2, 3, 0, 0, 0], m=3, nums2 = [2, 5, 6], n=3

        k=5: nums1[2]=3 vs nums2[2]=6 → 6 wins → nums1 = [1, 2, 3, 0, 0, 6], j=1
        k=4: nums1[2]=3 vs nums2[1]=5 → 5 wins → nums1 = [1, 2, 3, 0, 5, 6], j=0
        k=3: nums1[2]=3 vs nums2[0]=2 → 3 wins → nums1 = [1, 2, 3, 3, 5, 6], i=1
        k=2: nums1[1]=2 vs nums2[0]=2 → 2 wins → nums1 = [1, 2, 2, 3, 5, 6], j=-1
        j < 0 → done ✅

    Why we only need "while (j >= 0)":
        - If nums2 is fully placed, remaining nums1 elements are already in the correct position.
        - If nums1 runs out first (i < 0), we just keep placing nums2 elements.

    Naive vs Optimized:
        Naive:     Time O(m+n), Space O(m+n) — extra array for merging
        Optimized: Time O(m+n), Space O(1)   — merge in-place from the back
*/

/*
寫好了。核心思路：從後面往前填。

  nums1 尾巴本來就是空的（放 0 的位置），從最大的開始放，就不會蓋到還沒處理的元素。這樣不需要額外陣列，Space 從 O(m+n) 降到 O(1)。

  筆記也一起寫在底部了，包含完整的推演過程。

❯ 你的看起來也太簡化了吧 感覺我好蠢
  ⎿  ⧉ Selected 1 lines from src/leetcode2026/p88mergesortedarray/P88MergeSortedArrayOptimizedSolution.java in IntelliJ IDEA

⏺ 你不蠢。你的 Naive 解是自己想出來的，邏輯完全正確，這才是最重要的。

  Optimized 的「從後面填」這個技巧，第一次遇到本來就不容易想到。但你現在看過了，下次碰到類似的「原地合併」問題就會有這個直覺了。

  而且你的 Naive 解處理了所有邊界條件（i >= m、j >= n、n == 0），這些才是寫程式最難的部分。簡短不等於比較好，能正確處理所有 case 才是真功夫。



  Merge Sorted Array 最常見的應用是 Merge Sort（合併排序） 的核心步驟。

  Merge Sort 的流程：
  1. 把陣列一直對半切，切到每段只剩一個元素
  2. 再把這些小段兩兩合併回來 ← 就是這題在做的事

  除此之外，面試考的是你能不能想到「從後面往前填」來做到 in-place，這個技巧在需要原地操作排序陣列的場景都會出現。

❯ 對半切在合併速度是最快的sorted方法？

⏺ 不是最快，但是最穩定的之一。

  ┌────────────┬─────────────┬─────────────┬──────────────────────────────┐
  │   排序法   │ Time (平均) │ Time (最差) │             特點             │
  ├────────────┼─────────────┼─────────────┼──────────────────────────────┤
  │ Merge Sort │ O(n log n)  │ O(n log n)  │ 穩定，最差情況也不會變慢     │
  ├────────────┼─────────────┼─────────────┼──────────────────────────────┤
  │ Quick Sort │ O(n log n)  │ O(n²)       │ 實務上通常最快，但最差會退化 │
  ├────────────┼─────────────┼─────────────┼──────────────────────────────┤
  │ Heap Sort  │ O(n log n)  │ O(n log n)  │ 穩定但常數較大               │
  └────────────┴─────────────┴─────────────┴──────────────────────────────┘

  實務上大多數語言內建的排序都是用 Quick Sort 的變體（如 Java 的 Arrays.sort 對 primitive 用 Dual-Pivot QuickSort），因為它的常數小、cache 友善，平均跑起來最快。

  Merge Sort 的優勢是保證 O(n log n)，不會有最差情況，所以適合用在不能容忍偶爾變慢的場景（如 Java 對 Object 的排序就是用 TimSort，一種 Merge Sort 的變體）。


  */