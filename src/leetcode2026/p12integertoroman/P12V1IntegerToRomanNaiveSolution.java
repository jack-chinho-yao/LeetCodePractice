package leetcode2026.p12integertoroman;


public class P12V1IntegerToRomanNaiveSolution {

    public String intToRoman(int num) {
        StringBuilder result = new StringBuilder();
        while(num > 0){
            if(num >= 1000){
                result.append("M");
                num -= 1000;
            }else if ( 1000 > num && num >= 900){
                result.append("CM");
                num -= 900;
            }else if ( num >= 500){
                result.append("D");
                num -= 500;
            }else if ( 500 > num && num >= 400){
                result.append("CD");
                num -= 400;
            }else if (num >= 100){
                result.append("C");
                num -= 100;
            }else if ( 100 > num && num >= 90){
                result.append("XC");
                num -= 90;
            }else if ( num >= 50){
                result.append("L");
                num -= 50;
            }else if ( 50 > num && num >= 40){
                result.append("XL");
                num -= 40;
            }else if (num >= 10){
                result.append("X");
                num -= 10;
            }else if ( num == 9){
                result.append("IX");
                num -= 9;
            }else if ( num >= 5){
                result.append("V");
                num -= 5;
            }else if ( num == 4){
                result.append("IV");
                num -= 4;
            }else if (num >= 1){
                result.append("I");
                num -= 1;
            }else {
                throw new IllegalArgumentException();
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        P12V1IntegerToRomanNaiveSolution solution = new P12V1IntegerToRomanNaiveSolution();
//        System.out.println(solution.intToRoman(3));      // expected: "III"
//        System.out.println(solution.intToRoman(58));     // expected: "LVIII"
//        System.out.println(solution.intToRoman(1994));   // expected: "MCMXCIV"
        System.out.println(solution.intToRoman(3749));   // expected: "MCMXCIV"
    }




    /*
    Example 1:

    Input: num = 3749
    Output: "MMMDCCXLIX"
    Explanation:
    3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
     700 = DCC as 500 (D) + 100 (C) + 100 (C)
      40 = XL as 10 (X) less than 50 (L)
       9 = IX as 1 (I) less than 10 (X)

    Example 2:

    Input: num = 58
    Output: "LVIII"
    Explanation:
    50 = L, 8 = VIII

    Example 3:

    Input: num = 1994
    Output: "MCMXCIV"
    Explanation:
    1000 = M, 900 = CM, 90 = XC, 4 = IV

    Roman numeral symbols:
    Symbol  Value
    I       1
    V       5
    X       10
    L       50
    C       100
    D       500
    M       1000

    Subtractive forms:
    IV = 4,  IX = 9
    XL = 40, XC = 90
    CD = 400, CM = 900

    Constraints:
    - 1 <= num <= 3999
    */
}
