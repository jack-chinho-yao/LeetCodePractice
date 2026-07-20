package leetcode2026.p33searchinrotatedsortedarray;

// Modified binary search: decide which half is sorted, then bound-check. Time O(log n), Space O(1).
public class P33V2SearchInRotatedSortedArrayOptimizedSolution {

    public int search(int[] nums, int target) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P33V2SearchInRotatedSortedArrayOptimizedSolution solution =
                new P33V2SearchInRotatedSortedArrayOptimizedSolution();
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));  // 4
        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));  // -1
        System.out.println(solution.search(new int[]{1}, 0));                    // -1
    }

    /*
    === Guiding questions ===

    這是 153 的進階綜合題。做過 153 再做這題會順很多。

    1. 旋轉後陣列被斷點切成「左段、右段」兩條各自遞增的線。
       對任一個 mid，它一定落在其中一段。你怎麼「一眼判斷」mid 在左段還是右段？
       （提示：拿 nums[mid] 跟 nums[lo] 比，就能判斷 mid 這半邊有沒有含斷點。）

    2. 核心策略不是直接找 target，而是分兩步：
       (a) 先確定「lo..mid 這半邊」和「mid..hi 這半邊」中，哪一半是「乾淨的有序區間」。
       (b) 對那個有序的半邊，用單純的大小比較判斷 target 在不在它的範圍內：
           - 在範圍內 → 往那半邊收縮
           - 不在      → 往另一半收縮
       關鍵：永遠拿「有序的那一半」來做區間判斷，因為只有有序區間你才能用
       「頭 <= target <= 尾」這種簡單判斷。

    3. 邊界很容易錯的地方：判斷 target 在有序半邊時，等號要不要帶（<= 還是 <）？
       用 target 剛好等於端點的 case 手動驗一次。

    4. 另一條路：先用 153 的方法找到 pivot（最小值 index），
       這樣陣列就被切成兩段各自有序 → 判斷 target 落在哪段 → 對那段做標準 704 二分。
       兩種寫法都試試，體會「一次二分 vs 兩次二分」的取捨。

    自測：nums=[1], target=0（找不到）和 target=1（找到 index 0），你的迴圈會不會越界？
    */
}
