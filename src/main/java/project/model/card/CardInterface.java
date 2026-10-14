package project.model.card;

import java.math.BigDecimal;
import java.util.List;

import project.model.Transaction;

/**
 * Defines the methods that the different type of cards will have in common.
 */
public interface CardInterface {

    /**
     * A getter for the card number in masked version.
     * 
     * @return the card number in the form of String es. "**** **** **** 4812".
     */
    String getMaskedCardNumber();

    /**
     * Verify if the card is active or is temporarily frozen.
     * 
     * @return false if active, true if frozen.
     */
    boolean isFrozen();

    /**
     * Sets the freeze attribute to true.
     */
    void freeze();

    /**
     * Sets the freeze attribute to false.
     */
    void unfreeze();

    /**
     * Verify if the pin is correct.
     * 
     * @param pin the pin to verify.
     * @return true if correct, false if incorrect.
     */
    boolean verifyPin(String pin);

    /**
     * Executes a payment of the specified amount.
     * 
     * @param amount the amount to verify.
     * @return true if possible, false if not possible.
     */
    boolean executePayment(BigDecimal amount);

    /**
     * Get the list of past transactions.
     * 
     * @return the list of transactions.
     */
    List<Transaction> getTransactionHistory();
}
