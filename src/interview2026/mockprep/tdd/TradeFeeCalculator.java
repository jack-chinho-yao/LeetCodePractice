package interview2026.mockprep.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class TradeFeeCalculator {
    private final String PENDING = "PENDING";
    private final String COMPLETED = "COMPLETED";
    private final String FAILED = "FAILED";
    // "I'm using String constructors for BigDecimal because passing a double like new BigDecimal(0.015) introduces floating-point imprecision."
    private final BigDecimal ZERO = new BigDecimal("0");
    private final BigDecimal TEN = new BigDecimal("10");
    private final BigDecimal ATHOUSAND = new BigDecimal("1000");
    private final BigDecimal TENTHOUSAND = new BigDecimal("10000");

    public BigDecimal getFee(String[] strings) {
        String status = strings[1];
        BigDecimal fee = ZERO;
        // "I'm using equalsIgnoreCase as a defensive measure, since the status might come from an external system where casing isn't guaranteed."
        if (status.equalsIgnoreCase(PENDING)){
            return BigDecimal.ZERO;
        }else if (status.equalsIgnoreCase(FAILED)){
            return BigDecimal.ZERO;
        }else if(status.equalsIgnoreCase(COMPLETED)){
            fee = calculateFee(new BigDecimal(strings[0]));
        }
        return fee;
    }

    private BigDecimal calculateFee(BigDecimal amount) {
        BigDecimal fee = ZERO;
        // "I'm using compareTo instead of < or > because BigDecimal is an object — relational operators don't work on objects in Java."

        if (amount.compareTo(ZERO) == 0){
            fee = ZERO;
        }else if (amount.compareTo(ATHOUSAND) <= 0){
            // "compareTo returns negative/zero/positive, so < 0 means less than."

            fee = amount.multiply(new BigDecimal("0.015")).setScale(2, RoundingMode.HALF_UP);
            // "If the calculated fee is below the minimum, we enforce a floor of $10."
            // "I return a new value instead of mutating the input directly — this avoids side effects and makes the code easier to reason about, especially in a financial system where unexpected mutations can cause serious bugs."
            fee = applyMinimumFee(fee);
        }else if (amount.compareTo(TENTHOUSAND) <= 0){
            fee = amount.multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);
        // "I use explicit else-if with the condition stated, so anyone reading the code knows exactly which tier this is without checking the conditions above."
        } else if (amount.compareTo(TENTHOUSAND) > 0) {
            fee = amount.multiply(new BigDecimal("0.005")).setScale(2, RoundingMode.HALF_UP);
        } else {
            // "The final else acts as a safety net — if we ever reach here, it means the business logic has a gap we didn't account for. Failing fast with an exception is better than silently returning a wrong fee."
            throw new IllegalArgumentException("Unexpected amount: " + amount);
        }
        return fee;
    }

    private BigDecimal applyMinimumFee(BigDecimal fee){
        if (fee.compareTo(TEN) < 0){
            fee = TEN;
        }
        return fee;
    }
}
