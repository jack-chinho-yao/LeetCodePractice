-- Sample data for P7 / P9 practice

CREATE TABLE traders (
    trader_id   INT PRIMARY KEY,
    trader_name VARCHAR(100),
    region      VARCHAR(50)
);

CREATE TABLE trades (
    trade_id    INT PRIMARY KEY,
    trader_id   INT NOT NULL,
    symbol      VARCHAR(50),
    quantity    INT,
    price       DECIMAL(12,2),
    status      VARCHAR(20),
    trade_date  DATE
);

-- Traders: 3 regions, 3-4 traders each
INSERT INTO traders VALUES (1, 'Alice',   'WEST');
INSERT INTO traders VALUES (2, 'Bob',     'WEST');
INSERT INTO traders VALUES (3, 'Charlie', 'WEST');
INSERT INTO traders VALUES (4, 'Diana',   'EAST');
INSERT INTO traders VALUES (5, 'Eve',     'EAST');
INSERT INTO traders VALUES (6, 'Frank',   'EAST');
INSERT INTO traders VALUES (7, 'Grace',   'EAST');
INSERT INTO traders VALUES (8, 'Hank',    'CENTRAL');
INSERT INTO traders VALUES (9, 'Ivy',     'CENTRAL');
INSERT INTO traders VALUES (10, 'Jack',   'CENTRAL');

-- Trades: designed to test ROW_NUMBER vs RANK vs DENSE_RANK
-- WEST: Alice 500K, Bob 500K (tie!), Charlie 300K
INSERT INTO trades VALUES (101, 1, 'NGX-WCS',  1000, 200.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (102, 1, 'NGX-AECO', 2000, 150.00, 'COMPLETED', '2026-03-02');
INSERT INTO trades VALUES (103, 2, 'NGX-WCS',  2500, 200.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (104, 3, 'NGX-MSW',  3000, 100.00, 'COMPLETED', '2026-03-03');

-- EAST: Diana 600K, Eve 400K, Frank 400K (tie!), Grace 100K
INSERT INTO trades VALUES (201, 4, 'NGX-WCS',  3000, 200.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (202, 5, 'NGX-AECO', 2000, 200.00, 'COMPLETED', '2026-03-02');
INSERT INTO trades VALUES (203, 6, 'NGX-MSW',  4000, 100.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (204, 7, 'NGX-WCS',   500, 200.00, 'COMPLETED', '2026-03-04');

-- CENTRAL: Hank 800K, Ivy 250K, Jack 150K
INSERT INTO trades VALUES (301, 8, 'NGX-WCS',  4000, 200.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (302, 9, 'NGX-AECO', 1000, 150.00, 'COMPLETED', '2026-03-02');
INSERT INTO trades VALUES (303, 9, 'NGX-MSW',  1000, 100.00, 'COMPLETED', '2026-03-03');
INSERT INTO trades VALUES (304, 10, 'NGX-WCS',  500, 100.00, 'COMPLETED', '2026-03-01');
INSERT INTO trades VALUES (305, 10, 'NGX-AECO', 1000, 100.00, 'COMPLETED', '2026-03-02');

-- Extra trades for P9 testing (PENDING / CANCELLED + small amounts)
INSERT INTO trades VALUES (401, 1, 'NGX-WCS',  100,  50.00, 'PENDING',   '2026-03-05');
INSERT INTO trades VALUES (402, 4, 'NGX-AECO', 2000, 200.00, 'CANCELLED', '2026-03-05');
INSERT INTO trades VALUES (403, 8, 'NGX-MSW',   10,  10.00, 'COMPLETED', '2026-03-06');

-- MySQL auto-commits by default, no need for explicit COMMIT

/*
 * 預期結果 — 用這些來驗證你的 query：
 *
 * ============================================================
 * P7: ROW_NUMBER vs RANK vs DENSE_RANK 比較 (WEST region)
 * ============================================================
 * Alice  total_volume = 1000*200 + 2000*150 = 500,000
 * Bob    total_volume = 2500*200            = 500,000  ← 跟 Alice 同分！
 * Charlie total_volume = 3000*100           = 300,000
 *
 * ROW_NUMBER:  Alice=1, Bob=2, Charlie=3     (同分也不同名次)
 * RANK:        Alice=1, Bob=1, Charlie=3     (同分同名次，跳號)
 * DENSE_RANK:  Alice=1, Bob=1, Charlie=2     (同分同名次，不跳號)
 *
 * ============================================================
 * P7: Top 3 per region (ROW_NUMBER)
 * ============================================================
 * WEST:    Alice 500K (1), Bob 500K (2), Charlie 300K (3)
 * EAST:    Diana 600K (1), Eve 400K (2), Frank 400K (3)
 * CENTRAL: Hank 800K (1), Ivy 250K (2), Jack 150K (3)
 *
 * ============================================================
 * P9: Brokers with >= 2 completed trades where amount > 100K
 * ============================================================
 * trader_id=1 (Alice):  trade 101 = 200K ✓, trade 102 = 300K ✓  → count=2 ✓
 * trader_id=8 (Hank):   trade 301 = 800K ✓, trade 403 = 100 ✗   → count=1 ✗
 * trader_id=9 (Ivy):    trade 302 = 150K ✓, trade 303 = 100K ✗  → count=1 ✗
 * trader_id=10 (Jack):  trade 304 = 50K ✗,  trade 305 = 100K ✗  → count=0 ✗
 * → 只有 Alice (broker_id=1) 符合
 */
