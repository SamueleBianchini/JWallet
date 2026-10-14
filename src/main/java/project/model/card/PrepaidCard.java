package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The implementation of the PrepaidCard.
 */
public final class PrepaidCard extends AbstractCard {

    /**
     * This method is the constructor for the Prepaid card.
     * It should call the super constructor of the AbstractInfterface to save the basic card details.
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
    }

    /**
     * This is the implementation of the method from the AbstractCard.
     * It executes the payment and saves the resulting operation in the list of transactions.
     * 
     * @return a boolean which is true if the operation was successfull or false otherwise.
     */
    @Override
    public boolean executePayment(final BigDecimal amount) {
        //TODO
        return false;
    }
}

