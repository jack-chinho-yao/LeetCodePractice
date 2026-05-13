# 模擬二面 — Pair Coding 練習筆記

> 對象：交易所 / 金融科技公司 Java Backend Developer
> 形式：3 小時 onsite live pair coding
> 領域情境：能源交易結算所（energy trading clearinghouse）— 所有題目用 trade / settlement / clearing 包裝

---

## 練習執行規則（給 Claude 跑這個 session 用）

1. **由 warm-up Java Collection 題開始（easy）**
2. **進階到中等難度的資料處理題**，模擬交易所會用到的金融交易資料情境
3. **包含 2–3 題 SQL（Oracle 語法）**
4. **卡關時只給 hint，不要直接給完整答案**
5. **每題解完後請我口述思路**（模擬 pair coding 的溝通環節）
6. **點出我可能漏掉的 edge cases**
7. **每題解完後問我：「How would you write a JUnit test for this?」**
   — 我必須口頭列出**至少 3 個 edge cases**（模擬 TDD 思維）
8. **遇到 batch processing 或大資料量題目時，問我：**
   「如果這題要在 1 小時內處理 4000 萬筆，你會怎麼改？」
   — 訓練 SLA / scalability 思考

---

## 練習課表（3 小時版）

> **注意**：以下 9 題都是「**全新**」題目，跟 `collections/` 裡已有的 `Q1HashMapTraderSum.java` / `Q4HashSetDeduplicate.java` 等**沒對應關係**。每題會建立一個全新的 class file。

| #  | 題目                                          | 檔案路徑                                                                     | 難度        | 主題                          |
|----|-----------------------------------------------|------------------------------------------------------------------------------|-------------|-------------------------------|
| 1  | LinkedHashMap — 每位 trader 最近 N 筆交易     | `src/interview2026/mockprep/collections/RecentTradesTracker.java`            | Easy        | Collections                   |
| 2  | Group Trades by Symbol                        | `src/interview2026/mockprep/collections/TradeSymbolGrouper.java`             | Easy        | HashMap + ArrayList           |
| 3  | Top-N Traders by Volume                       | `src/interview2026/mockprep/collections/TopTradersByVolume.java`             | Medium      | Sorting + Streams             |
| 4  | Trade File Parser with Validation             | `src/interview2026/mockprep/parsing/TradeFileParser.java`                    | Medium      | Parsing + Exception handling  |
| 5  | LRU Cache for Market Data Quotes              | `src/interview2026/mockprep/collections/MarketDataLruCache.java`             | Medium-Hard | LinkedHashMap / DLL+HashMap   |
| 6  | Trade Reconciliation（兩來源比對）            | `src/interview2026/mockprep/collections/TradeReconciler.java`                | Medium      | HashMap + Set 操作            |
| 7  | Oracle SQL — 每個 region 前 3 名 trader       | `src/interview2026/mockprep/sql/Q7TopTradersPerRegion.sql`                   | Medium      | Window functions              |
| 8  | Oracle SQL — 每日累計結算總額                 | `src/interview2026/mockprep/sql/Q8DailyRunningSettlement.sql`                | Medium      | Window functions              |
| 9  | Oracle SQL — 至少 2 筆大額完成單的 broker     | `src/interview2026/mockprep/sql/Q9BrokersWithLargeTrades.sql`                | Easy        | GROUP BY + HAVING             |

**已練過、不重複**（不要動這些）：`Q1HashMapTraderSum` / `Q2StreamsFilterSum` / `Q3CsvParser` / `Q4HashSetDeduplicate` / `TradeFeeCalculator` / `RemovableIndices`

---

## 重點 Focus Areas

- **Java Collections**：HashMap, ArrayList, HashSet, LinkedHashMap
- **Algorithms with collections**：filter, group, aggregate
- **String parsing & manipulation**
- **Exception handling & input validation**
- **SQL**：JOIN, GROUP BY, HAVING, Window Functions（**Oracle 語法**）
- **Spring Core**：IoC, DI, Spring MVC
- **OOP**：Encapsulation, Inheritance, Polymorphism, Abstraction
- **LRU Cache** 實作概念
