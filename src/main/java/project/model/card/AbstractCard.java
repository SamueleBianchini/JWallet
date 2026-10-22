package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * It selects which ones to show based on specific instructions that can be put as parameters
     * to specify the period, the cause, positive or negative, or the amount of the selected operations.
     * 
     * @param start an optional that may contain a date from where to start the selection.
     *      If not present, there will not be a minimum date to consider.
     * @param finish an optional that may contain a date after which the operation is not selected
     *      anymore. If not present, there will not be a maximum date do consider.
     * @param cause an optional that may contain the specific cause for the operations we want
     *      to select. If not present, all possible causes will be considered.
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
    @Override
    public final List<Transaction> getTransactionHistory(
        final Optional<LocalDate> start,
        final Optional<LocalDate> finish,
        final Optional<String> cause,
        final Optional<Boolean> isPositive,
        final Optional<BigDecimal> min,
        final Optional<BigDecimal> max
    ) {
        /*
            This part checks if the start if after the finish or if the min is higher than the max, in which
            case it throws an IllegalArgumentException which will need to be handled by the controller when it calls this method.
        */
        if (start.isPresent() && finish.isPresent() && start.get().isAfter(finish.get())) {
            throw new IllegalArgumentException("Starting date cannot be after finishing date");
        }
        if (min.isPresent() && max.isPresent() && min.get().compareTo(max.get()) > 0) {
            throw new IllegalArgumentException("Minimum amount cannot be higher than the maximum amount");
        }
        /*
            This part is the output of the method. It uses stream() to filter for every parameter necessary
            and then returns the stream as an unmodifiable list.
        */
        return this.cardTransactions.stream()
            .filter(t -> start.isEmpty() || !t.date().isBefore(start.get()))
            .filter(t -> finish.isEmpty() || !t.date().isAfter(finish.get()))
            .filter(t -> cause.isEmpty() || t.cause().equals(cause.get()))
            .filter(t -> isPositive.isEmpty() || t.isPositive() == isPositive.get())
            .filter(t -> min.isEmpty() || t.amount().compareTo(min.get()) >= 0)
            .filter(t -> max.isEmpty() || t.amount().compareTo(max.get()) <= 0)
            .toList();
    }
}
