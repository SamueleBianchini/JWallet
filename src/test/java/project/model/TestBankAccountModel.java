package project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import project.utility.DataUtility;

/**
 * This is the test class for the BankAccount implementation.
 */
class TestBankAccountModel {

    /**
     * This method is the test for the constructor of the BankAccount.
     */
    @Test
    void testConstructor() {
        /* Test to see if the Input validation works for null parameters */
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccountImpl(null, DataUtility.NAME));
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccountImpl(DataUtility.IBAN, null));
        /* Test to see if the input validation works for blank strings */
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccountImpl(DataUtility.BLANK, DataUtility.NAME));
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccountImpl(DataUtility.IBAN, DataUtility.BLANK));
        /* Test to assert if the newly created instance is correct */
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        assertNotNull(bankAccount);
        assertEquals(DataUtility.IBAN, bankAccount.getIban());
        assertEquals(DataUtility.NAME, bankAccount.getCardHolder());
        assertEquals(BigDecimal.ZERO, bankAccount.getBalance());
    }

    /**
     * This method is the test for the methods executeTransaction and preAuthorixe of the bankAccount.
     */
    @Test
    void testExecuteTransaction() {
        /* Testing positive transaction */
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final Transaction positiveTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        assertThrows(IllegalArgumentException.class, () -> bankAccount.executeTransaction(null));
        bankAccount.executeTransaction(positiveTransaction);
        assertEquals(bankAccount.getBalance(), DataUtility.AMOUNT);
        /* Testing negative transaction */
        assertThrows(IllegalArgumentException.class, () -> bankAccount.preAuthorize(null));
        final Transaction impossibleTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ONE, false, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        assertThrows(IllegalArgumentException.class, () -> bankAccount.executeTransaction(impossibleTransaction));
        final Transaction negativeTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, false, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        bankAccount.executeTransaction(negativeTransaction);
        assertEquals(bankAccount.getBalance().stripTrailingZeros(), BigDecimal.ZERO);
    }
}
