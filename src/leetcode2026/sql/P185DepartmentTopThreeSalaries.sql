-- LeetCode 185: Department Top Three Salaries
-- https://leetcode.com/problems/department-top-three-salaries/
--
-- A company's executives are interested in seeing who earns the most money in
-- each of the company's departments. A high earner in a department is an
-- employee who has a salary in the top three unique salaries for that department.
-- 找出每個部門「薪資前三高」的員工。這裡的「前三高」指的是該部門
-- 「不重複薪資 (distinct salary)」的前三名，同一名次可以有多人並列。

-- =========================================
-- Schema setup
-- =========================================

DROP TABLE IF EXISTS 185_employee;
DROP TABLE IF EXISTS 185_department;

CREATE TABLE 185_department (
    id   INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE 185_employee (
    id           INT PRIMARY KEY,
    name         VARCHAR(50),
    salary       INT,
    departmentId INT,
    FOREIGN KEY (departmentId) REFERENCES 185_department(id)
);

-- =========================================
-- Test data
-- =========================================

INSERT INTO 185_department (id, name) VALUES
    (1, 'IT'),
    (2, 'Sales');

INSERT INTO 185_employee (id, name, salary, departmentId) VALUES
    (1, 'Joe',   85000, 1),
    (2, 'Henry', 80000, 2),
    (3, 'Sam',   60000, 2),
    (4, 'Max',   90000, 1),
    (5, 'Janet', 69000, 1),
    (6, 'Randy', 85000, 1),
    (7, 'Will',  70000, 1);

-- =========================================
-- Expected output (LeetCode 標準答案)
-- =========================================
--   +------------+----------+--------+
--   | Department | Employee | Salary |
--   +------------+----------+--------+
--   | IT         | Max      | 90000  |  ← IT 第一高 90000
--   | IT         | Joe      | 85000  |  ← IT 第二高 85000（Joe/Randy 並列）
--   | IT         | Randy    | 85000  |
--   | IT         | Will     | 70000  |  ← IT 第三高 70000（69000 落榜）
--   | Sales      | Henry    | 80000  |  ← Sales 第一高 80000
--   | Sales      | Sam      | 60000  |  ← Sales 第二高 60000
--   +------------+----------+--------+
--
-- 注意：
--   - 「前三」是指前三個「不重複」薪資（distinct），不是前三個人
--     → IT 的 distinct 薪資排序: 90000, 85000, 85000... 其實是 {90000, 85000, 70000, 69000}
--       取前三 distinct = {90000, 85000, 70000}，69000 的 Janet 落榜
--   - 同一薪資有並列 (tie) 時全部列出（85000 的 Joe 與 Randy 都算第二高）
--   - 用 DENSE_RANK（不是 RANK / ROW_NUMBER）才能正確處理「distinct 名次」

-- =========================================
-- My solution
-- =========================================
-- TODO: 自己先寫（提示：DENSE_RANK() OVER (PARTITION BY departmentId ORDER BY salary DESC) <= 3）


/*

SELECT Department, Employee, Salary FROM (
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary, dense_rank() over (PARTITION BY departmentId ORDER BY salary DESC ) AS rn
FROM employee e
JOIN department d ON d.id = e.departmentId
) t
WHERE rn <= 3
;

*/