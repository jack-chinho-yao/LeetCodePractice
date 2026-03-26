package leetcode2026.p206reverselinkedlist;

import leetcode2026.common.ListNode;

public class P206V1ReverseLinkedListNaiveSolution {

    // Iterative - Time: O(n), Space: O(1)
    public ListNode reverseList(ListNode head) {
        ListNode temp = head;
        ListNode prev = null;

        while(head != null){
            temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;
        }
        return prev;
    }

    public static void main(String[] args) {
        P206V1ReverseLinkedListNaiveSolution solution = new P206V1ReverseLinkedListNaiveSolution();

        // Example 1: [1,2,3,4,5] -> [5,4,3,2,1]
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        printList(solution.reverseList(head1));

        // Example 2: [1,2] -> [2,1]
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        printList(solution.reverseList(head2));

        // Example 3: [] -> []
        printList(solution.reverseList(null));
    }

    private static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(",");
            curr = curr.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}

/*
 * 思路：
 * 反轉 linked list 的核心問題：改變 next 指向時，原本的下一個節點會丟失。
 * 所以需要一個 temp 先把下一個節點存起來，才能安心改指向。
 *
 * 三個變數各自的角色：
 * - prev：前一個節點（反轉後的指向目標），初始為 null
 * - head：目前正在處理的節點
 * - temp：暫存 head.next，避免改指向後找不到下一個
 *
 * 迴圈每一輪做四件事（概念上三步）：
 * 1. 存下一個：temp = head.next
 * 2. 反轉指向：head.next = prev
 * 3. 往前移動：prev = head, head = temp
 *
 * 過程示意 [1 → 2 → 3]：
 * 初始：prev=null, head=1
 * 第一輪：temp=2, 1→null,       prev=1, head=2
 * 第二輪：temp=3, 2→1→null,     prev=2, head=3
 * 第三輪：temp=null, 3→2→1→null, prev=3, head=null → 結束
 * return prev (即 3，新的 head)
 *
 * V1 Iterative: Time O(n), Space O(1) ← 最佳解
 * V2 Recursive: Time O(n), Space O(n) call stack
 */
