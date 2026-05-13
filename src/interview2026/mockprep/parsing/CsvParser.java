package interview2026.mockprep.parsing;

import java.util.*;

/**
 * Q3 核心邏輯 — CSV 解析，計算每個 symbol 的總交易量
 */
public class CsvParser {

    /** 解析 CSV 字串，回傳每個 symbol 的總 quantity */
    public Map<String, Integer> parseAndSum(String csv) {
        Map<String, Integer> symbolQuantity = new HashMap<>();
        String[] lines = csv.split("\n");
        for (String line : lines) {
            try {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    throw new IllegalArgumentException("Invalid format: " + line);
                }
                String symbol = fields[1];
                int quantity = Integer.parseInt(fields[2]);
                symbolQuantity.put(symbol, quantity + symbolQuantity.getOrDefault(symbol, 0));
            } catch (Exception e) {
                System.out.println("Skipping invalid line: " + line);
            }
        }
        return symbolQuantity;
    }
}
