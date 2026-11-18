package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
     * Verify if the cvv is correct.
     * 
     * @param cvv the cvv to verify.
     * @return true if correct, false if incorrect.
     */
    boolean verifyCvv(String cvv);

    /**
     * Executes a payment of the specified amount.
     * 
     * @param amount the amount to verify.
     * @param date the date of the operation.
     * @param cause the cause of the operation.
     * @param destination the destination of the payment.
     * @return true if possible, false if not possible.
     */
    boolean executePayment(BigDecimal amount, LocalDate date, String cause, String destination);

    /**
     * This method is used to register a new transaction.
     * 
     * @param transaction the transaction we need to register.
     */
    void registerTransaction(Transaction transaction);

    /**
     * This method is used to get the list of the transactions.
     * It selects which ones to show based on specific instructions that can be put as parameters
     * to specify the period, the cause, positive or negative, or the amount of the selected operations.
     * 
     * @param start an optional that may contain a date from where to start the selection.
     *      If not present, there will not be a minimum date to consider.
     * @param finish an optional that may contain a date after which the operation is not selected
     *      anymore. If not present, there will not be a maximum date do consider.
     * @param otherParty an optional that may contain the specific other party for the operations we want
     *      to select. If not present, all other parties will be considered.
     * @param isPositive an optional that may contain a boolean which, if positive, means
     *      that the operations we need to select are only positive, if false only negative ones,
     *      if not present means both.
     * @param min an optional containing a minimum amount for the operation to be selected.
     *      If not present, no minimum will be considered.
     * @param max an optional containing a maximum amount for the operation to be selected.
     *      If not present, no maximum will be considered.
     * @return a list of the transactions relative to this bank account.
     * 
     * @see project.model.card.CardInterface#getTransactionHistory()
     */
    List<Transaction> getTransactionHistory(
        Optional<LocalDate> start,
        Optional<LocalDate> finish,
        Optional<String> otherParty,
        Optional<Boolean> isPositive,
        Optional<BigDecimal> min,
        Optional<BigDecimal> max);
}
