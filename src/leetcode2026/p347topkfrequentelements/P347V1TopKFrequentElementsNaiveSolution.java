package leetcode2026.p347topkfrequentelements;

import java.util.*;

public class P347V1TopKFrequentElementsNaiveSolution {

    // HashMap count + sort entries by frequency desc, Time O(n log n), Space O(n)
    public int[] topKFrequent(int[] nums, int k) {
        return null;
    }

    public static void main(String[] args) {
        P347V1TopKFrequentElementsNaiveSolution solution = new P347V1TopKFrequentElementsNaiveSolution();

        // Example 1: nums = [1,1,1,2,2,3], k = 2 -> [1,2] (any order)
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        System.out.println(Arrays.toString(solution.topKFrequent(nums1, 2)));

        // Example 2: nums = [1], k = 1 -> [1]
        int[] nums2 = {1};
        System.out.println(Arrays.toString(solution.topKFrequent(nums2, 1)));

        // Edge: nums = [4,1,-1,2,-1,2,3], k = 2 -> [-1, 2] (any order)
        int[] nums3 = {4, 1, -1, 2, -1, 2, 3};
        System.out.println(Arrays.toString(solution.topKFrequent(nums3, 2)));
    }
}

/*
 * Approach (V1 - naive: count then sort):
 *   Step 1: Build a frequency map - HashMap<Integer, Integer>, key = number,
 *           value = how many times it appears.
 *   Step 2: Convert the entrySet to a List, then sort it by entry value
 *           (frequency) in descending order.
 *   Step 3: Take the first k entries' keys, copy them into an int[] of size k.
 *
 * Why "naive":
 *   - The bottleneck is sorting all n distinct keys: O(n log n).
 *   - We don't actually need a fully ordered list - we only need the top k.
 *     A min-heap of size k would cut this to O(n log k); bucket sort to O(n).
 *
 * Complexity:
 *   Time  O(n log n)  - dominated by sort.
 *   Space O(n)        - frequency map + entry list.
 *
 * Key learnings:
 *   - Comparator.comparingInt(Map.Entry::getValue).reversed() is the idiomatic
 *     way to sort entries by value descending.
 *   - LeetCode 347 says answer can be in any order, so no extra ordering needed.
 *   - This is the obvious-first-thought solution; useful as a baseline before
 *     optimizing.
 *
 * Real-world usage:
 *   - "Top searched terms today" reports where total distinct terms is small
 *     enough that O(n log n) sort is fine.
 *   - Offline batch analytics where simplicity beats peak performance.
 *   - One-shot leaderboards / rankings computed once a day.
 */