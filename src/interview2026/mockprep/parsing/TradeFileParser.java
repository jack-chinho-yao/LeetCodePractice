package interview2026.mockprep.parsing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

public class TradeFileParser {

    private static final Logger logger = Logger.getLogger(TradeFileParser.class.getName());
    private static final String[] EXPECTED_HEADER = {"trade_id", "symbol", "quantity", "price", "broker_id"};

    static class Trade {
        final String tradeId;
        final String symbol;
        final int quantity;
        final BigDecimal price;
        final String brokerId;

        Trade(String tradeId, String symbol, int quantity, BigDecimal price, String brokerId) {
            this.tradeId = tradeId;
            this.symbol = symbol;
            this.quantity = quantity;
            this.price = price;
            this.brokerId = brokerId;
        }

        @Override
        public String toString() {
            return String.format("Trade{id=%s, symbol=%s, qty=%d, price=%s, broker=%s}",
                    tradeId, symbol, quantity, price.toPlainString(), brokerId);
        }
    }

    /**
     * Parse CSV lines into Trade objects. Skip header, log and skip invalid lines.
     * If header is invalid, log error and return empty list (abort entire file).
     */
    public List<Trade> parseFile(List<String> lines) {
        List<Trade> result = new ArrayList<>();

        if (lines == null || lines.isEmpty()) {
            logger.warning("File is empty or null");
            return result;
        }

        // Row 1: validate header — if wrong, abort entire file
        String[] header = lines.get(0).split(",", -1);
        if (!isValidHeader(header)) {
            logger.severe("Invalid header: " + lines.get(0) + " — aborting file");
            return result;
        }

        // Row 2+: parse each line
        for (int i = 1; i < lines.size(); i++) {
            int lineNum = i + 1;
            String line = lines.get(i);

            String[] fields = line.split(",", -1);

            // Field count check: must be exactly 5
            if (fields.length != EXPECTED_HEADER.length) {
                logger.warning("Line " + lineNum + ": expected 5 fields, got " + fields.length + " — skipping: " + line);
                continue;
            }

            String tradeId = fields[0].trim();
            String symbol = fields[1].trim();
            String quantityStr = fields[2].trim();
            String priceStr = fields[3].trim();
            String brokerId = fields[4].trim();

            // All 5 fields must be non-empty
            if (tradeId.isEmpty() || symbol.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty() || brokerId.isEmpty()) {
                logger.warning("Line " + lineNum + ": empty field(s) — skipping: " + line);
                continue;
            }

            // quantity must be a positive integer
            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                logger.warning("Line " + lineNum + ": quantity is not a valid integer '" + quantityStr + "' — skipping: " + line);
                continue;
            }
            if (quantity <= 0) {
                logger.warning("Line " + lineNum + ": quantity must be positive, got " + quantity + " — skipping: " + line);
                continue;
            }

            // price must be a positive number
            BigDecimal price;
            try {
                price = new BigDecimal(priceStr);
            } catch (NumberFormatException e) {
                logger.warning("Line " + lineNum + ": price is not a valid number '" + priceStr + "' — skipping: " + line);
                continue;
            }
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                logger.warning("Line " + lineNum + ": price must be positive, got " + price + " — skipping: " + line);
                continue;
            }

            result.add(new Trade(tradeId, symbol, quantity, price, brokerId));
        }

        logger.info("Parsed " + result.size() + " valid trades out of " + (lines.size() - 1) + " data lines");
        return result;
    }

    private boolean isValidHeader(String[] header) {
        if (header.length != EXPECTED_HEADER.length) return false;
        for (int i = 0; i < EXPECTED_HEADER.length; i++) {
            if (!EXPECTED_HEADER[i].equals(header[i].trim())) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        TradeFileParser parser = new TradeFileParser();
        List<String> lines = List.of(
                "trade_id,symbol,quantity,price,broker_id",   // header — valid
                "1001,NGX-WCS-JUL26,500,95.50,B001",         // valid
                "1002,NGX-AECO-AUG26,1000,2.10,B002",        // valid
                "BAD_LINE",                                    // wrong field count → skip
                "1003,,200,88.00,B003",                        // empty symbol → skip
                "1004,NGX-MSW-SEP26,-100,75.00,B004",         // negative quantity → skip
                "1005,NGX-WCS-JUL26,300,abc,B005",            // price not a number → skip
                "1006,NGX-WCS-JUL26,300,50.00,B006,EXTRA"     // 6 fields → skip
        );

        List<Trade> trades = parser.parseFile(lines);
        System.out.println("\n=== Valid trades ===");
        trades.forEach(System.out::println);
        // Expected: only 1001 and 1002
    }
}

/*
 * 設計重點（面試口述用）：
 *
 * 1. Header validation — 第一行如果格式不對，整個檔案 abort（不是跳過繼續）
 *    Why: header 錯代表整個檔案格式可能不對，繼續 parse 會產生錯誤資料
 *
 * 2. Fail-safe parsing — invalid line 只 skip + log，不拋 exception 中斷整個 batch
 *    Why: 金融交易檔案可能有幾萬行，不能因為一行壞的就丟掉整批
 *
 * 3. split(",", -1) 的 -1 — 保留 trailing empty strings
 *    "a,b,," → split(",") 得到 ["a","b"]（只有 2 個）
 *    "a,b,," → split(",", -1) 得到 ["a","b","",""]（4 個，正確）
 *    沒有 -1 會漏掉空欄位，field count check 就失效
 *
 * 4. Validation 順序：field count → empty check → type parse → range check
 *    由粗到細，fail-fast
 *
 * 5. Log 包含行號 + 原始內容 + 具體原因 — 方便 debug
 *
 * 6. 生產環境會用 CSV library（如 OpenCSV、Apache Commons CSV）處理：
 *    - 欄位內有逗號（quoted fields）
 *    - 換行符在欄位內
 *    - encoding 問題
 *    面試用 String.split 展示邏輯即可
 *
 * 7. 金額用 BigDecimal — 金融場景必須用 BigDecimal，Double 有浮點精度問題
 *    例如 0.1 + 0.2 = 0.30000000000000004（Double）vs 0.3（BigDecimal）
 *    BigDecimal 比較大小用 compareTo，不能用 > < ==
 */
