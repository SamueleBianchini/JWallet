package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The implementation of the PrepaidCard.
 * It has its own balance, which can only be increased by recharging it from the bank account,
 * and every payment can only be made using the money currently available on the card.
 */
public final class PrepaidCard extends AbstractCard {

    private BigDecimal balance;

    /**
     * This method is the constructor for the Prepaid card.
     * It should call the super constructor of the AbstractInfterface to save the basic card details.
     * The balance of a new prepaid card always starts from zero.
     *
     * @param cardNumber the card number.
     * @param cardHolder the card holder's name.
     * @param expiryDate the expiry date of the card.
     * @param cvv the card's cvv.
     * @param pin the card's pin.
     */
    public PrepaidCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin
        ) {
        super(cardNumber, cardHolder, expiryDate, cvv, pin);
        this.balance = BigDecimal.ZERO;
    }

    /**
     * This method is a getter for the field balance.
     *
     * @return the money currently available on the card as a BigDecimal.
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * This method is used to increase the balance of the card.
     * It is meant to be called by the bank account after it has taken the money from its own balance.
     *
     * @param amount the amount to add to the balance of the card, it must be greater than zero.
     * @return a boolean which is true if the recharge was executed, false if the amount is not valid
     *      or if the card is frozen or expired.
     */
    public boolean recharge(final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !isUsable()) {
            return false;
        }
        this.balance = this.balance.add(amount);
        return true;
    }

    /**
     * This is the implementation of the method from the AbstractCard.
     * It executes the payment taking the money from the balance of the card,
     * only if the card is usable and the balance is sufficient.
     *
     * @return a boolean which is true if the operation was successfull or false otherwise.
     */
    @Override
    public boolean executePayment(final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || !isUsable()) {
            return false;
        }
        if (this.balance.compareTo(amount) < 0) {
            return false;
        }
        this.balance = this.balance.subtract(amount);
        //TODO save the transaction in the card's history, once AbstractCard allows it
        return true;
    }

    /**
     * This method is used to check if the card can be used for an operation,
     * which means that it is not frozen and it is not expired.
     *
     * @return a boolean which is true if the card is usable, false otherwise.
     */
    private boolean isUsable() {
        return !isFrozen() && !getExpiryDate().isBefore(LocalDate.now());
    }
}
