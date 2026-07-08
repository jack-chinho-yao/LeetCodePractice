-- LeetCode 182: Duplicate Emails
-- https://leetcode.com/problems/duplicate-emails/
--
-- Write a solution to report all the duplicate emails. An email is a duplicate
-- if it appears more than once in the table. Return the result in any order.
-- 找出所有重複出現（出現次數 > 1）的 email；回傳順序不限。
-- 注意：email 欄位保證不為 NULL。

-- =========================================
-- Schema setup
-- =========================================

DROP TABLE IF EXISTS 182_person;

CREATE TABLE 182_person (
    id    INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL
);

-- =========================================
-- Test data
-- =========================================

INSERT INTO 182_person (id, email) VALUES
    (1, 'a@b.com'),
    (2, 'c@d.com'),
    (3, 'a@b.com');

-- =========================================
-- Expected output (LeetCode 標準答案)
-- =========================================
--   +---------+
--   | Email   |
--   +---------+
--   | a@b.com |  ← 出現 2 次（id 1, 3）
--   +---------+
--   c@d.com 只出現 1 次，不算重複。
--
-- 提示 / hints（不破梗）：
--   - 「出現次數 > 1」直接對應 GROUP BY + COUNT，篩選聚合結果用 HAVING（不能用 WHERE）。
--   - 另一條思路：自連接（self-join）找出有相同 email 但 id 不同的列，再去重。
--   - 輸出欄位名 LeetCode 預期是 `Email`（大寫 E），記得用別名對齊。

-- =========================================
-- My solution
-- =========================================
-- TODO: implement


-- =========================================
-- 其他解法 / Alternative approaches
-- =========================================
/*
 * TODO: 寫完解法後在這裡補上其他思路、比較、易錯點，以及
 *       真實世界用途 (real-world usage)。
 */
