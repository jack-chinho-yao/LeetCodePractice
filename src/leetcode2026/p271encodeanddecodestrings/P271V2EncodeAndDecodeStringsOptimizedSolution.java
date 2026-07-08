package leetcode2026.p271encodeanddecodestrings;

import java.util.ArrayList;
import java.util.List;

public class P271V2EncodeAndDecodeStringsOptimizedSolution {

    // Length-prefix encoding ("len#str"), Time O(n), Space O(n)
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for(String s : strs){
            int length = s.length();
            sb.append(length).append("#").append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String s) {
        List<String> resultList = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            if (s.charAt(i) == '#' && (s.charAt(i-1) == '0' ||
                    s.charAt(i-1) == '1' ||
                    s.charAt(i-1) == '2' ||
                    s.charAt(i-1) == '3' ||
                    s.charAt(i-1) == '4' ||
                    s.charAt(i-1) == '5' ||
                    s.charAt(i-1) == '6' ||
                    s.charAt(i-1) == '7' ||
                    s.charAt(i-1) == '8' ||
                    s.charAt(i-1) == '9'
                    )){
                Integer length = Integer.valueOf(s.charAt(i - 1) - '0');
                String substring = s.substring(i + 1, i + 1 + length);
                resultList.add(substring);
            }
        }
        return resultList;
    }

    public static void main(String[] args) {
        P271V2EncodeAndDecodeStringsOptimizedSolution solution = new P271V2EncodeAndDecodeStringsOptimizedSolution();

        // -------- baseline / 基本 --------
        // 1) simple: ["Hello","World"]  -> "5#Hello5#World"
        runTest(solution, "[1] simple two strings", List.of("Hello", "World"));

        // 2) '#' inside payload, single-digit lengths: works even with naive scan
        //    ["a#4b","c"]  -> "4#a#4b1#c"
        runTest(solution, "[2] '#' inside payload (single digit)", List.of("a#4b", "c"));

        // -------- 你的 decode 目前會掛掉的 cases --------
        // 3) the empty string: ["",]
        //    encoded "0#"  —— 需要把 '0' 也當成 length digit
        runTest(solution, "[3] single empty string", List.of(""));

        // 4) empty string mixed with normal: ["","ab"]
        //    encoded "0#2#ab"  —— 同上，要能識別 length=0
        runTest(solution, "[4] empty + normal", List.of("", "ab"));

        // 5) length >= 10 (multi-digit length): ["0123456789"]
        //    encoded "10#0123456789"  —— 不能只讀 '#' 前面一個 char
        runTest(solution, "[5] length 10 (multi-digit)", List.of("0123456789"));

        // 6) longer multi-digit length: 13 chars
        //    encoded "13#HelloWorldXYZ"  —— 同上
        runTest(solution, "[6] length 13 (multi-digit)", List.of("HelloWorldXYZ"));

        // 7) THE KILLER: payload contains "digit#" pattern that is NOT a real boundary
        //    ["4#","ab"] -> encoded "2#4#2#ab"
        //    若 decode 用「全字串找 #」會把 payload 裡的 "4#" 誤判成新的長度標記。
        //    這題完全打中我們之前討論的「不能在 payload 中段掃 #」的陷阱。
        runTest(solution, "[7] killer: digit# inside payload", List.of("4#", "ab"));

        // 8) another killer variant: ["9#hidden"]
        //    encoded "8#9#hidden"  —— 誤判第二個 '#' 會 substring 越界 crash
        runTest(solution, "[8] killer: leading 'digit#' in payload", List.of("9#hidden"));

        // -------- 進階 / advanced --------
        // 9) multiple strings, mix of lengths
        runTest(solution, "[9] mixed lengths", List.of("a", "bb", "ccc", "dddd"));

        // 10) special chars (unicode, newline, tab) — length-prefix should not care
        runTest(solution, "[10] unicode / newline / tab", List.of("中文", "line1\nline2", "tab\there"));
    }

    private static void runTest(P271V2EncodeAndDecodeStringsOptimizedSolution solution,
                                String label, List<String> input) {
        System.out.println("=== " + label + " ===");
        System.out.println("input:   " + input);
        String encoded;
        try {
            encoded = solution.encode(input);
        } catch (Exception e) {
            System.out.println("encoded: <encode threw " + e.getClass().getSimpleName() + ": " + e.getMessage() + ">");
            System.out.println();
            return;
        }
        System.out.println("encoded: " + (encoded == null ? "null" : "\"" + encoded + "\""));
        if (encoded == null) {
            System.out.println("decoded: <skipped, encode returned null>");
            System.out.println();
            return;
        }

        List<String> decoded;
        try {
            decoded = solution.decode(encoded);
        } catch (Exception e) {
            System.out.println("decoded: <decode threw " + e.getClass().getSimpleName() + ": " + e.getMessage() + ">");
            System.out.println("match:   false (exception)");
            System.out.println();
            return;
        }
        System.out.println("decoded: " + decoded);
        System.out.println("match:   " + input.equals(decoded));
        System.out.println();
    }
}

/*
 * Approach:
 *   Length-prefix encoding. For each string s in the input list, append
 *   "{s.length()}#{s}" to the encoded buffer.
 *   To decode: scan for the next '#', parse the integer before it as the
 *   length L, then read exactly the next L characters as the original string.
 *   Advance the pointer and repeat.
 *
 *     ["Hello","World"]   -> "5#Hello5#World"
 *     [""]                -> "0#"
 *     ["a#4b","c"]        -> "4#a#4b1#c"
 *
 * Key learnings:
 *   - Length prefix makes the decoder content-agnostic: no character in the
 *     payload can be misinterpreted as a delimiter, because we read by count
 *     rather than scanning for a sentinel.
 *   - The '#' is just a separator between the length digits and the payload —
 *     it has no special meaning inside the payload itself.
 *   - Use StringBuilder for encoding to avoid quadratic string concatenation.
 *   - Two indices in decode: one to find the '#' (i), one to track the
 *     start of the next record (j = i + 1 + len).
 *
 * Comparison with Naive:
 *   - Naive: relies on a "magic" delimiter never appearing in input -> fragile.
 *   - Optimized: works for any unicode input, empty strings, and strings that
 *     contain the '#' character itself.
 *
 * Real-world usage:
 *   - Network protocols: length-prefixed framing in HTTP/2, gRPC, Thrift,
 *     protobuf (varint length-delimited fields), Redis RESP (`$<len>\r\n...`).
 *   - Binary file formats: TAR record headers, PNG IDAT/IHDR chunks (4-byte
 *     length + 4-byte type + data), MP4 atoms / boxes, WAV/RIFF chunks.
 *   - Serialization: Java DataOutputStream.writeUTF writes 2-byte length
 *     prefix; Kryo, MessagePack, BSON all use length-prefix variants.
 *   - Log shipping / message queues: Kafka records, framed protobufs over
 *     TCP, structured logs where payloads may contain newlines/commas.
 *   - Database storage: variable-length columns in InnoDB / Postgres TOAST,
 *     SSTables (RocksDB, Cassandra) store key/value sizes followed by bytes.
 */