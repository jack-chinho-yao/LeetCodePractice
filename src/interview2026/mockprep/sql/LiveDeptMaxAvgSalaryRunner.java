package interview2026.mockprep.sql;

import java.sql.*;

/**
 * 模擬現場面試題（2026-04-13）：找出平均薪資最高的部門
 *
 * Tables:
 *   department(id, name)
 *   professor(id, department_id, name, salary)
 *
 * 題目：撈出「平均薪資最高」的部門名稱 + 該部門的平均薪資。
 *
 * 練習步驟：
 *   1. 先寫 queryV1_basic 把主幹邏輯跑通
 *   2. 跑 main 看結果，對照檔案最下方的「預期結果」區塊
 *   3. 再寫 queryV2_handleTies 處理同分（這個 dataset 故意設計了 tie）
 *   4. 再寫 queryV3_includeEmptyDept 思考「沒有 professor 的部門」要不要列出
 *   5. 寫完後口頭回答下方 Q1-Q4
 */
public class LiveDeptMaxAvgSalaryRunner {

    private static final String URL = "jdbc:h2:mem:mockprep_live_q1";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            createTables(conn);
            insertTestData(conn);

            System.out.println("=== V1: 基本版（找出平均薪資最高的部門）===");
            runQuery(conn, queryV1_basic());

            System.out.println("\n=== V2: 處理同分（兩個部門平均薪資相同時都要列出）===");
            runQuery(conn, queryV2_handleTies());

            System.out.println("\n=== V3: 包含沒有 professor 的部門（LEFT JOIN 思考）===");
            runQuery(conn, queryV3_includeEmptyDept());
        }
    }

    // ============================================================
    // TODO: 在這裡寫你的 SQL query
    // ============================================================

    /**
     * V1：基本版
     * - 回傳欄位：department_name, avg_salary
     * - 假設沒有同分（先不處理 tie）
     * - 提示：GROUP BY + ORDER BY + LIMIT 1 / FETCH FIRST 1 ROW ONLY
     */
    static String queryV1_basic() {
        return """
            -- TODO: 寫你的 query
            
        
                SELECT d.name as department_name, Round(AVG(salary),2) as avg_salary
                from professor p
                join department d on d.id = p.department_id
                group by  d.name
                order by avg_salary desc
                fetch first 1 row only
                
            """;
    }




// mysql
/*

    SELECT d.name as department_name, AVG(salary) as avg_salary
    from professor p
    join department d on d.id = p.department_id
    group by department_id, d.name
    order by avg_salary desc
    limit 1
*/

