package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;

import project.model.BankAccountInterface;

/**
 * The implementation of the DebitCard.
 * Every payment is reflected directly on the linked bank account,
 * and every payment can only be made using the money currently available on the bank account.
 */
public final class DebitCard extends AbstractCard {

    private final BankAccountInterface account;

    /**
     * This method is the constructor for the Debit Card.
     * It should call the super constructor of the AbstractCard to save the basic card details.
     *
     * @param cardNumber the card number.
     * @param cardHolder the card holder's name.
     * @param expiryDate the expiry date of the card.
     * @param cvv the card's cvv.
     * @param pin the card's pin.
     * @param account the bank account linked to the card.
     */
    public DebitCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin,
        final BankAccountInterface account
    ) {
        super(cardNumber, cardHolder, expiryDate, cvv, pin);
        this.account = account;
    }

    /**
     * This is the implementation of the method from the AbstractCard.
     * It executes the payment checking that the card is usable and that the linked bank account has enough money.
     *
     * @return a boolean which is true if the operation was successful or false otherwise.
     */
    @Override
    public boolean executePayment(final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (isFrozen() || getExpiryDate().isBefore(LocalDate.now())) {
            return false;
        }
        //TODO charge the bank account
        return account.preAuthorize(amount);
    }
}
