package leetcode2026.p206reverselinkedlist;

import leetcode2026.common.ListNode;

public class P206V1ReverseLinkedListNaiveSolutionPractice {
/*206邏輯是不是要先把head的下一個存起來避免不見 才可以動head的指針？

  Read 1 file (ctrl+o to expand)

⏺ 對,你的理解完全正確,而且你的程式碼已經正確做到這件事了。

  關鍵就在這一行順序:

  temp2 = head.next;   // ① 先存下一個,不然等等就找不到了
  head.next = temp1;   // ② 反轉指向(這一步會「切斷」原本往後的路)
  temp1 = head;        // ③ prev 前移
  head = temp2;        // ④ head 前移(靠剛剛存的 temp2)

  為什麼一定要先存?

  第 ② 步 head.next = temp1 一旦執行,head.next 原本指向的那個節點(下一個)就被覆蓋掉了。如果你沒先存,執行完 ② 之後就再也沒有任何變數指向原本的下一個節點 → 整條後面的鏈就遺失了,沒辦法繼續往下走。

  以 1 → 2 → 3 為例,假設不先存:
  head = 1, temp1 = null
  head.next = temp1  →  1.next = null   // 此刻 2 已經無人指向,遺失!
  head = head.next   →  head = null     // 想往前走卻走不到 2 了

  所以順序不能反。先 temp2 = head.next 把「回家的
  路」記起來,才能安心地把 head.next 改掉。

  小提醒(命名):你的邏輯完全對,只是變數名 temp1 / temp2 語意較弱。對照你檔案底部註解用的 prev / temp,可以改成:

  ListNode prev = null;   // temp1 的角色
  ListNode next = null;   // temp2 的角色
  while (head != null) {
      next = head.next;   // 先存
      head.next = prev;   // 反轉
      prev = head;        // prev 前移
      head = next;        // head 前移
  }
  return prev;

  功能一模一樣,只是讀起來更貼近「prev / 目前 / next」三個角色。要不要改看你,原本的已經是 O(n) time、O(1) space 的最佳解了。

*/
    // Iterative - Time: O(n), Space: O(1)
    public ListNode reverseList(ListNode head) {
        ListNode temp1 = null;
        ListNode temp2 = null;
        while(head != null){
            temp2 = head.next;
            head.next = temp1;
            temp1 = head;
            head = temp2;
        }

        return temp1;
    }

    public static void main(String[] args) {
        P206V1ReverseLinkedListNaiveSolutionPractice solution = new P206V1ReverseLinkedListNaiveSolutionPractice();

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
