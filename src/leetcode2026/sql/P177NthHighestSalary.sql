-- LeetCode 177: Nth Highest Salary
-- https://leetcode.com/problems/nth-highest-salary/
--
-- Write a SQL query / function to get the Nth highest salary from the Employee table.
-- If there is no Nth highest salary, return null.
-- 從 Employee 表中找出第 N 高的薪水；若不存在則回傳 null。
-- 注意：重複薪資只算一個排名（distinct salary）。

-- =========================================
-- Schema setup
-- =========================================

DROP TABLE IF EXISTS 177_employee;

CREATE TABLE 177_employee (
    id     INT PRIMARY KEY,
    salary INT
);

-- =========================================
-- Test data
-- =========================================

INSERT INTO 177_employee (id, salary) VALUES
    (1, 100),
    (2, 200),
    (3, 300);

-- Example 1: N = 2  → 200
-- Example 2: N = 4  → null  (只有 3 個不同薪資)
-- Example 3 (tie):  若有 (1,100), (2,200), (3,200) 則
--                   N = 2 → 100 （因為 distinct 後排名為 200, 100）

-- =========================================
-- Expected output (LeetCode 標準答案)
-- =========================================
--   getNthHighestSalary(2)
--   +------------------------+
--   | getNthHighestSalary(2) |
--   +------------------------+
--   | 200                    |
--   +------------------------+

-- =========================================
-- My solution（思路 1: DISTINCT + ORDER BY + LIMIT/OFFSET）
-- =========================================
-- Approach: 把薪資 DISTINCT 後由大到小排序，跳過前 N-1 筆，取第 1 筆。
--           LIMIT/OFFSET 不能直接吃變數運算式，所以先把 N-1 算進一個變數 M。
--           找不到時 LIMIT 取出 0 列，外層 SELECT 用子查詢自動變 NULL，符合題目要求。

CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    DECLARE M INT;
    SET M = N - 1;
    RETURN (
        SELECT DISTINCT salary
        FROM 177_employee
        ORDER BY salary DESC
        LIMIT 1 OFFSET M
    );
END;


-- =========================================
-- 其他解法 / Alternative approaches
-- =========================================
/*
 * 思路 2: DENSE_RANK window function (MySQL 8+)
 *   CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
 *   BEGIN
 *     RETURN (
 *       SELECT DISTINCT salary FROM (
 *         SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
 *         FROM 177_employee
 *       ) t
 *       WHERE rnk = N
 *     );
 *   END;
 *
 *   - DENSE_RANK 對 tie 給相同排名、不跳號，正好對應「distinct 第 N 高」。
 *   - 找不到時子查詢回傳 0 列 → NULL。
 *
 * 思路 3: 自相關子查詢（最古老、相容性最好）
 *   RETURN (
 *     SELECT salary FROM 177_employee e1
 *     WHERE N - 1 = (
 *         SELECT COUNT(DISTINCT e2.salary)
 *         FROM 177_employee e2
 *         WHERE e2.salary > e1.salary
 *     )
 *     LIMIT 1
 *   );
 *   - 「比我高的 distinct 薪水恰好有 N-1 個」就是第 N 高。
 *   - O(n²)，資料量大時慢，但不依賴 LIMIT/OFFSET 或 window function。
 *
 * 三種解法比較：
 *   思路 1 (OFFSET，本檔)    — 最直覺，所有版本 MySQL 都支援；要先把 N-1 存進變數。
 *   思路 2 (DENSE_RANK)      — 最現代，可讀性最佳；需要 MySQL 8+ 或同等的 window function。
 *   思路 3 (自相關子查詢)    — 相容性最高、最透明，但效能差，正式環境不推薦。
 *
 * 易錯點：
 *   - 忘了 DISTINCT → tie 時排名會跳掉（用 RANK 而非 DENSE_RANK 也會有同樣問題）。
 *   - LIMIT N-1, 1 寫不出來（MySQL LIMIT 不允許運算式），必須先 SET 變數。
 *   - 找不到時要回傳 NULL，不要寫 IFNULL(..., 0) 之類把 NULL 蓋掉的處理。
 *
 * 真實世界用途 (real-world usage)：
 *   - 薪資/HR 系統：找出公司 / 部門 / 職等的第 N 高薪，做薪酬市場比較。
 *   - 電商：商品價格分析（第 N 高定價、第 N 高客單價）。
 *   - 排行榜：遊戲分數、考試成績取第 N 名（distinct 分數 → 排名意義）。
 *   - 金融：股票歷史第 N 高收盤價、第 N 大成交額。
 *   - 監控告警：歷史第 N 高 QPS / latency，作為動態 threshold 基準。
 */
