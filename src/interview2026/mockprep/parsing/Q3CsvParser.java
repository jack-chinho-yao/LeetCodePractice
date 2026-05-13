package interview2026.mockprep.parsing;

import java.util.*;

/**
 * Q3 - CSV 解析：解析交易紀錄 CSV，計算每個 symbol 的總交易量
 * 格式：tradeID,symbol,quantity,price
 * 需要 try-catch 處理格式錯誤的行
 */
public class Q3CsvParser {

    public static void main(String[] args) {
        String csv =
            "T001,NGX_GAS,100,45.5\n" +
            "T002,NGX_PWR,200,30.0\n" +
            "T003,INVALID_LINE\n" +
            "T004,NGX_GAS,150,46.0\n" +
            "T005,NGX_PWR,50,31.5\n";

        // TODO: 逐行解析 CSV
        // 1. split by "\n" 取得每行
        // 2. 每行 split by "," 取得欄位
        // 3. 如果欄位數不是 4，跳過 (try-catch)
        // 4. 用 HashMap 累加每個 symbol 的 quantity
        Map<String, Integer> symbolQuantity = new HashMap<>();


        // 期望輸出：
        // NGX_GAS: 250
        // NGX_PWR: 250

    }
}
