package leetcode2026.p704binarysearch;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

/**
 * 手刻的小測試工具（沒有用 JUnit，純 main + 自己數 pass/fail），
 * 示範我平常怎麼驗一個演算法對不對。三個層次：
 *   1. exampleTests()  — 題目給的範例，最基本的 smoke test。
 *   2. edgeTests()     — 「人腦想得到的陷阱」：邊界、空陣列、頭尾、找不到。
 *   3. stressTest()    — 「人腦想不到的陷阱」：對拍一個一定正確但很慢的 oracle，
 *                        丟大量隨機輸入，讓機器幫你找反例。
 *
 * 核心觀念 = differential testing（對拍）：
 *   我不去「證明」快版對，而是準備一個「笨到不可能錯」的慢版當標準答案（oracle），
 *   兩邊餵一樣的輸入，只要有一組輸出不同 → 快版就有 bug，而且直接印出反例。
 */
public class P704BinarySearchTest {

    // 受測對象（system under test）。想測別份解答，改這一行就好。
    private static final P704V2BinarySearchOptimizedSolutionClaude SUT =
            new P704V2BinarySearchOptimizedSolutionClaude();

    // ---- 迷你斷言框架 ----
    private static int passed = 0;
    private static int failed = 0;

    private static void check(int[] nums, int target, int expected) {
        int actual = SUT.search(nums, target);
        if (actual == expected) {
            passed++;
        } else {
            failed++;
            System.out.println("  ✗ FAIL nums=" + Arrays.toString(nums)
                    + " target=" + target + " → expected " + expected + " but got " + actual);
        }
    }

    /**
     * Oracle（標準答案）：故意寫最笨的線性掃描。
     * 它慢（O(n)），但「笨到不可能錯」——這正是 oracle 該有的特質。
     */
    private static int linearOracle(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;   // 陣列去重過 → 第一個命中就是唯一答案
        }
        return -1;
    }

    public static void main(String[] args) {
        exampleTests();
        edgeTests();
        stressTest();

        System.out.println();
        System.out.println("==== 總計: " + passed + " passed, " + failed + " failed ====");
        if (failed > 0) {
            throw new AssertionError(failed + " 個測試失敗");   // CI 會因為非零結束碼而變紅
        }
    }

    /** 1. 題目範例 — 最基本的 sanity check。 */
    private static void exampleTests() {
        System.out.println("[exampleTests]");
        check(new int[]{-1, 0, 3, 5, 9, 12}, 9, 4);
        check(new int[]{-1, 0, 3, 5, 9, 12}, 2, -1);
        check(new int[]{5}, 5, 0);
    }

    /** 2. 邊界 — 這些是二分最常掛的地方，每一條都對應一個具體的失敗模式。 */
    private static void edgeTests() {
        System.out.println("[edgeTests]");
        int[] n = {-1, 0, 3, 5, 9, 12};
        check(n, -1, 0);            // 命中「第一格」— lo 邊界
        check(n, 12, 5);            // 命中「最後一格」— hi 邊界（你之前 break 版就是掛在這）
        check(n, -100, -1);         // 比全部都小 — 往左收到空
        check(n, 100, -1);          // 比全部都大 — 往右收到空
        check(n, 4, -1);            // 落在兩個值中間、不存在
        check(new int[]{}, 5, -1);  // 空陣列 — 迴圈一次都不該進，直接 -1
        check(new int[]{7}, 7, 0);  // 單元素、命中
        check(new int[]{7}, 8, -1); // 單元素、沒命中
        check(new int[]{1, 2}, 1, 0);  // 雙元素，測 mid 取哪一個 / 會不會漏掉
        check(new int[]{1, 2}, 2, 1);
    }

    /** 3. 對拍 — 20 萬組隨機輸入 vs oracle，機器幫你找人想不到的反例。 */
    private static void stressTest() {
        System.out.println("[stressTest] 對拍 200000 組隨機輸入...");
        Random r = new Random(42);   // 固定 seed → 每次跑結果可重現，出 bug 好回溯
        int cases = 200_000;
        int mismatches = 0;

        for (int t = 0; t < cases; t++) {
            // 用 TreeSet 一次搞定「排序 + 去重」——因為題目保證 nums 已排序且唯一。
            int len = r.nextInt(8);                     // 長度 0~7，含空陣列
            TreeSet<Integer> set = new TreeSet<>();
            while (set.size() < len) set.add(r.nextInt(20) - 5);  // 值域 -5~14，故意窄，逼出重複命中/邊界
            int[] nums = set.stream().mapToInt(Integer::intValue).toArray();
            int target = r.nextInt(20) - 5;

            int expected = linearOracle(nums, target);
            int actual = SUT.search(nums, target);
            if (expected != actual) {
                mismatches++;
                if (mismatches <= 5) {   // 只印前 5 個反例，免得洗版
                    System.out.println("  ✗ MISMATCH nums=" + Arrays.toString(nums)
                            + " target=" + target + " oracle=" + expected + " sut=" + actual);
                }
            }
        }

        if (mismatches == 0) {
            passed++;
            System.out.println("  ✓ " + cases + " 組全部與 oracle 一致");
        } else {
            failed++;
            System.out.println("  ✗ " + mismatches + " 組不一致");
        }
    }
}
