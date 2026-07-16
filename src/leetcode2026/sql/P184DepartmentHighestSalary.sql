-- LeetCode 184: Department Highest Salary
-- https://leetcode.com/problems/department-highest-salary/
--
-- Find employees who have the highest salary in each of the departments.
-- 找出每個部門薪資最高的員工（若同部門同薪則並列）。

-- =========================================
-- Schema setup
-- =========================================

DROP TABLE IF EXISTS 184_employee;
DROP TABLE IF EXISTS 184_department;

CREATE TABLE 184_department (
    id   INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE 184_employee (
    id           INT PRIMARY KEY,
    name         VARCHAR(50),
    salary       INT,
    departmentId INT,
    FOREIGN KEY (departmentId) REFERENCES 184_department(id)
);

-- =========================================
-- Test data
-- =========================================

INSERT INTO 184_department (id, name) VALUES
    (1, 'IT'),
    (2, 'Sales');

INSERT INTO 184_employee (id, name, salary, departmentId) VALUES
    (1, 'Joe',   70000, 1),
    (2, 'Jim',   90000, 1),
    (3, 'Henry', 80000, 2),
    (4, 'Sam',   60000, 2),
    (5, 'Max',   90000, 1);

-- =========================================
-- Expected output (LeetCode 標準答案)
-- =========================================
--   +------------+----------+--------+
--   | Department | Employee | Salary |
--   +------------+----------+--------+
--   | IT         | Jim      | 90000  |  ← IT 部門最高 90000，Jim/Max 並列
--   | IT         | Max      | 90000  |
--   | Sales      | Henry    | 80000  |  ← Sales 部門最高 80000
--   +------------+----------+--------+
--
-- 注意：
--   - 每個部門的最高薪可能有並列（tie），要全部列出
--   - 不能只用 ORDER BY + LIMIT，會漏掉 tie
-- =========================================
-- My solution（思路 3: JOIN 子查詢）
-- =========================================
-- Approach: 先用子查詢算出每個 departmentId 的最高薪，再把這個
--           (departmentId, maxSalary) 結果當成一張臨時表，JOIN 回 Employee
--           找出所有薪資等於該部門最高薪的員工（自然處理 tie）。
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM 184_employee e
JOIN 184_department d ON d.id = e.departmentId
JOIN (
    SELECT departmentId, MAX(salary) AS maxSalary
    FROM 184_employee
    GROUP BY departmentId
) t ON t.departmentId = e.departmentId
   AND t.maxSalary    = e.salary
;

-- =========================================
-- 其他解法 / Alternative approaches
-- =========================================
/*
 * 思路 1: tuple IN (最簡潔)
 *   SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
 *   FROM 184_employee e
 *   JOIN 184_department d ON e.departmentId = d.id
 *   WHERE (e.departmentId, e.salary) IN (
 *       SELECT departmentId, MAX(salary)
 *       FROM 184_employee
 *       GROUP BY departmentId
 *   );
 *
 * 思路 2: window function — RANK / DENSE_RANK (最現代)
 *   WITH ranked AS (
 *       SELECT
 *           d.name AS Department,
 *           e.name AS Employee,
 *           e.salary AS Salary,
 *           RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) AS rnk
 *       FROM 184_employee e
 *       JOIN 184_department d ON e.departmentId = d.id
 *   )
 *   SELECT Department, Employee, Salary FROM ranked WHERE rnk = 1;
 *
 * 三種解法比較：
 *   思路 1 (tuple IN)         — 最簡潔，但不是所有 DB 都支援 row-value IN
 *                                (MySQL/PostgreSQL OK；Oracle OK；舊版 SQL Server 不行)
 *   思路 2 (window function)  — 最現代，可讀性最佳。需要 DB 支援 window function
 *                                (MySQL 8+、PostgreSQL、Oracle、SQL Server 2008+)
 *   思路 3 (JOIN 子查詢，本檔)— 相容性最好，連最古老的 DB 都吃。底層運作最透明，
 *                                懂這種寫法等於懂底層發生什麼，再學前兩種更有感。


 SELECT Department, Employee, Salary FROM (
    SELECT d.name AS Department, e.name AS Employee, e.salary, dense_rank() over (PARTITION BY d.name ORDER BY e.salary DESC) AS rn
    FROM Employee e
    JOIN Department d ON d.id = e.departmentId
)t WHERE rn = 1;



 你的寫法完全正確，而且就是思路 2 的變體——LeetCode 上這題這樣寫是滿分解答。幾個點說明一下：

  1. DENSE_RANK vs RANK — 在這題結果一樣

  因為你只取 rn = 1（最頂端那層），兩者在「第一名」完全等價。差別只在遇到 tie 之後：

  salary  RANK  DENSE_RANK
  90000    1        1
  90000    1        1
  80000    3        2      ← 這裡才開始不同（RANK 跳號，DENSE_RANK 不跳）

  既然只篩 rn = 1，用哪個都對。所以你這版和註解裡的思路 2 產出的結果一模一樣。

  因為你只取 rn = 1（最頂端那層），兩者在「第一名」完全等價。差別只在遇到 tie 之後：

  salary  RANK  DENSE_RANK
  90000    1        1
  90000    1        1
  80000    3        2      ← 這裡才開始不同（RANK 跳號，DENSE_RANK 不跳）

  既然只篩 rn = 1，用哪個都對。所以你這版和註解裡的思路 2 產出的結果一模一樣。

  2. 一個小差異：PARTITION BY d.name vs e.departmentId

  - 註解裡思路 2 用的是 e.departmentId（id）
  - 你用的是 d.name（部門名稱）

  99% 情況下結果相同。唯一風險：如果兩個部門 id 不同但名稱剛好一樣，用 name 分組會把它們併在一起算，用 departmentId 才是最嚴謹的。實務上建議
  partition by id。

  3. 表名

  你寫的是 Employee / Department（LeetCode 原始表名），這在本檔要跑本地測試的話要改成 184_employee /
  184_department。不過如果只是貼在註解區當「LeetCode 提交版」，維持原表名是合理的。

  ---
  要不要我把它加進檔案？ 你的 DENSE_RANK 版其實可以當作思路 2 的補充（示範 RANK 和 DENSE_RANK 在這題等價），或獨立成思路
  4。不過原檔已經有三個解法且註解完整，加不加都行——你決定，我可以幫你補上並統一表名。


 */
