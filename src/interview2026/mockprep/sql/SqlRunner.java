package interview2026.mockprep.sql;

import java.sql.*;

/**
 * H2 in-memory SQL 練習環境
 * 直接 run main，自動建表 + 塞假資料 + 跑你的 query
 */
public class SqlRunner {

    private static final String URL = "jdbc:h2:mem:ice_practice";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            createTables(conn);
            insertTestData(conn);

            System.out.println("=== Q5: GROUP BY + HAVING ===");
            runQuery(conn, Q5());

            System.out.println("\n=== Q6: LEFT JOIN by Region ===");
            runQuery(conn, Q6());

            System.out.println("\n=== Q7: Window Function - Latest Trade per Trader ===");
            runQuery(conn, Q7());

            System.out.println("\n=== Q8: Oracle-style Top N (H2 用 FETCH NEXT 模擬) ===");
            runQuery(conn, Q8());
        }
    }

    // ============================================================
    // TODO: 在這裡寫你的 SQL query
    // ============================================================

    static String Q5() {
        // 找出總交易金額超過 10,000 的 trader，只看 COMPLETED，按金額由高到低
        return """
            -- TODO: 寫你的 query
            SELECT 1
            """;
    }

    static String Q6() {
        // 每個 region 的總交易金額，包含沒有交易的 region（LEFT JOIN）
        return """
            -- TODO: 寫你的 query
            SELECT 1
            """;
    }

    static String Q7() {
        // 每個 trader 最近一筆交易的 trade_id 和 amount（Window Function）
        return """
            -- TODO: 寫你的 query
            SELECT 1
            """;
    }

    static String Q8() {
        // Q5 的結果只取前 5 名 + 只看今年的交易
        return """
            -- TODO: 寫你的 query
            SELECT 1
            """;
    }

    // ============================================================
    // 建表 + 假資料（不用動這裡）
    // ============================================================

    static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE traders (
                    trader_id   VARCHAR(10) PRIMARY KEY,
                    trader_name VARCHAR(50),
                    region      VARCHAR(30)
                )
                """);

            stmt.execute("""
                CREATE TABLE trades (
                    trade_id   VARCHAR(10) PRIMARY KEY,
                    trader_id  VARCHAR(10),
                    symbol     VARCHAR(20),
                    amount     DECIMAL(12,2),
                    status     VARCHAR(20),
                    trade_date DATE,
                    FOREIGN KEY (trader_id) REFERENCES traders(trader_id)
                )
                """);
        }
    }

    static void insertTestData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Traders - 注意 WEST region 沒有任何交易（Q6 測 LEFT JOIN 用）
            stmt.execute("INSERT INTO traders VALUES ('TR01', 'Alice Chen',   'EAST')");
            stmt.execute("INSERT INTO traders VALUES ('TR02', 'Bob Wang',     'EAST')");
            stmt.execute("INSERT INTO traders VALUES ('TR03', 'Charlie Liu',  'NORTH')");
            stmt.execute("INSERT INTO traders VALUES ('TR04', 'Diana Zhang',  'NORTH')");
            stmt.execute("INSERT INTO traders VALUES ('TR05', 'Edward Wu',    'SOUTH')");
            stmt.execute("INSERT INTO traders VALUES ('TR06', 'Fiona Lee',    'WEST')");  // 沒有交易

            // Trades - 混合各種 status，金額有大有小
            // TR01 Alice: COMPLETED 共 15000 (超過 10000)
            stmt.execute("INSERT INTO trades VALUES ('T001', 'TR01', 'NGX_GAS', 8000.00,  'COMPLETED', '2026-01-15')");
            stmt.execute("INSERT INTO trades VALUES ('T002', 'TR01', 'NGX_PWR', 7000.00,  'COMPLETED', '2026-03-20')");
            stmt.execute("INSERT INTO trades VALUES ('T003', 'TR01', 'NGX_GAS', 3000.00,  'FAILED',    '2026-02-10')");

            // TR02 Bob: COMPLETED 共 12000 (超過 10000)
            stmt.execute("INSERT INTO trades VALUES ('T004', 'TR02', 'NGX_PWR', 5000.00,  'COMPLETED', '2026-01-20')");
            stmt.execute("INSERT INTO trades VALUES ('T005', 'TR02', 'NGX_GAS', 7000.00,  'COMPLETED', '2026-03-25')");
            stmt.execute("INSERT INTO trades VALUES ('T006', 'TR02', 'NGX_PWR', 2000.00,  'PENDING',   '2026-03-28')");

            // TR03 Charlie: COMPLETED 共 6000 (不到 10000)
            stmt.execute("INSERT INTO trades VALUES ('T007', 'TR03', 'NGX_GAS', 3000.00,  'COMPLETED', '2026-02-01')");
            stmt.execute("INSERT INTO trades VALUES ('T008', 'TR03', 'NGX_PWR', 3000.00,  'COMPLETED', '2026-02-15')");

            // TR04 Diana: COMPLETED 共 18000 (最高)
            stmt.execute("INSERT INTO trades VALUES ('T009', 'TR04', 'NGX_GAS', 10000.00, 'COMPLETED', '2026-01-05')");
            stmt.execute("INSERT INTO trades VALUES ('T010', 'TR04', 'NGX_PWR', 8000.00,  'COMPLETED', '2026-03-30')");
            stmt.execute("INSERT INTO trades VALUES ('T011', 'TR04', 'NGX_GAS', 5000.00,  'FAILED',    '2026-03-01')");

            // TR05 Edward: COMPLETED 共 11000 (超過 10000)
            stmt.execute("INSERT INTO trades VALUES ('T012', 'TR05', 'NGX_PWR', 6000.00,  'COMPLETED', '2026-02-20')");
            stmt.execute("INSERT INTO trades VALUES ('T013', 'TR05', 'NGX_GAS', 5000.00,  'COMPLETED', '2026-03-15')");

            // TR06 Fiona: 完全沒有交易（測 LEFT JOIN）

            // 加幾筆去年的交易（測 Q8 日期過濾）
            stmt.execute("INSERT INTO trades VALUES ('T014', 'TR01', 'NGX_GAS', 9000.00,  'COMPLETED', '2025-11-10')");
            stmt.execute("INSERT INTO trades VALUES ('T015', 'TR02', 'NGX_PWR', 8000.00,  'COMPLETED', '2025-12-05')");
        }

        System.out.println("Tables created and test data inserted.\n");
    }

    static void runQuery(Connection conn, String sql) {
        // 跳過空白或只有註解的 query
        String trimmed = sql.replaceAll("--.*", "").trim();
        if (trimmed.isEmpty() || trimmed.equals("SELECT 1")) {
            System.out.println("  (尚未實作 — 請填入你的 SQL)\n");
            return;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            // 印出欄位名稱
            for (int i = 1; i <= colCount; i++) {
                System.out.printf("%-15s", meta.getColumnLabel(i));
            }
            System.out.println();
            System.out.println("-".repeat(15 * colCount));

            // 印出資料
            while (rs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    System.out.printf("%-15s", rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("  SQL Error: " + e.getMessage());
        }
        System.out.println();
    }
}
