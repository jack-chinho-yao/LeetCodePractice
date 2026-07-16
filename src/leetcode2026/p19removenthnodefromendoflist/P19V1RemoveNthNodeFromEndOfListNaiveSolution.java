package leetcode2026.p19removenthnodefromendoflist;

import leetcode2026.common.ListNode;

public class P19V1RemoveNthNodeFromEndOfListNaiveSolution {

    // Two-pass: 先數出長度 length，再刪掉第 (length - n) 個（從頭 0-based）
    // Time: O(n)（掃兩趟），Space: O(1)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int length = 0;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            length++;
        }

        // 要刪的節點從頭算是第 (length - n) 個（0-based）
        // 若 length - n == 0，代表要刪的是 head 本身
        int idxToRemove = length - n;
        if (idxToRemove == 0) {
            return head.next;
        }

        // 走到「待刪節點的前一個」
        ListNode prev = head;
        for (int i = 0; i < idxToRemove - 1; i++) {
            prev = prev.next;
        }
        prev.next = prev.next.next;

        return head;
    }

    public static void main(String[] args) {
        P19V1RemoveNthNodeFromEndOfListNaiveSolution solution = new P19V1RemoveNthNodeFromEndOfListNaiveSolution();

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
     * 思路（Two-pass 直觀解）：
     *   「倒數第 n 個」= 「正數第 (length - n) 個」（0-based）。
     *   第一趟先數出總長度 length，第二趟走到「待刪節點的前一個」把它跳過。
     *
     * 邊界處理：
     *   - 若 length - n == 0，代表要刪的是 head 本身，直接回傳 head.next。
     *   - 用長度計算可避免手動處理很多 off-by-one，思路最好懂。
     *
     * 缺點：掃了兩趟。進階解（見 V2）用「雙指針間隔 n」可以一趟完成。
     */
}
