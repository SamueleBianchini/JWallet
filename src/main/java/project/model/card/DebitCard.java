package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;

import project.model.Transaction;

/**
 * The implementation of the DebitCard.
 * Every payment is reflected directly on the linked bank account,
 * and every payment can only be made using the money currently available on the bank account.
 */
public final class DebitCard extends AbstractCard {

    /**
     * This method is the constructor for the Debit Card.
     * It should call the super constructor of the AbstractCard to save the basic card details.
     *
     * @param cardNumber the card number.
     * @param cardHolder the card holder's name.
     * @param expiryDate the expiry date of the card.
     * @param cvv the card's cvv.
     * @param pin the card's pin.
     */
    public DebitCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin
    ) {
        super(cardNumber, cardHolder, expiryDate, cvv, pin);
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
        if (super.isFrozen()) {
            return false;
        }
        super.registerTransaction(new Transaction(
                amount,
                BigDecimal.ZERO,
                false,
                date,
                cause,
                destination
            ));
        return true;
    }
}
