-- P7: Top 3 traders by total trade volume in each region
-- Difficulty: Medium | Topic: Window Functions (ROW_NUMBER + PARTITION BY)

SELECT region, trader_name, total_volume, rn
FROM (
    SELECT tr.region,
           tr.trader_name,
           SUM(t.quantity * t.price) AS total_volume,
           ROW_NUMBER() OVER (
               PARTITION BY tr.region
               ORDER BY SUM(t.quantity * t.price) DESC
           ) AS rn
    FROM traders tr
    JOIN trades t ON tr.trader_id = t.trader_id
    GROUP BY tr.region, tr.trader_name
)
WHERE rn <= 3;

/*
 * 核心概念:
 * 1. PARTITION BY region = 每個 region 各自排名，從 1 開始
 * 2. ROW_NUMBER() = 嚴格遞增 1,2,3... 同分也給不同名次
 *    - RANK()       = 同分同名次，下一名跳號 (1,1,3)
 *    - DENSE_RANK() = 同分同名次，不跳號 (1,1,2)
 * 3. Window function 不能放在 WHERE 裡 → 必須用子查詢包一層，外層再 WHERE rn <= 3
 * 4. Window function 的 ORDER BY 不能用 SELECT 別名 → 要寫完整的 SUM(...)
 * 5. 需要 GROUP BY 因為有 SUM() — 先聚合出每個 trader 的 total_volume，再排名
 *
 * 常見錯誤:
 * 1. PARTITION BY 後面放聚合函數 → 應放分組欄位 (region)
 * 2. WHERE rn <= 3 放在子查詢裡 → Window function 在 WHERE 之後才執行，會報錯
 * 3. ORDER BY total_volume (用別名) → Window function 裡不能用別名
 * 4. 忘了 GROUP BY → 有 SUM() 就一定要 GROUP BY
 * 5. Oracle 子查詢不支援 AS alias → 寫 ) n 或不寫都行，不要寫 ) AS n
 */



/*


⏺ 改好了。Oracle → MySQL 差異：
  - NUMBER → INT / DECIMAL
  - VARCHAR2 → VARCHAR
  - DATE '2026-03-01' → '2026-03-01'
  - 不用 COMMIT

  Window functions (ROW_NUMBER, RANK, DENSE_RANK, PARTITION BY) MySQL 8.0+ 語法跟 Oracle
  一樣，不用改。去跑吧。

✻ Cogitated for 56s

❯ 直接給我一些語法好了 我想快速懂這些東西
⎿  ⧉ Selected 87 lines from src/interview2026/ice/sql/Q7SampleData.sql in IntelliJ IDEA



⏺ 直接跑這些就能看出差異：

  -- 1. 基本 Window Function — 不分組，全部一起排
SELECT tr.region, tr.trader_name,
       SUM(t.quantity * t.price) AS total_volume,
       ROW_NUMBER() OVER (ORDER BY SUM(t.quantity * t.price) DESC) AS rn,
    RANK()       OVER (ORDER BY SUM(t.quantity * t.price) DESC) AS rnk,
    DENSE_RANK() OVER (ORDER BY SUM(t.quantity * t.price) DESC) AS drnk
FROM traders tr
         JOIN trades t ON tr.trader_id = t.trader_id
WHERE t.status = 'COMPLETED'
GROUP BY tr.region, tr.trader_name;

-- 2. 加 PARTITION BY — 每個 region 各自排
SELECT tr.region, tr.trader_name,
       SUM(t.quantity * t.price) AS total_volume,
       ROW_NUMBER() OVER (PARTITION BY tr.region ORDER BY SUM(t.quantity * t.price) DESC) AS rn,
    RANK()       OVER (PARTITION BY tr.region ORDER BY SUM(t.quantity * t.price) DESC) AS rnk,
    DENSE_RANK() OVER (PARTITION BY tr.region ORDER BY SUM(t.quantity * t.price) DESC) AS drnk
FROM traders tr
         JOIN trades t ON tr.trader_id = t.trader_id
WHERE t.status = 'COMPLETED'
GROUP BY tr.region, tr.trader_name;

-- 3. 篩 Top 3 — 包子查詢
SELECT * FROM (
                  SELECT tr.region, tr.trader_name,
                         SUM(t.quantity * t.price) AS total_volume,
                         ROW_NUMBER() OVER (PARTITION BY tr.region ORDER BY SUM(t.quantity * t.price) DESC) AS rn
                  FROM traders tr
                           JOIN trades t ON tr.trader_id = t.trader_id
                  WHERE t.status = 'COMPLETED'
                  GROUP BY tr.region, tr.trader_name
              ) sub
WHERE rn <= 3;

Query 1 跑完你會看到 WEST 的 Alice 跟 Bob 同分 500K：
  ROW_NUMBER: 不同名次 (1, 2)
  RANK:       同名次，跳號 (1, 1, 跳到 3)
  DENSE_RANK: 同名次，不跳號 (1, 1, 接 2)

  Query 2 加了 PARTITION BY region，每個 region 的排名重新從 1 開始。

  Query 3 就是 P7 的正解。

  三個一起跑，一次搞懂。

❯ 所以如果我要選分組之後的排名我就一定要子查詢嗎？

⏺ 對。因為 Window Function 在 WHERE 之後才執行，你沒辦法在同一層 WHERE rn <= 3。

  唯一例外是 MySQL 8.0+ 支援 CTE (Common Table Expression)，跟子查詢做一樣的事，但可讀性更好：

  -- 子查詢寫法
SELECT * FROM (
                  SELECT ..., ROW_NUMBER() OVER (...) AS rn
                  FROM ...
              ) sub
WHERE rn <= 3;

-- CTE 寫法（同樣效果，更好讀）
WITH ranked AS (
    SELECT ..., ROW_NUMBER() OVER (...) AS rn
FROM ...
    )
SELECT * FROM ranked
WHERE rn <= 3;

兩個都是「包一層再篩」，只是語法不同。面試寫哪個都行，CTE 看起來比較 senior。

  去跑那三個 query 吧。

❯ 直接用join算是哪種join為啥我們公司都用left join

⏺ JOIN = INNER JOIN，只回傳兩邊都有匹配的 row。

  traders:  Alice(1), Bob(2), Charlie(3)
  trades:   trader_id=1, trader_id=1, trader_id=2

  INNER JOIN → Alice, Alice, Bob        (Charlie 沒交易，不出現)
  LEFT JOIN  → Alice, Alice, Bob, Charlie(NULL)  (Charlie 出現，trades 欄位填 NULL)

  你們公司用 LEFT JOIN 的原因：業務上通常不想漏掉任何主表的資料。例如「列出所有 trader 的交易」，如果用
  INNER JOIN，沒交易的 trader 直接消失 — 報表上看起來像這個人不存在。LEFT JOIN
  保證主表每筆都在，沒匹配的填 NULL。

  這題用 INNER JOIN 就對了 — 沒交易的 trader 不可能有 volume，不需要出現。

  面試一句話："INNER JOIN when I only need matched rows, LEFT JOIN when I need all rows from the left
  table regardless of match."

  跑完那三個 query 了嗎？

*/