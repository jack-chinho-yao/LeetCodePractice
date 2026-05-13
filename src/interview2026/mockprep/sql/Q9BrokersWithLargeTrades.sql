-- P9: Brokers with at least 2 completed trades where per-trade amount > $100,000
-- Difficulty: Easy | Topic: GROUP BY + HAVING

SELECT broker_id, COUNT(trade_id) AS trades
FROM trades
WHERE status = 'COMPLETED'
  AND (quantity * price) > 100000
GROUP BY broker_id
HAVING COUNT(trade_id) >= 2
ORDER BY trades DESC;

/*
 * SQL 執行順序（面試必背）:
 * FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY
 *
 * WHERE vs HAVING:
 * - WHERE:  GROUP BY 之前，篩單筆 row（不能用聚合函數）
 * - HAVING: GROUP BY 之後，篩分組結果（可以用聚合函數）
 *
 * 常見錯誤:
 * 1. GROUP BY trade_id → 每組只有 1 筆（PK），HAVING count >= 2 永遠不成立
 *    應該 GROUP BY broker_id（按 broker 分組）
 * 2. 把 (quantity * price) > 100000 放在 HAVING → 這是單筆條件，應放 WHERE
 * 3. 忘了 HAVING → 沒篩「至少 2 筆」的條件
 */
