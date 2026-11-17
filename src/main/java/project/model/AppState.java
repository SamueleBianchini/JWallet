package project.model;

import java.util.ArrayList;
import java.util.List;
import project.model.card.CreditCard;
import project.model.card.DebitCard;
import project.model.card.PrepaidCard;

/**
 * Object the complete state of the user session for JSON persistence.
 */
public final class AppState {

    private BankAccountImpl bankAccount;
    private DebitCard debitCard;
    private CreditCard creditCard;
    private List<PrepaidCard> prepaidCards;

    /**
     * Default constructor
     */
    public AppState() {
        this.prepaidCards = new ArrayList<>();
    }

    /**
     * Constructs a new AppState
     *
     * @param bankAccount  the user's bank account
     * @param debitCard    the user's main debit card
     * @param creditCard   the user's credit card
     * @param prepaidCards the list of user's prepaid cards
     */
    public AppState(
        final BankAccountImpl bankAccount,
        final DebitCard debitCard,
        final CreditCard creditCard,
        final List<PrepaidCard> prepaidCards
    ) {
        this.bankAccount = bankAccount;
        this.debitCard = debitCard;
        this.creditCard = creditCard;
        this.prepaidCards = prepaidCards != null ? new ArrayList<>(prepaidCards) : new ArrayList<>();
    }

    /**
     * Gets the bank account.
     *
     * @return the bank account
     */
    public BankAccountImpl getBankAccount() {
        return bankAccount;
    }

    /**
     * Sets the bank account.
     *
     * @param bankAccount the bank account to set
     */
    public void setBankAccount(final BankAccountImpl bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * Gets the debit card.
     *
     * @return the debit card
     */
    public DebitCard getDebitCard() {
        return debitCard;
    }

    /**
     * Sets the debit card.
     *
     * @param debitCard the debit card to set
     */
    public void setDebitCard(final DebitCard debitCard) {
        this.debitCard = debitCard;
    }

    /**
     * Gets the credit card.
     *
     * @return the credit card
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the credit card.
     *
     * @param creditCard the credit card to set
     */
    public void setCreditCard(final CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * Gets the list of prepaid cards.
     *
     * @return a list of prepaid cards
     */
    public List<PrepaidCard> getPrepaidCards() {
        return new ArrayList<>(prepaidCards);
    }

    /**
     * Sets the list of prepaid cards.
     *
     * @param prepaidCards the list of prepaid cards to set
     */
    public void setPrepaidCards(final List<PrepaidCard> prepaidCards) {
        this.prepaidCards = prepaidCards != null ? new ArrayList<>(prepaidCards) : new ArrayList<>();
    }
}
