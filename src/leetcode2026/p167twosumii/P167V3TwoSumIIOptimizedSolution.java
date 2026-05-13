package leetcode2026.p167twosumii;

import java.util.Arrays;

// Two pointers on the sorted array. Time O(n), Space O(1).
public class P167V3TwoSumIIOptimizedSolution {

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        P167V3TwoSumIIOptimizedSolution solution = new P167V3TwoSumIIOptimizedSolution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 7, 11, 15}, 9))); // [1, 2]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 3, 4}, 6)));      // [1, 3]
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-1, 0}, -1)));       // [1, 2]
    }

    /*
    Optimized approach — Two Pointers:

    Because the array is sorted in non-decreasing order, we can point one index
    at each end and shrink the window based on how the current sum compares to
    the target:

        left  ──►                        ◄── right
        [ 2,   3,   4,   7,   11,   15 ]        target = 18

        sum = numbers[left] + numbers[right]
          if sum == target  -> done, return [left + 1, right + 1]
          if sum <  target  -> left++   (need a larger value)
          if sum >  target  -> right--  (need a smaller value)

    Why it works:
    - Moving `left` rightward only increases the sum (values are non-decreasing).
    - Moving `right` leftward only decreases the sum.
    - So each move eliminates exactly one index from needing further consideration,
      which means we visit each index at most once.

    Complexity:
    - Time  : O(n)  — pointers traverse the array once.
    - Space : O(1)  — meets the problem's constant-space requirement.

    Comparison vs. Naive:
    - Naive ignores sortedness → O(n^2).
    - Hash map (Two Sum I style) would also be O(n) time but O(n) space,
      which violates the "constant extra space" constraint of this problem.
    - Two pointers is the textbook fit here: exploits sortedness AND stays O(1) space.

    ────────────────────────────────────────────────────────────────────────
    Big-picture takeaway — why this problem actually matters:
    ────────────────────────────────────────────────────────────────────────

    The literal "find two numbers summing to target" problem rarely shows up
    in production code, and no JDK Collection exposes it as a method. What
    this problem really teaches is a reflex:

        "Data is sorted → reach for two pointers or binary search,
         NOT a HashMap."

    That reflex shows up everywhere in real systems:

    1. JDK internals
       - Arrays.sort() (TimSort): merging two sorted runs uses two pointers.
       - Collections.disjoint(), String.regionMatches(): two-pointer scans.
       - TreeSet.subSet() / TreeMap.subMap(): range traversal on sorted tree.

    2. Database engines
       - Sort-merge join: sort both tables, then two pointers to match rows.
         This is a core join algorithm in Postgres / MySQL / Oracle.
       - B+ tree range scans walk sorted leaf linked lists with two pointers.

    3. Search engines (Lucene / Elasticsearch)
       - Boolean query "A AND B" intersects sorted posting lists using
         two pointers. This is the backbone of keyword search.

    4. Trading systems (directly relevant to exchange/matching engines)
       - Order book matching: buy side descending, sell side ascending,
         two pointers walk toward each other until prices no longer cross.
         This is literally Two Sum II with "==" replaced by "price crosses".
       - Market data merge: k-way merge of sorted feeds from multiple venues.
       - Trade reconciliation / netting: sorted ledgers, two pointers to
         find offsetting pairs.

    5. Version control & diff
       - git diff / Myers algorithm operates on sorted line sequences with
         a two-pointer-style traversal.
       - 3-way merge is the three-pointer generalization.

    6. Distributed systems
       - Kafka / Cassandra SSTable compaction: k-way merge of sorted logs.
       - CRDT / vector clock merges traverse sorted event streams.

    The underlying principle, stated once:

        "When data has a monotonicity guarantee (sortedness, timestamps,
         priority), two pointers can exploit it to eliminate an entire
         row/column of possibilities per step, turning O(n^2) brute force
         into O(n) with O(1) extra space."

    So this problem is not about pairs summing to a target. It is about
    building the muscle memory that "sorted" is a free gift, and the
    right tool to unwrap it is two pointers — not a HashMap.
    */
}
