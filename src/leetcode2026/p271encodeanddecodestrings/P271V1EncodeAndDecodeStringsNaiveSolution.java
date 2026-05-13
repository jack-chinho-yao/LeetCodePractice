package leetcode2026.p271encodeanddecodestrings;

import java.util.ArrayList;
import java.util.List;

public class P271V1EncodeAndDecodeStringsNaiveSolution {

    // "Magic delimiter" approach, Time O(n), Space O(n)
    public String encode(List<String> strs) {
        return null;
    }

    public List<String> decode(String s) {
        return null;
    }

    public static void main(String[] args) {
        P271V1EncodeAndDecodeStringsNaiveSolution solution = new P271V1EncodeAndDecodeStringsNaiveSolution();

        // Example 1: ["Hello","World"]
        List<String> input1 = new ArrayList<>();
        input1.add("Hello");
        input1.add("World");
        runTest(solution, input1);

        // Example 2: [""]
        List<String> input2 = new ArrayList<>();
        input2.add("");
        runTest(solution, input2);

        // Example 3: input contains a '#' — will break naive if you picked '#' as
        // the delimiter; will round-trip fine if you picked something else.
        // Either way, this is the test case that exposes naive's fragility.
        List<String> input3 = new ArrayList<>();
        input3.add("a#4b");
        input3.add("c");
        runTest(solution, input3);
    }

    private static void runTest(P271V1EncodeAndDecodeStringsNaiveSolution solution, List<String> input) {
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
 * Approach / 思路:
 *   Pick a "magic" delimiter character assumed never to appear in any input
 *   string (e.g. a rare non-ASCII char), join the list with it, then split
 *   on that same character to decode.
 *
 *   挑一個「假設絕對不會出現在 input 裡」的特殊字元當分隔符，用它把整個 list
 *   join 起來；decode 時直接 split 回來。
 *
 * Why this approach is fundamentally broken / 為什麼這個解法本質上是壞的:
 *   Real-world input is *arbitrary* — unicode, binary bytes, user-supplied
 *   text, anything. No character is guaranteed to never appear. The moment a
 *   string contains the chosen delimiter, decoding silently returns the wrong
 *   list (no exception, no error — just corrupted data). The only reason this
 *   passes on LeetCode is that the judge restricts the input character set,
 *   which hides the flaw.
 *
 *   現實世界的 input 可以是任意字串——unicode、binary、使用者輸入都可能塞進來。
 *   根本沒有任何字元能保證永遠不出現。一旦 input 裡剛好出現你挑的分隔符，
 *   decode 出來的結果就會默默變錯（不會 throw、不會報錯，就是資料壞了）。
 *   這題在 LeetCode 上能過，是因為題目暗中限制了輸入字元集，把這個漏洞藏起來。
 *
 * The two real-world solutions / 現實世界真正能用的兩條路:
 *   1) Length-prefix (see V2): write "{len}#{str}" for each string. Decode
 *      reads len first, then counts exactly len characters — never scans the
 *      payload for a delimiter, so content can contain anything.
 *      長度前綴（見 V2）：每段寫成 "{長度}#{內容}"。decode 先讀長度，再剛好
 *      數那麼多字元，從不在 payload 中段尋找分隔符，所以內容裡有什麼都不會錯。
 *
 *   2) Escaping: pick a delimiter D and an escape char E. Inside payload,
 *      rewrite D as "ED" and E as "EE". Decode walks character-by-character
 *      and treats E as "the next char is literal".
 *      Examples: CSV doubles `"` to `""`; JSON uses `\"`, `\\`, `\n`; URL
 *      encoding uses `%20` for space.
 *      轉義法：選一個分隔符 D 和轉義字元 E，payload 裡的 D 改寫成 "ED"，E
 *      改寫成 "EE"。decode 時一個字元一個字元掃，遇到 E 就把下一個字當字面值。
 *      例子：CSV 把 `"` 變 `""`、JSON 用 `\"` `\\` `\n`、URL encoding 用 `%20`。
 *
 *   Trade-off / 取捨:
 *     length-prefix → cleanest, O(n), but must know each string's length
 *                     upfront (no true streaming write).
 *                     最乾淨、O(n)，但要先知道長度（不能邊算邊寫出去）。
 *     escaping      → streamable and human-readable, but the escape state
 *                     machine is easy to get wrong.
 *                     可串流、可讀，但 escape 狀態機很容易寫錯。
 *
 * Comparison with Optimized (V2) / 與 V2 比較:
 *   - V1 Naive: relies on a banned-character assumption. Passes LeetCode,
 *     fails in production the first time an input contains the delimiter.
 *     依賴「某字元永遠不出現」的假設，LeetCode 過得了、上 prod 第一次踩到就壞。
 *   - V2 Optimized: length-prefix. Works for any unicode / binary input,
 *     including strings that contain '#' or are empty.
 *     長度前綴，任意 unicode / binary、含 '#' 或空字串都能正確還原。
 *
 * Real-world usage / 真實世界對應:
 *   - The "magic delimiter" pattern only survives in *tightly controlled*
 *     contexts where the input alphabet is restricted by design: ASCII
 *     control chars RS/GS/US (0x1E/0x1D/0x1F) in EDI / fixed-format records,
 *     or `\0` terminators in C strings (which is exactly why C strings can't
 *     hold arbitrary binary data — the same flaw, just standardized).
 *     「假設沒人會用的分隔符」這個 pattern 只能用在**字母表被嚴格限制**的場景：
 *     例如 EDI / 定長記錄裡用 ASCII 控制字元 RS/GS/US (0x1E/0x1D/0x1F)，或
 *     C 語言用 `\0` 結尾（這也正是 C 字串不能存任意 binary 的原因——同一個
 *     漏洞，只是被標準化了）。
 *   - For genuinely arbitrary input (network payloads, user text, file
 *     contents), production systems always use length-prefix or escaping
 *     instead. See V2 notes for the full list of real systems.
 *     只要 input 是真正任意的（網路 payload、使用者文字、檔案內容），實務系統
 *     一律改用 length-prefix 或 escaping，不會賭分隔符。具體系統見 V2 筆記。
 */