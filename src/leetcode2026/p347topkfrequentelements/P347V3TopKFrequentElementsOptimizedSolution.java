package leetcode2026.p347topkfrequentelements;

import java.util.*;

public class P347V3TopKFrequentElementsOptimizedSolution {

    // HashMap count + min-heap of size k, Time O(d log k), Space O(k)
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: count frequencies.
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.merge(num, 1, Integer::sum);
        }

        // Step 2: min-heap ordered by frequency ascending.
        // PriorityQueue defaults to min-heap; comparator picks the field to compare by.
        PriorityQueue<Map.Entry<Integer, Integer>> heap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Step 3: offer every entry; whenever size exceeds k, poll the min.
        // The smallest in the heap is the current cutoff - anything bigger bumps it out.
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        // Step 4: drain heap into result. Order is low-to-high frequency
        // (min-heap pops smallest first); problem accepts any order.
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = heap.poll().getKey();
        }
        return result;
    }

    public static void main(String[] args) {
        P347V3TopKFrequentElementsOptimizedSolution solution = new P347V3TopKFrequentElementsOptimizedSolution();

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
 * Approach (V3 - min-heap of fixed size k):
 *   Step 1: Build a HashMap<Integer, Integer> of value -> frequency (same as V1/V2).
 *   Step 2: Maintain a min-heap (PriorityQueue ordered by frequency ascending),
 *           cap the size at k.
 *   Step 3: For each (value, freq) entry:
 *             - heap.offer(entry)
 *             - if heap.size() > k, heap.poll() (remove the lowest frequency)
 *   Step 4: After processing, the heap contains exactly the top-k by frequency.
 *           Drain it into the result array.
 *
 * Why min-heap (not max-heap) for "top k":
 *   - Counter-intuitive: we use a MIN-heap to find the K LARGEST.
 *   - Trick: the smallest item in our k-sized heap is the "cutoff". Any new
 *     item bigger than the cutoff bumps the current cutoff out, since after
 *     offering the heap exceeds size k and we poll the min.
 *   - A max-heap of all d entries would force O(d log d) - we'd build a heap
 *     over everything just to peel off k. The min-heap-of-k bounds the heap
 *     size, so each operation is O(log k), not O(log d).
 *
 * Complexity:
 *   Time  O(n + d log k)
 *           - n to count frequencies
 *           - d entries each go through O(log k) heap ops, where d = distinct
 *             values, k = top-k requested. Often k << d, so log k is tiny.
 *   Space O(n + k)
 *           - n for the freq map (unavoidable)
 *           - k for the heap (NOT n+1 like V2 - this is the big win)
 *
 * Why this often beats V2 in practice:
 *   - V2 allocates n+1 ArrayList objects up front, even when most stay empty.
 *   - V3 only allocates k entries in the heap. For typical k = 10, this is
 *     basically free regardless of n.
 *   - V3's "log k" factor is usually small (log 10 ~= 3.3); V2's "O(n)"
 *     contains hidden constants from object allocation that often dominate.
 *
 * Streaming friendliness:
 *   - V1 / V2 require all data to be present before processing - they need
 *     the full frequency map to start.
 *   - V3 can be adapted to streaming: as new (value, freq) pairs arrive,
 *     just offer + maybe-poll. Memory stays at O(k) regardless of how much
 *     data flows through.
 *   - This is why min-heap is the production answer when data volume is
 *     unbounded (logs, packets, events, market ticks).
 *
 * Key learnings:
 *   - Java's PriorityQueue defaults to a MIN-heap. To get max-heap, pass
 *     `Comparator.reverseOrder()`.
 *   - Custom comparator for entry-by-value:
 *       Comparator.comparingInt(Map.Entry::getValue)
 *   - "Top k" with bounded heap size is the canonical streaming algorithm -
 *     same trick used in MapReduce reducers, Spark aggregations, etc.
 *   - Three solutions to "top k" - pick by constraint:
 *       (a) sort all                -> O(n log n) - simple, fine for small n
 *       (b) bucket sort             -> O(n)       - fastest IF sort key is bounded int
 *       (c) min-heap of size k      -> O(n log k) - best space, streaming-friendly
 *
 * Real-world usage:
 *   - Kafka / streaming pipelines: maintain rolling "top k events per window"
 *     with bounded memory regardless of throughput.
 *   - Search engine suggestions: top k autocomplete suggestions per prefix,
 *     updated as new queries arrive.
 *   - Real-time DDoS mitigation: top k attacker IPs per second from a packet
 *     stream that may exceed millions of packets/sec.
 *   - Stock trading systems: top k most active symbols in the last minute,
 *     where the tick stream is unbounded but memory is fixed.
 *   - Recommendation systems: top k items per user from a sharded vote stream
 *     across many workers (each worker keeps its local top k via min-heap,
 *     then a final reducer merges).
 *   - Gaming leaderboards: top k high scores updated as new scores stream in,
 *     without needing to store every score ever seen.
 *   - SRE / observability: top k slowest endpoints / largest payloads in a
 *     time window, maintained continuously by ingestion pipelines.
 */