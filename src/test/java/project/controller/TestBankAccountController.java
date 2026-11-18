package project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import project.model.BankAccountImpl;
import project.model.BankAccountInterface;
import project.utility.DataUtility;
import project.model.Transaction;

/**
 * This class is the test class for the bankAccountController.
 */
class TestBankAccountController {

    /**
     * This method is the test for the constructor of the bankAccount.
     */
    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccountControllerImpl(null));
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final BankAccountControllerInterface controller = new BankAccountControllerImpl(bankAccount);
        assertNotNull(controller);
        assertEquals(DataUtility.IBAN, bankAccount.getIban());
        assertEquals(DataUtility.NAME, bankAccount.getCardHolder());
        assertEquals(BigDecimal.ZERO, bankAccount.getBalance());
    }

    /**
     * This method is the test for the Deposit method of the BankAccountController.
     */
    @Test
    void testDeposit() {
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final BankAccountControllerInterface controller = new BankAccountControllerImpl(bankAccount);
        /* Input validation testing */
        assertThrows(IllegalArgumentException.class, () -> controller.deposit(null, DataUtility.DATE_TEXT));
        assertThrows(IllegalArgumentException.class, () -> controller.deposit(
            DataUtility.NEG_AMOUNT.toString(),
            DataUtility.DATE_TEXT));
        assertThrows(IllegalArgumentException.class, () -> controller.deposit(
            BigDecimal.ZERO.toString(),
            DataUtility.DATE_TEXT));
        assertThrows(IllegalArgumentException.class, () -> controller.deposit(
            DataUtility.AMOUNT.toString(), null));
        /* Correct recording and execution testing */
        controller.deposit(
            DataUtility.AMOUNT.toString(),
            DataUtility.DATE_TEXT);
        assertEquals(controller.getBalance(), DataUtility.AMOUNT);
        final Transaction transaction = bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ).getFirst();
        assertEquals(transaction.amount(), DataUtility.AMOUNT);
        assertEquals(transaction.fee(), BigDecimal.ZERO);
        assertEquals(transaction.date(), DataUtility.DATE);
        assertTrue(transaction.isPositive());
        assertEquals(transaction.cause(), "cash deposit");
        assertEquals(transaction.otherParty(), DataUtility.NAME);
    }

    /**
     * This method is the test for the WireTransfer method of the BankAccountController.
     */
    @Test
    void testWireTranser() {
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final BankAccountControllerInterface controller = new BankAccountControllerImpl(bankAccount);
        controller.deposit(
            DataUtility.AMOUNT.add(BigDecimal.ONE).toString(),
            DataUtility.DATE_TEXT);
        /* Input validation testing */
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            null, DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.NEG_AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            BigDecimal.ZERO.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), null, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, null, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.BLANK, DataUtility.OTHER_IBAN
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, null
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.BLANK
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.IBAN
        ));
        /* Correct recording and execution testing */
        controller.wireTransfer(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CAUSE, DataUtility.OTHER_IBAN
        );
        assertEquals(controller.getBalance().stripTrailingZeros(), BigDecimal.ZERO);
        final Transaction transaction = bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ).get(1);
        assertEquals(transaction.amount(), DataUtility.AMOUNT);
        assertEquals(transaction.fee(), BigDecimal.ONE);
        assertEquals(transaction.date(), DataUtility.DATE);
        assertFalse(transaction.isPositive());
        assertEquals(transaction.cause(), DataUtility.CAUSE);
        assertEquals(transaction.otherParty(), DataUtility.OTHER_IBAN);
    }

    /**
     * This method is the test for the PrepaidCharge method of the BankAccountController.
     */
    @Test
    void testPrepaidCharge() {
        final BankAccountInterface bankAccount = new BankAccountImpl(DataUtility.IBAN, DataUtility.NAME);
        final BankAccountControllerInterface controller = new BankAccountControllerImpl(bankAccount);
        controller.deposit(DataUtility.AMOUNT.add(BigDecimal.ONE).toString(), DataUtility.DATE_TEXT);
        /* Input validation testing */
        assertThrows(IllegalArgumentException.class, () -> controller.prepaidCharge(
            null, DataUtility.DATE_TEXT, DataUtility.CARD_NUMBER
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.prepaidCharge(
            DataUtility.NEG_AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CARD_NUMBER
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.prepaidCharge(
            BigDecimal.ZERO.toString(), DataUtility.DATE_TEXT, DataUtility.CARD_NUMBER
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.prepaidCharge(
            DataUtility.AMOUNT.toString(), null, DataUtility.CARD_NUMBER
        ));
        assertThrows(IllegalArgumentException.class, () -> controller.prepaidCharge(
            DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, null
        ));
        /* Correct recording and execution testing */
        controller.prepaidCharge(DataUtility.AMOUNT.toString(), DataUtility.DATE_TEXT, DataUtility.CARD_NUMBER);
        assertEquals(controller.getBalance().stripTrailingZeros(), BigDecimal.ZERO);
        final Transaction transaction = bankAccount.viewBankTransactions(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ).get(1);
        assertEquals(transaction.amount(), DataUtility.AMOUNT);
        assertEquals(transaction.fee(), BigDecimal.ONE);
        assertEquals(transaction.date(), DataUtility.DATE);
        assertFalse(transaction.isPositive());
        assertEquals(transaction.cause(), "Recharging the prepaid card");
        assertEquals(transaction.otherParty(), DataUtility.CARD_NUMBER);
    }
}
