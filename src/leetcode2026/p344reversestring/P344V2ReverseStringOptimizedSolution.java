package leetcode2026.p344reversestring;

import java.util.Arrays;

public class P344V2ReverseStringOptimizedSolution {
    public void reverseString(char[] s) {
        int length = s.length;
        int left = 0;
        int right = length - 1;
        while(left < right){
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        P344V2ReverseStringOptimizedSolution solution = new P344V2ReverseStringOptimizedSolution();

        char[] s1 = {'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(s1);
        System.out.println(Arrays.toString(s1)); // expected: [o, l, l, e, h]

        char[] s2 = {'H', 'a', 'n', 'n', 'a', 'h'};
        solution.reverseString(s2);
        System.out.println(Arrays.toString(s2)); // expected: [h, a, n, n, a, H]
    }

    /*
    Optimized approach — Two Pointers (head & tail, walking toward center).

    Algorithm:
      left  = 0
      right = s.length - 1
      while (left < right):
          swap s[left] and s[right]   // temp three-step
          left++
          right--

    Complexity:
    - Time  : O(n)  — each element is touched at most once.
    - Space : O(1)  — only a handful of int/char variables.
    - In-place, satisfies the problem's "O(1) extra memory" constraint.

    Why two pointers applies here:
    - The "monotonicity" being exploited is not value order, but INDEX order
      (0, 1, 2, ...). The reversal problem is structurally "pair up head & tail,
      then next-head & next-tail, ..." — which is exactly what head/tail two
      pointers express cleanly.

    ────────────────────────────────────────────────────────────────────────
    Sub-topic: why we use the "temp three-step" swap, not XOR swap
    ────────────────────────────────────────────────────────────────────────

    The textbook swap in Java:
        char temp = s[left];
        s[left]   = s[right];
        s[right]  = temp;

    There exists a "clever" swap without a temp variable (XOR swap):
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

    DO NOT use XOR swap in production or interviews. Reasons:

    1. No practical performance benefit.
       Modern JIT compilers (HotSpot, Graal) optimize temp-based swap down
       to register moves with near-zero overhead. XOR swap, by contrast,
       creates three instructions with strict data dependency, preventing
       CPU pipeline parallelism. Benchmarks often show XOR swap is SLOWER.

    2. Silent correctness bug on the same-index edge case.
       If left == right (or any two indices alias the same slot), XOR swap
       silently zeroes out the data:
           arr[i] = arr[i] ^ arr[i];  // becomes 0
           arr[i] = arr[i] ^ arr[i];  // still 0
           arr[i] = arr[i] ^ arr[i];  // still 0
       Temp swap has no such bug — worst case it's a no-op.

    3. Readability is significantly worse.
       "char temp = ...; a = b; b = temp;" reads as "swap" at a glance.
       XOR swap requires the reader to recall bit-manipulation identities.

    ────────────────────────────────────────────────────────────────────────
    Interview answer (bilingual) — if asked "do you know the XOR swap trick?"
    ────────────────────────────────────────────────────────────────────────

    🇬🇧 English:
    "Yes, I'm aware of it, but I wouldn't reach for it in production.
     Modern JIT compilers already optimize temp-based swaps down to
     near-zero overhead. Meanwhile XOR swap has worse readability and
     a silent correctness bug when both indices point to the same slot —
     it zeros out the data. So it's strictly worse on reliability and
     maintainability."

    🇹🇼 中文：
    「知道，但我不會在 production 用。原因是現代 JIT 編譯器對 temp swap
     已經優化到幾乎沒有任何 overhead，而 XOR swap 一來可讀性差，
     二來有同 index 陷阱 — 如果兩個 index 指向同一格，資料會被靜悄悄清零。
     所以在可靠性和維護性上都是明顯較差的選擇。」

    Key vocabulary for this interview topic:
    - readability            / 可讀性
    - maintainability        / 維護性
    - overhead               / 開銷 / 額外成本
    - silent (correctness) bug / 靜悄悄的正確性 bug
    - edge case              / 邊界情況
    - strictly worse         / 嚴格更差
    - legacy micro-optimization / 過時的微優化

    Bottom line: XOR swap is a 1970s-era micro-optimization for memory-
    constrained systems. In Java / Go / modern C++, it has no place.
    */
}
