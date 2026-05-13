package leetcode2026.p12integertoroman;


public class P12V3IntegerToRomanDigitLookupSolution {

    public String intToRoman(int num) {
        String[] s1 = new String[]{"","M", "MM", "MMM"};
        String[] s2 = new String[]{"","C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] s3 = new String[]{"","X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] s4 = new String[]{"","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return s1[num / 1000] + s2[(num/100) % 10] + s3[(num/10) % 10] + s4[num % 10];
    }

    public static void main(String[] args) {
        P12V3IntegerToRomanDigitLookupSolution solution = new P12V3IntegerToRomanDigitLookupSolution();
        System.out.println(solution.intToRoman(3));      // expected: "III"
        System.out.println(solution.intToRoman(58));     // expected: "LVIII"
        System.out.println(solution.intToRoman(1994));   // expected: "MCMXCIV"
        System.out.println(solution.intToRoman(3749));   // expected: "MMMDCCXLIX"
    }




    /*
    Approach: digit-by-digit lookup table — O(1) time / O(1) space.

    Because the problem constrains 1 <= num <= 3999, the integer has at most 4 digits.
    Pre-compute every possible Roman string for each digit-place (thousands, hundreds,
    tens, ones), then concatenate the four lookups. No loop, no branching on values.

    Hint for the four tables (index = digit 0..9):
        thousands: ""  "M"  "MM"  "MMM"                                   (only 0..3 needed)
        hundreds:  ""  "C"  "CC"  "CCC"  "CD"  "D"  "DC"  "DCC" "DCCC" "CM"
        tens:      ""  "X"  "XX"  "XXX"  "XL"  "L"  "LX"  "LXX" "LXXX" "XC"
        ones:      ""  "I"  "II"  "III"  "IV"  "V"  "VI"  "VII" "VIII" "IX"

    Then return:
        thousands[num / 1000]
      + hundreds [(num / 100) % 10]
      + tens     [(num / 10)  % 10]
      + ones     [ num        % 10]

    Comparison vs. V2 (greedy with parallel arrays):
    - V2 has an outer loop over 13 (value, symbol) pairs and an inner while subtracting.
      Worst case ~ a dozen iterations.
    - V3 is pure table lookup — exactly 4 array accesses + string concat. No loops at all.

    Real-world usage:
    - Pre-computed lookup tables for fixed-range encodings: ASCII <-> hex byte tables,
      day-of-week strings, month-name lookups, percent-encoding tables in URL encoders.
    - Format-string fast paths: localized number/date formatters often pre-build tables for
      "00".."99" so they can write two characters with one indexed access instead of dividing.
    - Game / UI level numbering shown in Roman numerals (chapters, achievements, regnal numbers
      like "Louis XIV") — the input domain is small and bounded, perfect for table lookup.
    - Any time the input domain is small and bounded: replacing branches with a table is a
      classic "data over code" optimization (e.g. CRC tables, parser state machines).
    */
}
