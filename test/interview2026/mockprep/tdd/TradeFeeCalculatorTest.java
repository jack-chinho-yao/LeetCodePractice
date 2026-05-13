package interview2026.mockprep.tdd;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TradeFeeCalculatorTest {
//    好題目，跟交易所場景相關的：
//
//    Trade Fee Calculator（交易手續費計算器）
//
//    規則：
//            - amount <= 1,000 → fee = 1.5%
//            - amount 1,001 ~ 10,000 → fee = 1.0%
//            - amount > 10,000 → fee = 0.5%
//            - 最低手續費：$10（算出來不到 $10 就收 $10）
//            - status 不是 "COMPLETED" → fee = 0（不收費）
//
//    範例：
//
//            ┌────────┬───────────┬───────────────────────────────────────────┐
//            │ amount │  status   │                    fee                    │
//            ├────────┼───────────┼───────────────────────────────────────────┤
//            │ 500    │ COMPLETED │ 10.0（500 * 1.5% = 7.5，低於最低，收 10） │
//            ├────────┼───────────┼───────────────────────────────────────────┤
//            │ 5,000  │ COMPLETED │ 50.0（5000 * 1.0%）                       │
//            ├────────┼───────────┼───────────────────────────────────────────┤
//            │ 20,000 │ COMPLETED │ 100.0（20000 * 0.5%）                     │
//            ├────────┼───────────┼───────────────────────────────────────────┤
//            │ 5,000  │ PENDING   │ 0.0                                       │
//            └────────┴───────────┴───────────────────────────────────────────┘
    private final TradeFeeCalculator tradeFeeCalculator = new TradeFeeCalculator();
// "I'm using BigDecimal instead of double because floating-point arithmetic can cause precision issues, which is unacceptable in a financial system."
    @Test
    void testPendingStatus_returnsZeroFee(){
        String[] trade = new String[]{"5000", "PENDING"};
        assertEquals(BigDecimal.ZERO, tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testFailedStatusAmountUnder1000_returnsZeroFee(){
        String[] trade = new String[]{"500", "FAILED"};
        assertEquals(new BigDecimal("0"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountUnder1000_minimumFee(){
        String[] trade = new String[]{"500", "COMPLETED"};
        assertEquals(new BigDecimal("10"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountEqualZero_minimumFee(){
        String[] trade = new String[]{"0", "COMPLETED"};
        assertEquals(new BigDecimal("0"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountEqual1000_1point5Fee(){
        String[] trade = new String[]{"1000", "COMPLETED"};
        assertEquals(new BigDecimal("15.00"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountUnder1000_1point5Fee(){
        String[] trade = new String[]{"800", "COMPLETED"};
        assertEquals(new BigDecimal("12.00"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountBetween1000And10000_1percentFee(){
        String[] trade = new String[]{"1234", "COMPLETED"};
        assertEquals(new BigDecimal("12.34"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountEqual10000_1percentFee(){
        String[] trade = new String[]{"10000", "COMPLETED"};
        assertEquals(new BigDecimal("100.00"), tradeFeeCalculator.getFee(trade));
    }

    @Test
    void testCompletedStatusAmountOver10000_point5percentFee(){
        String[] trade = new String[]{"12345", "COMPLETED"};
        assertEquals(new BigDecimal("61.73"), tradeFeeCalculator.getFee(trade));
    }


}
