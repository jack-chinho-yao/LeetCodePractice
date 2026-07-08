-- LeetCode 178: Rank Scores
-- https://leetcode.com/problems/rank-scores/
--
-- Write a solution to find the rank of the scores. The ranking rules are:
--   - Scores ranked from highest to lowest.
--   - Tie  → same rank.
--   - After a tie, the next rank is the next consecutive integer (no holes / 不跳號).
-- Return the result table ordered by score in descending order.
-- 依分數由高到低給名次：同分並列同名次，且名次連續不跳號（1,1,2,3,3,4 而非 1,1,3,4...）。

-- =========================================
-- Schema setup
-- =========================================

DROP TABLE IF EXISTS 178_scores;

CREATE TABLE 178_scores (
    id    INT PRIMARY KEY,
    score DECIMAL(3, 2)
);

-- =========================================
-- Test data
-- =========================================

INSERT INTO 178_scores (id, score) VALUES
    (1, 3.50),
    (2, 3.65),
    (3, 4.00),
    (4, 3.85),
    (5, 4.00),
    (6, 3.65);

-- =========================================
-- Expected output (LeetCode 標準答案)
-- =========================================
--   +-------+------+
--   | score | rank |
--   +-------+------+
--   | 4.00  | 1    |  ← 並列第 1（id 3, 5）
--   | 4.00  | 1    |
--   | 3.85  | 2    |
--   | 3.65  | 3    |  ← 並列第 3（id 2, 6）
--   | 3.65  | 3    |
--   | 3.50  | 4    |  ← tie 之後不跳號，仍是 4 而非 5
--   +-------+------+
--
-- 提示 / hints（不破梗）：
--   - 「同分同名次 + 不跳號」是關鍵字 → 想想哪個排名語意正好符合（對比 RANK 會跳號）。
--   - `rank` 在 MySQL 8+ 是保留字，輸出欄位別名要用反引號包起來：`rank`。
--   - 也有不靠 window function 的古典寫法（相容最老的 DB）可想想。

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