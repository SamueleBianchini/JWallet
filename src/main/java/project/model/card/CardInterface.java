package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
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
     * This method is the getter for the field cardNumber. It should not be modified by the extensions of this abstract class.
     * 
     * @return the card number as a String.
     */
    String getCardNumber();

    /**
     * This method is the getter for the field expiryDate. It should not be modified by the extensions of this abstract class.
     * 
     * @return the expiry date as a LocalDate.
     */
    LocalDate getExpiryDate();

    /**
     * This method is the getter for the field cardHolder. It should not be modified by the extensions of this abstract class.
     * 
     * @return the card holder's name as a String.
     */
    String getCardHolder();

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
