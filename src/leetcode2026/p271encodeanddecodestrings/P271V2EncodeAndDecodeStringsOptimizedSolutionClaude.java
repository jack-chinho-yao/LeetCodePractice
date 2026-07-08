package leetcode2026.p271encodeanddecodestrings;

import java.util.ArrayList;
import java.util.List;

public class P271V2EncodeAndDecodeStringsOptimizedSolutionClaude {

    // Length-prefix encoding ("len#str"), Time O(n), Space O(n)
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append('#').append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int hashIdx = s.indexOf('#', i);
            int len = Integer.parseInt(s.substring(i, hashIdx));
            int payloadStart = hashIdx + 1;
            int payloadEnd = payloadStart + len;
            result.add(s.substring(payloadStart, payloadEnd));
            i = payloadEnd;
        }
        return result;
    }

    public static void main(String[] args) {
        P271V2EncodeAndDecodeStringsOptimizedSolutionClaude solution =
                new P271V2EncodeAndDecodeStringsOptimizedSolutionClaude();

        runTest(solution, "[1] simple two strings", List.of("Hello", "World"));
        runTest(solution, "[2] '#' inside payload (single digit)", List.of("a#4b", "c"));
        runTest(solution, "[3] single empty string", List.of(""));
        runTest(solution, "[4] empty + normal", List.of("", "ab"));
        runTest(solution, "[5] length 10 (multi-digit)", List.of("0123456789"));
        runTest(solution, "[6] length 13 (multi-digit)", List.of("HelloWorldXYZ"));
        runTest(solution, "[7] killer: digit# inside payload", List.of("4#", "ab"));
        runTest(solution, "[8] killer: leading 'digit#' in payload", List.of("9#hidden"));
        runTest(solution, "[9] mixed lengths", List.of("a", "bb", "ccc", "dddd"));
        runTest(solution, "[10] unicode / newline / tab", List.of("中文", "line1\nline2", "tab\there"));
    }

    private static void runTest(P271V2EncodeAndDecodeStringsOptimizedSolutionClaude solution,
                                String label, List<String> input) {
        System.out.println("=== " + label + " ===");
        System.out.println("input:   " + input + "  (size=" + input.size() + ")");
        String encoded;
        try {
            encoded = solution.encode(input);
        } catch (Exception e) {
            System.out.println("encoded: <encode threw " + e.getClass().getSimpleName() + ": " + e.getMessage() + ">");
            System.out.println();
            return;
        }
        System.out.println("encoded: \"" + encoded + "\"");

        List<String> decoded;
        try {
            decoded = solution.decode(encoded);
        } catch (Exception e) {
            System.out.println("decoded: <decode threw " + e.getClass().getSimpleName() + ": " + e.getMessage() + ">");
            System.out.println("match:   false (exception)");
            System.out.println();
            return;
        }
        System.out.println("decoded: " + decoded + "  (size=" + decoded.size() + ")");
        System.out.println("match:   " + input.equals(decoded));
        System.out.println();
    }
}

