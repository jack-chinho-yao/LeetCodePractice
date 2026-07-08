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
 */
