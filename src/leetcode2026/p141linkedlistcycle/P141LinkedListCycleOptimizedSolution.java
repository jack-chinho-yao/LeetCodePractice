package leetcode2026.p141linkedlistcycle;

import leetcode2026.common.ListNode;

public class P141LinkedListCycleOptimizedSolution {

    // Floyd's Cycle Detection (快慢指針)
    // Time: O(n), Space: O(1)
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        P141LinkedListCycleOptimizedSolution solution = new P141LinkedListCycleOptimizedSolution();

        // Test case 1: [3,2,0,-4], cycle at pos 1
        ListNode head1 = new ListNode(3);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(0);
        head1.next.next.next = new ListNode(-4);
        head1.next.next.next.next = head1.next; // cycle
        System.out.println("Test 1: " + solution.hasCycle(head1)); // true

        // Test case 2: [1,2], cycle at pos 0
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = head2; // cycle
        System.out.println("Test 2: " + solution.hasCycle(head2)); // true

        // Test case 3: [1], no cycle
        ListNode head3 = new ListNode(1);
        System.out.println("Test 3: " + solution.hasCycle(head3)); // false
    }


    /*
    Linked List Cycle考這種題目有什麼用途嗎下午3:31評估循環檢測題目的面試價值與實務應用。The user is asking about the practical purpose of LeetCode 141 (Linked List Cycle detection) - why this type of problem is asked in interviews and what real-world applications it has.Done好問題，這題考的是 Floyd's Cycle Detection（快慢指針），實際用途比你想的多：
真實應用場景：
死循環檢測 — 程式裡如果有東西互相引用形成環，就會無限迴圈。比如你做的 Spring Boot 專案裡，如果 Bean A 依賴 Bean B，Bean B 又依賴 Bean A，就是 circular dependency。Spring 內部就需要類似的機制去偵測這種環。
垃圾回收（GC） — JVM 的垃圾回收機制需要判斷物件之間的引用有沒有形成環，來決定能不能回收記憶體。
作業系統 — Deadlock detection，檢測資源等待是否形成循環等待。
Git — Git 內部用 DAG（有向無環圖），需要確保 commit 歷史不會形成環。
資料驗證 — 你做過的 RabbitMQ 訊息流，如果訊息路由設定錯了形成環，訊息會無限轉發。需要環檢測來防止。
面試真正考的是：
其實面試官不在乎你會不會解這題，他們想看的是你懂不懂快慢指針這個思維模式。這個技巧還能延伸到 Find Duplicate Number（#287）、Linked List 找中間點、判斷回文 Linked List 等題，一個模式解一堆題。
所以結論： 不是在考你背答案，是在考你能不能用 O(1) 空間解決「有沒有環」這類問題的思維能力。
*/

}