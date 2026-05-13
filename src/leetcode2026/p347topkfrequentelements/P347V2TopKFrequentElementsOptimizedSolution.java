package leetcode2026.p347topkfrequentelements;

import java.util.*;

public class P347V2TopKFrequentElementsOptimizedSolution {

    // HashMap count + bucket sort by frequency, Time O(n), Space O(n)
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: count frequencies. merge: if absent put 1, else oldVal + 1.
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.merge(num, 1, Integer::sum);
        }

        // Step 2: buckets[i] = list of values that appear exactly i times.
        // Using List<List<Integer>> to sidestep Java's generic-array-creation rule.
        List<List<Integer>> buckets = new ArrayList<>(nums.length + 1);

        // Step 3: drop each value into the bucket indexed by its frequency.
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            buckets.get(entry.getValue()).add(entry.getKey());
        }

        // Step 4: walk buckets from highest frequency down, collect up to k values.
        int[] result = new int[k];
        int idx = 0;
        for (int i = nums.length; i >= 1 && idx < k; i--) {
            for (int num : buckets.get(i)) {
                result[idx++] = num;
                if (idx == k) break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P347V2TopKFrequentElementsOptimizedSolution solution = new P347V2TopKFrequentElementsOptimizedSolution();

        // Example 1: nums = [1,1,1,2,2,3], k = 2 -> [1,2] (any order)
        int[] nums1 = {1, 1, 1, 2, 2, 3,3,3,3,3,3};
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
 * Approach (V2 - bucket sort by frequency):
 *   Insight: in an array of length n, the maximum possible frequency of any
 *   single value is n. So we can index buckets by frequency:
 *     buckets[f] = list of values that appear exactly f times.
 *
 *   Step 1: Build a HashMap<Integer, Integer> of value -> frequency (same as V1).
 *   Step 2: Allocate List<Integer>[] buckets of size n + 1 (frequency 0..n).
 *   Step 3: For each (value, freq) entry, do buckets[freq].add(value).
 *   Step 4: Walk buckets from index n down to 1, collecting values until we
 *           have k of them. Return as int[].
 *
 * Why this beats V1:
 *   - V1 wastes work fully sorting all entries when we only care about the top k.
 *   - Bucket sort exploits the fact that frequencies are bounded integers in
 *     [1, n]. Indexing into an array beats comparison sort when the value
 *     range is small.
 *
 * Why not a min-heap?
 *   - Min-heap of size k gives O(n log k), which is great but not optimal.
 *   - Bucket sort gives true O(n), which is the theoretical floor for this
 *     problem (we have to look at every element at least once).
 *   - Heap is still a good fallback if memory is tight (heap is O(k), buckets
 *     are O(n)).
 *
 * Complexity:
 *   Time  O(n) - each element touched a constant number of times.
 *   Space O(n) - frequency map + buckets array.
 *
 * Key learnings:
 *   - Bucket sort is unlocked whenever your sort key is a bounded integer.
 *     Frequencies are bounded by the array length -> bucket sort fits.
 *   - Java doesn't allow `new List<Integer>[n + 1]` directly because of
 *     generics + arrays. Workarounds: cast `(List<Integer>[]) new List[n + 1]`,
 *     or use `List<List<Integer>>` instead.
 *   - "Top k" problems have three classic solutions worth knowing:
 *       (a) sort everything                    -> O(n log n)
 *       (b) min-heap of size k                 -> O(n log k)
 *       (c) bucket sort / quickselect          -> O(n)
 *     Pick based on whether the sort key is a bounded integer (bucket) or
 *     whether you need streaming / online behavior (heap).
 *
 * Real-world usage:
 *   - Twitter / X trending hashtags: count tweets per tag in a window, then
 *     bucket sort to find the top N trending in O(n).
 *   - Network monitoring "top talkers": IPs producing the most packets - each
 *     packet count is bounded, bucket sort finds the loudest in linear time.
 *   - DDoS detection: top requesting IPs per minute, where we need a fast
 *     rolling top-k over high-throughput data.
 *   - E-commerce "best sellers" widgets that update frequently and need
 *     low-latency top-k extraction.
 *   - Log analysis: most frequent error codes / endpoints in the last hour,
 *     where the count range is naturally bounded by request volume.
 *   - Recommendation pre-filter: cheap top-k popularity bucket before more
 *     expensive personalization scoring.
 */