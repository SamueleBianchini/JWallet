package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import project.model.Transaction;
import project.utility.InputValidator;

/**
 * Represents a credit card implementation of {@link AbstractCard}.
 * Manages credit limits, interest rates, debt, and monthly settlement.
 */
public final class CreditCard extends AbstractCard {

    private final BigDecimal creditLimit;
    private final BigDecimal interestRate;
    private BigDecimal currentDebt;

    /**
     * Constructs a new CreditCard instance.
     *
     * @param cardNumber          the credit card number
     * @param cardHolder          the card holder's name
     * @param expiryDate          the expiration date
     * @param cvv                 the security code (CVV)
     * @param pin                 the security PIN
     * @param creditLimit         the maximum credit limit
     * @param interestRatePercent the interest rate percentage
     */
    public CreditCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin,
        final BigDecimal creditLimit,
        final BigDecimal interestRatePercent
    ) {
        /* Super validation and constructor */
        super(cardNumber, cardHolder, expiryDate, cvv, pin);
        /* Input validation */
        InputValidator.checkArgument(creditLimit, p -> p.compareTo(BigDecimal.ZERO) > 0, "Credit limit");
        InputValidator.checkArgument(interestRatePercent, p -> p.compareTo(BigDecimal.ZERO) > 0, "Interest rate percent");
        /* Actual constructor */
        this.creditLimit = creditLimit;
        this.interestRate = interestRatePercent;
        this.currentDebt = BigDecimal.ZERO;
    }

    /**
     * Settles the monthly debt by charging the associated bank account.
     *
     * @param date the date of the operation.
     * @param iban the iban of the otherParty for the transaction, in this case the bankAccount.
     * @return true if settlement was successful or no debt existed, false otherwise.
     */
    public boolean endMonth(final LocalDate date, final String iban) {
        /* Input validation */
        InputValidator.checkArgument(date, p -> true, "Date");
        InputValidator.checkArgument(iban, p -> !p.isBlank() && !p.isEmpty(), "Iban");
        /* Method execution */
        final BigDecimal withdrawAmount = currentDebt.add(currentDebt.multiply(interestRate));
        final String cause = "addebito mensile estratto conto carta di credito di " + withdrawAmount;
        super.registerTransaction(new Transaction(currentDebt,
            currentDebt.multiply(interestRate),
            true,
            date,
            cause,
            iban));
        this.currentDebt = BigDecimal.ZERO;
        return true;
    }

    /**
     * Checks whether the card can afford a payment of the specified amount.
     *
     * @param amount the amount to check
     * @return true if the payment can be afforded, false otherwise
     */
    public boolean canAfford(final BigDecimal amount) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> p.compareTo(BigDecimal.ZERO) > 0, "Amount");
        /* Method execution */
        if (isFrozen() || getExpiryDate().isBefore(LocalDate.now())) {
            return false;
        }
        return amount.compareTo(getAvailableCredit()) <= 0;
    }

    private BigDecimal getAvailableCredit() {
        return creditLimit.subtract(currentDebt);
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
        /* Input validation */
        InputValidator.checkArgument(amount, p -> p.compareTo(BigDecimal.ZERO) > 0, "Amount");
        InputValidator.checkArgument(date, p -> true, "Date");
        InputValidator.checkArgument(cause, p -> !p.isBlank() && !p.isEmpty(), "Cause");
        InputValidator.checkArgument(destination, p -> !p.isBlank() && !p.isEmpty(), "Destination");
        /* Method execution */
        if (isFrozen() || amount.compareTo(creditLimit.subtract(currentDebt)) > 0) {
            return false;
        }
        this.currentDebt = this.currentDebt.add(amount);
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
