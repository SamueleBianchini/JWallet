package project.utility;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class will be a utility class to get the data useful for the parameters for the tests.
 */
public final class DataUtility {

    public static final String BLANK = "    ";
    public static final String IBAN = "IT00A0000000000000000000000";
    public static final String OTHER_IBAN = "IT00A0000000000000000000000";
    public static final String NAME = "Nathan Drake";
    public static final String CAUSE = "Cruise vacation";
    public static final BigDecimal AMOUNT = new BigDecimal("1000.00");
    public static final LocalDate DATE = LocalDate.of(2026, 1, 1);
    public static final BigDecimal NEG_AMOUNT = new BigDecimal("-1000.00");

    /**
     * This constructor is private because this is a utility class.
     */
    private DataUtility() {

    }
}
