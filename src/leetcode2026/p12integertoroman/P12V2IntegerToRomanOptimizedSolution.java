package leetcode2026.p12integertoroman;


public class P12V2IntegerToRomanOptimizedSolution {

    public String intToRoman(int num) {
        int[] number = new int[]{1,4,5,9,10,40,50,90,100,400,500,900,1000};
        String[] strings = new String[]{"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
        StringBuilder sb = new StringBuilder();
        for(int i = number.length - 1; i >=0 ; i--){
            while(num >= number[i]){
                sb.append(strings[i]);
                num -= number[i];
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        P12V2IntegerToRomanOptimizedSolution solution = new P12V2IntegerToRomanOptimizedSolution();
        System.out.println(solution.intToRoman(3));      // expected: "III"
        System.out.println(solution.intToRoman(58));     // expected: "LVIII"
        System.out.println(solution.intToRoman(1994));   // expected: "MCMXCIV"
    }




    /*
    Optimized approach idea:
    - Pre-build a lookup table of value -> symbol pairs sorted from largest to smallest,
      including the six subtractive forms (CM=900, CD=400, XC=90, XL=40, IX=9, IV=4).
    - Greedily subtract the largest value that fits and append its symbol; repeat until num == 0.

    Real-world usage:
    - Number/format encoding: converting an integer into a positional/symbolic representation
      (e.g. base conversion, custom serial number formats, license-plate generators).
    - Greedy "make change" problems: ATM cash dispensers, vending machine coin returns,
      shipping box-size selection — anywhere you repeatedly take the largest available unit
      that fits the remainder.
    - Invoice / chapter / outline numbering systems that require Roman numerals
      (legal documents, book front-matter pages, movie sequels, monarchs/popes).
    */
}
