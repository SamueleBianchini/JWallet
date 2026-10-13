package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import project.Transaction;

/**
 * defines the methods that the different type of card will have in common.
 */
public interface CardInterface {

    /**
     * a getter to the implement card number.
     * 
     * @return the card number in the form of String.
     */
    String getCardNumber();

    /**
     * a getter to the implement card number in masked version.
     * 
     * @return the card number in the form of String es. "**** **** **** 4812".
     */
    String getMaskedCardNumber();

    /**
     * a getter to the implement card number.
     * 
     * @return the expiration date as a LocalDate.
     */
    LocalDate getExpiryDate();

    /**
     * a getter to the card Holder's name.
     * 
     * @return the card holder's name in String.
     */
    String getCardHolder();

    /**
     * verify if the card is active or is temporarily frozen.
     * 
     * @return false if active, true if frozen.
     */
    boolean isFrozen();

    /**
     * sets the freeze attribute to true.
     */
    void freeze();

    /**
     * sets the freeze attribute to false.
     */
    void unfreeze();

    /**
     * verify if the pin is correct.
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
     * get the list of past transactions.
     * @return the list of transactions.
     */
    List<Transaction> getTransactionHistory();
}
