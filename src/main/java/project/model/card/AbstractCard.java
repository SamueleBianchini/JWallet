package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import project.model.Transaction;

/**
 * This abstract class implements cardInterface and is used as a template for all other cards that we are going to create.
 * It contains the basic constructor and all the basic methods that will be common for all cards.
 */
public abstract class AbstractCard implements CardInterface {
    private final List<Transaction> cardTransactions;
    private final String cardNumber;
    private final String cardHolder;
    private final LocalDate expiryDate;
    /**
     * The cvv will not have a getter method because it is considered a sensitive information and therefore
     * cannot be exposed for safety reasons.
     */
    @SuppressWarnings("checkstyle:org.danilopianini.gradle-java-qa")
    private final String cvv;
    /**
     * The pin will not have a getter method because it is considered a sensitive information and therefore
     * cannot be exposed for safety reasons.
     */
    @SuppressWarnings("checkstyle:org.danilopianini.gradle-java-qa")
    private final String pin;
    private boolean isFrozen;

    /**
     * This method is the constructor for the basic card.
     * 
     * @param cardNumber the number of the card.
     * @param cardHolder the card's holder's name.
     * @param expiryDate the expiry date of the card.
     * @param cvv the cvv of the card. This field will not have a getter because it is important for security purposes.
     * @param pin the pin of the card. This field will not have a getter because it is important for security purposes.
     */
    public AbstractCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin
    ) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.pin = pin;
        isFrozen = false;
        this.cardTransactions = new ArrayList<>();
    }

    /**
     * This method is the getter for the field cardNumber. It should not be modified by the extensions of this abstract class.
     * 
     * @return the card number as a String.
     */
    @Override
    public final String getCardNumber() {
        return cardNumber;
    }

    /**
     * This method is used to get the card number in its masked version.
     * It should not be modified by the extensions of this abstract class.
     * 
     * @return the card number as a String in its masked version.
     */
    @Override
    public final String getMaskedCardNumber() {
        if (cardNumber != null && cardNumber.length() >= 4) {
            final String prefix = cardNumber.substring(0, cardNumber.length() - 4).replaceAll("[0 - 9]", "*");
            final String suffix = cardNumber.substring(cardNumber.length() - 4);
            return prefix.concat(suffix);
        }
        return "****************";
    }

    /**
     * This method is the getter for the field expiryDate. It should not be modified by the extensions of this abstract class.
     * 
     * @return the expiry date as a LocalDate.
     */
    @Override
    public final LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * This method is the getter for the field cardHolder. It should not be modified by the extensions of this abstract class.
     * 
     * @return the card holder's name as a String.
     */
    @Override
    public final String getCardHolder() {
        return this.cardHolder;
    }

    /**
     * This method is used to check if the card has been frozen.
     * It should not be modified by the extensions of this abstract class.
     * 
     * @return the field isFrozen, which is true if frozen and false otherwise.
     */
    @Override
    public final boolean isFrozen() {
        return isFrozen;
    }

    /**
     * This method is used to freeze the card. It should not be modified by the extensions of this abstract class.
     */
    @Override
    public final void freeze() {
        isFrozen = true;
    }

    /**
     * This method is used to unfreeze the card. It should not be modified by the extensions of this abstract class.
     */
    @Override
    public final void unfreeze() {
        isFrozen = false;
    }

    /**
     * This method is used to verify if the pin passed as a parameter is correct.
     * 
     * @return a boolean which is true if the pin passed as a parameter is equal to the card's one and false otherwise.
     * 
     * @see project.model.card.CardInterface#verifyPin(java.lang.String)
     */
    @Override
    public final boolean verifyPin(final String pinInserted) {
        return pinInserted != null && pinInserted.equals(pin.trim());
    }

    /**
     * This method is used to verify if the cvv passed as a parameter is correct.
     * 
     * @return a boolean which is true if the cvv passed as a parameter is equal to the card's one and false otherwise.
     * 
     * @see project.model.card.CardInterface#verifyCvv(java.lang.String)
     */
    @Override
    public final boolean verifyCvv(final String cvvInserted) {
        return cvvInserted != null && cvvInserted.equals(cvv.trim());
    }

    /**
     * This method is used to execute the payment.
     * This method needs to be implemented differently in every single type of card.
     * 
     * @return a boolean which is true if the operation was successfull and false otherwise.
     * 
     * @see project.model.card.CardInterface#executePayment(java.math.BigDecimal)
     */
    @Override
    public abstract boolean executePayment(BigDecimal amount);

    /**
     * This method is used to get the list of the transactions.
     * It should not be modified by the extensions of this abstract class.
     * 
     * @return the lisgt of all transactions.
     * 
     * @see project.model.card.CardInterface#getTransactionHistory()
     */
    @Override
    public final List<Transaction> getTransactionHistory() {
        return List.copyOf(this.cardTransactions);
    }
}