//          select * from (
//            select department_name, (sum_salary/sum_professor) as average_salary from
//                (
//                SELECT department_id, d.name as department_name, count(department_id) as sum_professor ,sum(salary) as sum_salary
//                from professor p
//                join department d on d.id = p.department_id
//                group by department_id, d.name
//                ) t
//
//            order by average_salary desc
//             )
//             where rownum = 1;
//             // oracle

    /**
     * V2：處理同分
     * - 如果有兩個部門平均薪資都是最高，兩個都要列出
     * - 不能只用 ORDER BY + LIMIT（會漏掉同分的）
     * - 提示：子查詢找出 max(avg_salary)，或用 RANK() / DENSE_RANK() window function
     */
    static String queryV2_handleTies() {
        return """
            -- TODO: 寫你的 query
                      select name, avg_salary from (
                           SELECT d.name
                                     ,  round(avg(salary), 2) as avg_salary
                                     ,                     rank() over ( order by round(avg(salary), 2)  desc  ) AS rn
                                    from professor p
                                    join department d on d.id = p.department_id
                                    group by d.id, d.name
                                )t WHERE rn = 1;
            
            """;
    }

    /**
     * V3：包含沒有 professor 的部門
     * - History 這個部門沒有任何 professor，avg_salary 應該是 NULL
     * - 問題：這種部門要不要算進「最高平均薪資」的比較？
     * - 寫兩個版本比較：INNER JOIN（排除）vs LEFT JOIN（包含 NULL）
     * - 提示：這題沒有標準答案，看 PM 要怎麼定義。寫一個版本就好，口頭討論取捨。
     */
    static String queryV3_includeEmptyDept() {
        return """
            -- TODO: 寫你的 query
                          SELECT d.name
                                            ,  round(AVG(salary), 2) AS avg_salary
                                            ,                     rank() over ( ORDER BY round(AVG(salary), 2)  DESC  ) AS rn
                                           FROM department d
                                           left JOIN professor p ON d.id = p.department_id
                                           GROUP BY d.name
                                order by rn 
            """;
    }

    // ============================================================
    // 建表 + 假資料（不用動這裡）
    // ============================================================
    /*
    正確應該這樣
    SELECT d.name,
    COALESCE(ROUND(AVG(p.salary), 2), 0) AS avg_salary
    FROM department d
    LEFT JOIN professor p ON p.department_id = d.id
    GROUP BY d.id, d.name
    ORDER BY avg_salary DESC;
    */


    static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE department (
                    id   INT PRIMARY KEY,
                    name VARCHAR(50)
                )
                """);

            stmt.execute("""
                CREATE TABLE professor (
                    id            INT PRIMARY KEY,
                    department_id INT,
                    name          VARCHAR(50),
                    salary        DECIMAL(10,2),
                    FOREIGN KEY (department_id) REFERENCES department(id)
                )
                """);
        }
    }

    static void insertTestData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Departments (5 個 — History 故意沒有 professor)
            stmt.execute("INSERT INTO department VALUES (1, 'Computer Science')");
            stmt.execute("INSERT INTO department VALUES (2, 'Mathematics')");
            stmt.execute("INSERT INTO department VALUES (3, 'Physics')");
            stmt.execute("INSERT INTO department VALUES (4, 'Chemistry')");
            stmt.execute("INSERT INTO department VALUES (5, 'History')");  // 沒有 professor

            // Computer Science: avg = (120 + 140 + 100) / 3 = 120
            stmt.execute("INSERT INTO professor VALUES (101, 1, 'Alice',   120000)");
            stmt.execute("INSERT INTO professor VALUES (102, 1, 'Bob',     140000)");
            stmt.execute("INSERT INTO professor VALUES (103, 1, 'Carol',   100000)");

            // Mathematics: avg = (180 + 160) / 2 = 170
            stmt.execute("INSERT INTO professor VALUES (201, 2, 'Dan',     180000)");
            stmt.execute("INSERT INTO professor VALUES (202, 2, 'Eve',     160000)");

            // Physics: avg = (200 + 180) / 2 = 190  ← TIE 最高
            stmt.execute("INSERT INTO professor VALUES (301, 3, 'Frank',   200000)");
            stmt.execute("INSERT INTO professor VALUES (302, 3, 'Grace',   180000)");

            // Chemistry: avg = (190 + 190) / 2 = 190  ← TIE 最高
            stmt.execute("INSERT INTO professor VALUES (401, 4, 'Henry',   190000)");
            stmt.execute("INSERT INTO professor VALUES (402, 4, 'Ivy',     190000)");

            // History: 沒有任何 professor (測 LEFT JOIN / NULL avg)
        }
        System.out.println("Tables created and test data inserted.\n");
    }

    static void runQuery(Connection conn, String sql) {
        String trimmed = sql.replaceAll("--.*", "").trim();
        if (trimmed.isEmpty() || trimmed.equals("SELECT 1")) {
            System.out.println("  (尚未實作 — 請填入你的 SQL)\n");
            return;
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                System.out.printf("%-25s", meta.getColumnLabel(i));
            }
            System.out.println();
            System.out.println("-".repeat(25 * colCount));
            while (rs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    System.out.printf("%-25s", rs.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("  SQL Error: " + e.getMessage());
        }
        System.out.println();
    }
}

/*
 * ============================================================
 * 預期結果 — 寫完 query 後對照這裡驗證
 * ============================================================
 *
 * 各部門平均薪資：
 *   Computer Science: 120,000
 *   Mathematics:      170,000
 *   Physics:          190,000  ← 同分最高
 *   Chemistry:        190,000  ← 同分最高
 *   History:          NULL  （沒有 professor）
 *
 * V1 基本版 (ORDER BY + LIMIT 1) 預期輸出：
 *   Physics 或 Chemistry 的其中一個（看 DB 引擎的 tie-breaking，不穩定）
 *   → 這就是為什麼 V1 不完美
 *
 * V2 處理同分版 預期輸出：
 *   Physics     190000
 *   Chemistry   190000
 *
 * V3 包含 History 版 預期輸出：
 *   (如果用 LEFT JOIN 且沒過濾 NULL)
 *   History 的 AVG(salary) 會是 NULL，不會參與 MAX 比較
 *   最終結果還是 Physics + Chemistry = 190000
 *
 * ============================================================
 * 面試官可能問的追問 (Q1-Q4)
 * ============================================================
 *
 * Q1 (approach)：
 *   「講一下你的 query 怎麼想的？」
 *   → GROUP BY department → AVG(salary) → 找出最高那個。
 *     同分要用 subquery 比對 max(avg)，不能只用 ORDER BY + LIMIT。
 *
 * Q2 (complexity / 效能)：
 *   「這個 query 在 professor 表有 10M 筆時會怎樣？」
 *   → 全表 scan + GROUP BY 雜湊彙總。O(N) time, O(D) memory (D = dept 數)。
 *     如果 department_id 有 index，join 回 department 那邊會快一點。
 *     真正的 bottleneck 是 full table scan on professor — 沒辦法用 index 避掉 AVG 的全欄掃。
 *     改善：pre-aggregate 成 materialized view / 每天 batch 算好存一張 dept_salary_stats 表。
 *
 * Q3 (edge cases)：
 *   a. 同分：兩個部門平均一樣 → ORDER BY + LIMIT 會漏
 *   b. 空部門：沒 professor 的 department → AVG = NULL，MAX 會自動排除（MAX 忽略 NULL）
 *      但如果業務定義「空部門平均當作 0」，要用 COALESCE(AVG(salary), 0)
 *   c. salary NULL：AVG 會忽略 NULL 值（不是整筆排除）→ 要確認 professor.salary NULL 的語義
 *   d. 整張 professor 表空 → query 不會出錯，會回傳 0 rows（V1）或 NULL（V3 with LEFT JOIN）
 *
 * Q4 (你會怎麼測試)：
 *   測試矩陣：
 *   1. happy path — 明顯最高的部門
 *   2. tie — 兩個部門平均相同
 *   3. empty department — 部門存在但沒 professor
 *   4. single professor dept — 平均就是那一個薪資
 *   5. NULL salary — AVG 自動忽略還是要顯式 filter
 *   6. negative / zero salary — 業務上合理嗎？應該拒絕還是照算
 *
 * ============================================================
 * 通用寫法參考（寫完自己的 query 再看）
 * ============================================================
 *
 * V1 基本版 (ANSI SQL):
 *   SELECT d.name, AVG(p.salary) AS avg_salary
 *   FROM department d
 *   JOIN professor p ON p.department_id = d.id
 *   GROUP BY d.id, d.name
 *   ORDER BY avg_salary DESC
 *   FETCH FIRST 1 ROW ONLY;   -- Oracle / H2 / PostgreSQL
 *   -- LIMIT 1;                -- MySQL
 *
 * V2 處理同分（subquery 版）：
 *   SELECT name, avg_salary FROM (
 *     SELECT d.name, AVG(p.salary) AS avg_salary
 *     FROM department d JOIN professor p ON p.department_id = d.id
 *     GROUP BY d.id, d.name
 *   ) t
 *   WHERE avg_salary = (
 *     SELECT MAX(avg_s) FROM (
 *       SELECT AVG(salary) AS avg_s FROM professor GROUP BY department_id
 *     ) x
 *   );
 *
 * V2 處理同分（CTE + RANK 版，更乾淨）：
 *   WITH dept_avg AS (
 *     SELECT d.name, AVG(p.salary) AS avg_salary,
 *            RANK() OVER (ORDER BY AVG(p.salary) DESC) AS rnk
 *     FROM department d JOIN professor p ON p.department_id = d.id
 *     GROUP BY d.id, d.name
 *   )
 *   SELECT name, avg_salary FROM dept_avg WHERE rnk = 1;
 */