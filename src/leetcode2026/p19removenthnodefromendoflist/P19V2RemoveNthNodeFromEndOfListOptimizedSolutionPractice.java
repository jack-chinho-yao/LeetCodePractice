package leetcode2026.p19removenthnodefromendoflist;

import leetcode2026.common.ListNode;

public class P19V2RemoveNthNodeFromEndOfListOptimizedSolutionPractice {

    // One-pass 雙指針：fast 先走 n 步，再讓 fast/slow 一起走到底，slow 就停在待刪節點的前一個
    // Time: O(n)（只掃一趟），Space: O(1)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        for(int i = 0; i < n; i++){
            fast = fast.next;
        }

        while (fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;


        return dummy.next;
    }

    public static void main(String[] args) {
        P19V2RemoveNthNodeFromEndOfListOptimizedSolutionPractice solution = new P19V2RemoveNthNodeFromEndOfListOptimizedSolutionPractice();

        // Test 1: [1,2,3,4,5], n = 2 -> [1,2,3,5]
        ListNode head1 = build(new int[]{1, 2, 3, 4, 5});
        System.out.println("Test 1: " + toString(solution.removeNthFromEnd(head1, 2))); // 1 2 3 5

        // Test 2: [1], n = 1 -> []
        ListNode head2 = build(new int[]{1});
        System.out.println("Test 2: " + toString(solution.removeNthFromEnd(head2, 1))); // (empty)

        // Test 3: [1,2], n = 1 -> [1]
        ListNode head3 = build(new int[]{1, 2});
        System.out.println("Test 3: " + toString(solution.removeNthFromEnd(head3, 1))); // 1

        // Test 4: [1,2], n = 2 -> [2]（刪 head）
        ListNode head4 = build(new int[]{1, 2});
        System.out.println("Test 4: " + toString(solution.removeNthFromEnd(head4, 2))); // 2
    }

    private static ListNode build(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : vals) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(' ');
        }
        return sb.toString();
    }

    /*
     * 思路（One-pass 雙指針，只掃一趟）：
     *   核心：讓 fast 與 slow 保持「間隔 n 個節點」。
     *   當 fast 走到最後一個節點時，slow 剛好停在「倒數第 n+1 個」= 待刪節點的前一個。
     *
     * 過程示意（[1,2,3,4,5], n = 2）：
     *   dummy -> 1 -> 2 -> 3 -> 4 -> 5
     *   1. fast 先走 2 步：fast 在節點 2，slow 在 dummy
     *   2. 一起走到 fast.next == null：fast 在 5，slow 在 3
     *   3. slow.next = slow.next.next -> 刪掉 4，得 [1,2,3,5]
     *
     * dummy 節點的用途：
     *   若要刪的是 head（n == length），沒有 dummy 就得特判。
     *   加了 dummy，slow 可以停在 dummy 上，「刪 head」與「刪中間」邏輯完全一致。
     *
     * 與 V1 比較：
     *   V1 掃兩趟（先數長度再刪），思路直觀但多一趟。
     *   V2 靠「固定間隔的雙指針」一趟搞定，是這題的經典最佳解。
     * ❯ v1 v2看起來沒差太多吧？？

⏺ 你說得對，在大 O 上完全一樣——兩個都是 Time O(n) / Space O(1)。這題的 V1/V2 差距確實比大多數題目小，我老實說：

  實質差別只有「掃幾趟」

  - V1：掃 2 趟（一趟數長度，一趟刪）
  - V2：掃 1 趟

  但 2n 和 n 在大 O 都是 O(n)，實務上對 5 個節點的鏈表根本感覺不到差。所以你的直覺沒錯——這題不像別的題（例如 Two Sum 從 O(n²)→O(n)）有數量級差距。

  那 V2 到底「值」在哪？

  不是效能，是思維模式：

  1. 雙指針「固定間隔」技巧 — fast 先走 n 步再一起走，這個 pattern 會重複出現在很多鏈表題。這才是面試官想看的。
  2. 一趟解決「需要往回數」的問題 — 鏈表不能倒著走，V2 用「間隔」把「倒數第 n」轉成「正著走」，這個轉換思路本身是重點。

  真正的差別其實在別的題才看得出來

  如果這題改成「鏈表非常長、而且只能讀一次（stream，不能存下來重掃）」，V1 就直接 GG（沒辦法掃第二趟），V2 才行。這種「single-pass 限制」在處理大資料流時是真的會遇到的。

  ---
  所以結論：你的觀察是對的，這題 V1/V2 沒有數量級差異。留兩版的價值是練「雙指針間隔」這個手法，不是為了效能。

  如果你覺得這題留兩版有點多餘，其實也可以只留 V2（經典解），或把 V1 降級成註解區的「另解」。要我幫你合併嗎？

*
* ❯ v2 只掃一次？ 但是快慢指針各自走一趟 不是也是走兩趟嗎

⏺ 好問題，這正是「一趟 / 兩趟」最容易誤會的地方。關鍵是：「趟數」不是數指針的數量，而是數「有沒有從頭重新掃一遍」。

  先看兩者的實際差異

  - V1（兩趟）：走到尾巴 → 回到 head 重新開始 → 再走一次。指針倒帶回頭了。
  - V2（一趟）：fast 和 slow 都只往前、從不回頭，兩個指針在同一次前進中一起完成。

  fast 和 slow 不是「各走各的兩趟」，而是同一趟裡前後錯開的兩個位置。slow 只是跟在 fast 後面 n 步，沒有自己重新從頭掃。

  用「讀資料流」的角度想最清楚

  想像鏈表是一條只能往前、看過就消失的輸送帶（stream）：

  V1：看到尾巴才知道長度 → 但輸送帶已經跑完了，東西不見了
      → 只能「倒帶」重看一次     ❌  stream 做不到

  V2：fast 是「探路的手」，先伸出去 n 格
      slow 是「跟著的手」，兩隻手一起往前移
      fast 碰到底時，slow 剛好停在要刪的前一個  ✅  全程沒倒帶

  那「總共移動幾步」呢？（你可能真正在問這個）

  - V1：數長度 L 步 + 再走到刪除點約 L 步 = 約 2L 步
  - V2：fast 走 L 步 + slow 走 (L−n) 步 = 約 2L−n 步

  所以論「指針移動總次數」，兩者其實差不多（V2 還略少一點點）。

  重點來了 👉 「一趟 vs 兩趟」不是在比移動次數，而是在比**「需不需要把整條 list 從頭再看一遍」**：

  - V1 需要回頭重掃 → 如果資料只能讀一次（stream、超大檔案、網路封包），就 GG
  - V2 永遠不回頭 → 邊讀邊處理就能完成

  ---
  所以你的直覺沒錯：兩個指針加起來的移動量差不多。V2 的價值不在「少走幾步」，而在「全程單向、不用倒帶」——這才是「single-pass」真正的意義。

     */
}
