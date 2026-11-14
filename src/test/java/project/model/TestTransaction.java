package project.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import project.utility.DataUtility;

/**
 * This is the test class for the definition of the transctions.
 */
class TestTransaction {

    /**
     * This method is used to test the creation of the transactions.
     */
    @Test
    void testTransaction() {
        /* Testing for null parameters */
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(null, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(DataUtility.AMOUNT, null, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(DataUtility.AMOUNT, BigDecimal.ZERO, true, null, DataUtility.CAUSE, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, null, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, null));
        /* Testing for illegal arguments */
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(
                DataUtility.NEG_AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(
                DataUtility.AMOUNT, DataUtility.NEG_AMOUNT, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(
                DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.BLANK, DataUtility.IBAN));
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(
                DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.BLANK));
    }
}
