# LeetCode Practice - Project Conventions

## Directory Structure

```
src/
├── leetcode2026/
│   ├── common/                # Shared data structures (ListNode, TreeNode, etc.)
│   ├── p1twosum/
│   │   ├── P1TwoSumNaiveSolution.java
│   │   └── P1TwoSumOptimizedSolution.java
│   └── p21mergetwosortedlists/
│       ├── P21MergeTwoSortedListsNaiveSolution.java
│       └── P21MergeTwoSortedListsOptimizedSolution.java
```

## Naming Rules

- **Package**: `leetcode2026.p{number}{problemname}` (all lowercase)
- **Class**: `P{Number}V{Version}{ProblemName}{Naive|Optimized}Solution`
- Version number goes right after the problem number: `P739V1`, `P739V2`, etc.
- When creating a new problem, start with V1 (NaiveSolution) and V2 (OptimizedSolution)
- Additional versions (V3, V4...) are added as the solution evolves
- Class names use PascalCase

## Shared Data Structures (`leetcode2026.common`)

- `ListNode` — singly linked list node
- Add more as needed (e.g. `TreeNode`)
- LeetCode already provides these classes, so only the solution method needs to be copied for submission

## When Creating a New Problem

1. Create package `leetcode2026.p{number}{name}`
2. Create both `NaiveSolution` and `OptimizedSolution` files
3. Import shared structures from `leetcode2026.common` if needed
4. Include a `main` method for local testing with the problem's example inputs pre-populated

## Comments Convention

- Keep only a brief one-liner (approach name, Time/Space complexity) above the method
- Put detailed discussion notes (思路、過程示意、比較) at the **bottom** of the file inside a `/* ... */` block, so they don't spoil the solution during review

## File Template

```java
package leetcode2026.p{number}{problemname};

import leetcode2026.common.ListNode; // if needed

public class P{Number}V1{ProblemName}NaiveSolution {

    public ReturnType methodName(params) {
        return null;
    }

    public static void main(String[] args) {
        P{Number}V1{ProblemName}NaiveSolution solution = new P{Number}V1{ProblemName}NaiveSolution();

    }
}
```
