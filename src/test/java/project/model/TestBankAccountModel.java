package project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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
        assertThrows(IllegalArgumentException.class, () -> bankAccount.executeTransaction(
            new Transaction(DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.IBAN)
        ));
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

    /**
     * This method is the test for the method viewBankTransactions.
     */
    @Test
    void testTransactionHistory() {
        /* Definition of the bankAccount and the list of transactions */
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final List<Transaction> transactionsList = new ArrayList<>();
        /* Definition of the transactions needed to test all date cases */
        final LocalDate firstDate = LocalDate.of(2025, 11, 11);
        final LocalDate lastDate = LocalDate.of(2026, 3, 3);
        final Transaction middleDateTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(middleDateTransaction);
        final Transaction firstDateTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, firstDate, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(firstDateTransaction);
        final Transaction lastDateTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, lastDate, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(lastDateTransaction);
        /* Definition of the transaction needed to test negative case */
        final Transaction negativeTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, false, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(negativeTransaction);
        /* Definition of the transaction needed to test the otherParty */
        final Transaction otherPartyTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, "IT00A0000000000000000000002"
        );
        transactionsList.add(otherPartyTransaction);
        /* Definition of the transactions needed to test all cases for the amount */
        final BigDecimal firstAmount = new BigDecimal("500.00");
        final BigDecimal lastAmount = new BigDecimal("1500.00");
        final Transaction middleAmountTransaction = new Transaction(
            DataUtility.AMOUNT, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(middleAmountTransaction);
        final Transaction firstAmountTransaction = new Transaction(
            firstAmount, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(firstAmountTransaction);
        final Transaction lastAmountTransaction = new Transaction(
            lastAmount, BigDecimal.ZERO, true, DataUtility.DATE, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        transactionsList.add(lastAmountTransaction);
        /* Execution and recording of all transaction */
        for (final Transaction transaction : transactionsList) {
            bankAccount.executeTransaction(transaction);
        }
        /* Filters testing for empty filters*/
        assertEquals(transactionsList, bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ));
        /* Filters testing for dates*/
        assertEquals(DataUtility.removeElement(transactionsList, firstDateTransaction), bankAccount.viewBankTransactions(
            Optional.of(DataUtility.DATE),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ));
        assertEquals(DataUtility.removeElement(transactionsList, lastDateTransaction), bankAccount.viewBankTransactions(
            Optional.empty(),
            Optional.of(DataUtility.DATE),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.viewBankTransactions(
            Optional.of(lastDate),
            Optional.of(firstDate),
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ));
        /* Filters testing for negative*/
        assertEquals(DataUtility.removeElement(transactionsList, negativeTransaction), bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.of(true),
            Optional.empty(), Optional.empty()
        ));
        /* Filters testing for otherParty*/
        assertEquals(DataUtility.removeElement(transactionsList, otherPartyTransaction), bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(),
            Optional.of(DataUtility.OTHER_IBAN),
            Optional.empty(), Optional.empty(), Optional.empty()
        ));
        /* Filters testing for amount*/
        assertEquals(DataUtility.removeElement(transactionsList, firstAmountTransaction), bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.of(DataUtility.AMOUNT),
            Optional.empty()
        ));
        assertEquals(DataUtility.removeElement(transactionsList, lastAmountTransaction), bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.of(DataUtility.AMOUNT)
        ));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
            Optional.of(lastAmount),
            Optional.of(firstAmount)
        ));
    }
}
