package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;

import project.model.Transaction;

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
     * @param date the date of the operation.
     * @param iban the iban of the otherParty of the transaction, in this case the bankAccount.
     * @return a boolean which is true if the recharge was executed, false if the amount is not valid
     *      or if the card is frozen or expired.
     */
    public boolean recharge(final BigDecimal amount, final LocalDate date, final String iban) {
        if (super.isFrozen()) {
            return false;
        }
        this.balance = this.balance.add(amount);
        final Transaction transaction = new Transaction(amount,
            BigDecimal.ONE,
            true,
            date,
            "Charging the Prepaid Card",
            iban);
        super.registerTransaction(transaction);
        return true;
    }

    /**
     * This method is used to execute the payment.
     * This method needs to be implemented differently in every single type of card.
     * 
     * @param amount the amount of the operation.
     * @param date the date on which the operation has occurred.
     * @param cause the cause of the operation.
     * @param destination the destination of the operation.
     * @return a boolean which is true if the operation was successfull and false otherwise.
     * 
     * @see project.model.card.CardInterface#executePayment(java.math.BigDecimal)
     */
    @Override
    public boolean executePayment(final BigDecimal amount, final LocalDate date, final String cause, final String destination) {
        if (!super.isFrozen() || amount.compareTo(this.balance) > 0) {
            return false;
        } else {
            this.balance = this.balance.subtract(amount);
            super.registerTransaction(new Transaction(
                amount,
                BigDecimal.ZERO,
                false,
                date,
                cause,
                destination
            ));
        }
        return true;
    }
}
