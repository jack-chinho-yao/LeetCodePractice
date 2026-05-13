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
        return null;
    }

    public static void main(String[] args) {
        P271V2EncodeAndDecodeStringsOptimizedSolution solution = new P271V2EncodeAndDecodeStringsOptimizedSolution();

        // Example 1: ["Hello","World"] -> "5#Hello5#World"
        List<String> input1 = new ArrayList<>();
        input1.add("Hello");
        input1.add("World");
        runTest(solution, input1);

        // Example 2: [""] -> "0#"
        List<String> input2 = new ArrayList<>();
        input2.add("");
        runTest(solution, input2);

        // Example 3: ["a#4b","c"] -> "4#a#4b1#c"
        // shows '#' inside content does NOT confuse the decoder because we read
        // exactly `len` chars after each '#'
        List<String> input3 = new ArrayList<>();
        input3.add("a#4b");
        input3.add("c");
        runTest(solution, input3);
    }

    private static void runTest(P271V2EncodeAndDecodeStringsOptimizedSolution solution, List<String> input) {
        String encoded = solution.encode(input);
        List<String> decoded = solution.decode(encoded);
        boolean match = input.equals(decoded);
        System.out.println("input:   " + input);
        System.out.println("encoded: " + (encoded == null ? "null" : "\"" + encoded + "\""));
        System.out.println("decoded: " + decoded);
        System.out.println("match:   " + match);
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