/*
 * Approach / 思路:
 *   Length-prefix encoding. encode writes "{len}#{payload}" for every string.
 *   decode keeps a single pointer `i` that always points to the start of the
 *   next length field. Each iteration: find the next '#' from i, parse the
 *   digits between i and '#' as the length L, read exactly L characters after
 *   '#', then advance i past that payload to the next segment boundary.
 *
 *   長度前綴 encoding。encode 每個字串都寫成 "{長度}#{內容}"。decode 用單一
 *   指標 `i`，永遠指向「下一段長度欄位的開頭」。每輪：從 i 往後找 `#`，把
 *   i 到 `#` 之間的數字解析成長度 L，從 `#` 後面剛好讀 L 個字元當 payload，
 *   再把 i 跳到 payload 結尾（也就是下一段的開頭），重複直到字串結束。
 *
 * The single critical insight / 關鍵心智模型:
 *   The decoder must NEVER scan the middle of a payload for '#'. It only
 *   looks for '#' when it has already arrived at a known segment boundary
 *   and is reading the length field. Once the length is known, advancement
 *   happens by counting, not by searching.
 *
 *   decoder **絕對不能** 在 payload 中段去掃描 `#`。只有當指標已經停在「下一段
 *   開頭、正在讀長度數字」的狀態時才會找 `#`。一旦讀到長度，後續就是用計數
 *   往前推進，完全不再尋找分隔符。
 *
 *   Two implicit states / 兩個隱性狀態:
 *     [reading length] --hit '#'--> [reading payload by count L] --done--> [reading length] ...
 *     [讀長度]         --遇到 #--> [按計數讀 L 個字元]           --完成--> [讀長度] ...
 *
 *   This is exactly why payloads can contain '#', "digit#", or any byte:
 *   when the decoder is in "reading payload" state, it is not looking for
 *   any character at all — it is just counting.
 *
 *   這就是為什麼 payload 裡可以有 `#`、`digit#` 或任何 byte 都不會出錯：
 *   decoder 在「讀 payload」狀態時根本沒在「找字元」，只是純粹數數字。
 *
 * Common pitfalls / 常見地雷:
 *   1) Looping `for (i = 0; i < s.length(); i++)` and checking `s.charAt(i) == '#'`
 *      — this scans every position, so payloads containing "digit#" will be
 *      misinterpreted as new segment boundaries.
 *      用 `for` 全掃每個位置然後看 `s.charAt(i) == '#'`，會把 payload 裡的
 *      "digit#" 誤判成新段開頭。要用 `while` + 移動指標。
 *
 *   2) Only reading one digit before '#'. Length can be multi-digit (10, 13,
 *      100, 1234...). Use `Integer.parseInt(s.substring(i, hashIdx))` to
 *      capture all digits at once.
 *      只讀 `#` 前面一個字元當長度。長度可以是多位數（10、13、100、1234...）。
 *      要用 `Integer.parseInt(s.substring(i, hashIdx))` 一次讀完整段數字。
 *
 *   3) Forgetting '0' is a valid length. Empty string "" encodes to "0#",
 *      which must round-trip back to "".
 *      忘記 '0' 也是合法長度。空字串 "" 會 encode 成 "0#"，必須能 round-trip
 *      回到 ""。不要在 encode 裡用 `if (s.isBlank()) continue;` 跳過——
 *      那會把空字串默默丟掉，破壞 round-trip 契約。
 *
 *   4) `List.of("").toString()` prints as "[]", which looks identical to an
 *      empty list. Always check `input.size()` alongside the printed form
 *      when debugging, or your eyes will lie to you.
 *      `List.of("").toString()` 印出來是 "[]"，跟真的空 list 字面上一樣。
 *      debug 時要同時印 `size`，不然眼睛會騙你。
 *
 * Comparison with Naive (V1) / 與 V1 比較:
 *   - V1 Naive: relies on a "magic" delimiter never appearing in input. Passes
 *     LeetCode (judge restricts alphabet), fails on arbitrary input.
 *     V1 Naive 賭某個分隔符永遠不會出現在 input 裡。LeetCode 過得了（題目暗中
 *     限制字元集），現實世界第一次遇到該字元就壞。
 *   - V2 Optimized (this): length-prefix. Works for any unicode / binary input,
 *     including '#' inside payload, "digit#" inside payload, empty strings.
 *     V2 (本檔)：長度前綴。任何 unicode / binary 輸入都能正確 round-trip，
 *     包含 payload 含 `#`、含 "digit#"、空字串。
 *
 * Complexity / 複雜度:
 *   - Time:  O(n) where n is the total byte length of the encoded output.
 *            Each character is touched at most twice (once for indexOf, once
 *            for substring copy).
 *            O(n)，n 為 encoded 字串總長度。每個字元最多被處理兩次。
 *   - Space: O(n) for the result list and the StringBuilder buffer.
 *            O(n)，result list 與 StringBuilder buffer。
 *
 * Real-world usage / 真實世界對應:
 *   - Network protocols: HTTP/2 frames, gRPC, Thrift, protobuf length-delimited
 *     fields, Redis RESP (`$<len>\r\n<bytes>\r\n`), WebSocket frames.
 *     網路協定：HTTP/2 frame、gRPC、Thrift、protobuf length-delimited 欄位、
 *     Redis RESP (`$<len>\r\n<bytes>\r\n`)、WebSocket frame。
 *   - Binary file formats: PNG IDAT/IHDR chunks (4-byte length + 4-byte type
 *     + data + CRC), TAR record headers, MP4 atoms/boxes, WAV/RIFF chunks,
 *     ZIP local file headers.
 *     二進位檔案格式：PNG IDAT/IHDR chunks（4-byte length + 4-byte type +
 *     data + CRC）、TAR 記錄表頭、MP4 atoms/boxes、WAV/RIFF chunks、ZIP
 *     local file header。
 *   - Serialization: Java DataOutputStream.writeUTF (2-byte length prefix),
 *     Kryo, MessagePack, BSON, Avro.
 *     序列化：Java DataOutputStream.writeUTF（2-byte 長度前綴）、Kryo、
 *     MessagePack、BSON、Avro。
 *   - Log shipping / message queues: Kafka records (varint length + payload),
 *     framed protobufs over TCP, syslog octet-counting framing (RFC 6587).
 *     Log 傳輸 / 訊息佇列：Kafka 記錄（varint 長度 + payload）、TCP 上的
 *     framed protobuf、syslog octet-counting framing (RFC 6587)。
 *   - Database storage: variable-length columns in InnoDB row format,
 *     Postgres TOAST, SSTables in RocksDB / Cassandra (key/value sizes
 *     followed by bytes).
 *     資料庫儲存：InnoDB row format 的變長欄位、Postgres TOAST、RocksDB /
 *     Cassandra 的 SSTable（key/value 大小後接 bytes）。
 */
