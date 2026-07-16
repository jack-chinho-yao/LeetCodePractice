-- LeetCode 180. Consecutive Numbers
-- Find all numbers that appear at least three times consecutively.
-- Return the result table in any order.

-- ============================================================
-- Schema
-- ============================================================
DROP TABLE IF EXISTS 180_logs;

CREATE TABLE 180_logs (
    id  INT PRIMARY KEY,   -- id is the autoincrement primary key
    num VARCHAR(10)
);

-- ============================================================
-- Data (LeetCode example 1)
-- ============================================================
INSERT INTO 180_logs (id, num) VALUES
    (1, '1'),
    (2, '1'),
    (3, '1'),
    (4, '2'),
    (5, '1'),
    (6, '2'),
    (7, '2');

-- Expected output: ConsecutiveNums = 1
-- (num "1" appears consecutively at id 1,2,3)

-- ============================================================
-- Solution 1: self-join on consecutive ids
-- ============================================================
SELECT DISTINCT l1.num AS ConsecutiveNums
FROM 180_logs l1
JOIN 180_logs l2 ON l1.id = l2.id - 1 AND l1.num = l2.num
JOIN 180_logs l3 ON l1.id = l3.id - 2 AND l1.num = l3.num;

-- ============================================================
-- Solution 2: window function (LEAD)
-- ============================================================
-- SELECT DISTINCT num AS ConsecutiveNums
-- FROM (
--     SELECT num,
--            LEAD(num, 1) OVER (ORDER BY id) AS next1,
--            LEAD(num, 2) OVER (ORDER BY id) AS next2
--     FROM 180_logs
-- ) t
-- WHERE num = next1 AND num = next2;

/*
思路
----
「連續出現 3 次」= 有 3 個 id 相鄰 (id, id+1, id+2) 且 num 相同。

解法 1：self-join
  把同一張表 join 三次，用 id 差 1、差 2 對齊三個相鄰列，
  再要求三列的 num 相等。DISTINCT 去掉重複的 num。

  l1.id  l2.id  l3.id
    1      2      3     -> num 皆為 '1'  ✔ 命中
    ...

解法 2：window function
  用 LEAD 取後面第 1、第 2 列的 num，三者相等即命中。
  資料量大時通常比 self-join 快，只掃一次表。

注意
----
- num 用 VARCHAR，比較時字串相等即可。
- 若 id 不保證連續遞增（中間有缺號），self-join 的 id±1 仍成立，
  因為它比的是「值差」，只要缺號就不算相鄰，符合題意。
*/