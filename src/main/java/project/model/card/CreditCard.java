package project.model.card;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import project.model.Transaction;
import project.utility.InputValidator;

/**
 * A credit card: you spend now and pay the debt, plus interest, at the end of the month.
 */
public final class CreditCard extends AbstractCard {

    /** Decimals of a sum of money (cents). */
    private static final int CENT_DECIMALS = 2;
    /** Places to move the comma to turn a percentage into a decimal (5 becomes 0.05). */
    private static final int PERCENT_SHIFT = 2;

    private final BigDecimal creditLimit;
    /** The interest rate as a decimal (0.05 is 5%). */
    private final BigDecimal interestRate;
    private BigDecimal currentDebt;

    /**
     * Creates a credit card.
     *
     * @param cardNumber          the card number
     * @param cardHolder          the name of the holder
     * @param expiryDate          the expiry date
     * @param cvv                 the CVV code
     * @param pin                 the PIN
     * @param creditLimit         the most the card can spend
     * @param interestRatePercent the interest rate in percent (5 is 5%)
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
        this.interestRate = interestRatePercent.movePointLeft(PERCENT_SHIFT);
        this.currentDebt = BigDecimal.ZERO;
    }

    /**
     * Gives the debt plus the interest. It does not change the card.
     * Take this amount from the bank account first, then call {@link #endMonth(LocalDate, String)}.
     *
     * @return the amount to pay, or zero if there is no debt
     */
    public BigDecimal getAmountDue() {
        return currentDebt.add(computeInterest());
    }

    /**
     * Closes the month: saves the payment and sets the debt to zero.
     * Call it only after the bank account has been charged. Does nothing if there is no debt.
     *
     * @param date the date of the payment
     * @param iban the IBAN of the bank account that paid
     */
    public void endMonth(final LocalDate date, final String iban) {
        /* Input validation */
        InputValidator.checkArgument(date, p -> true, "Date");
        InputValidator.checkArgument(iban, p -> !p.isBlank() && !p.isEmpty(), "Iban");
        /* Method execution */
        if (currentDebt.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        final String cause = "addebito mensile estratto conto carta di credito di " + getAmountDue();
        super.registerTransaction(new Transaction(currentDebt, computeInterest(), true, date, cause, iban));
        this.currentDebt = BigDecimal.ZERO;
    }

    private BigDecimal computeInterest() {
        return currentDebt.multiply(interestRate).setScale(CENT_DECIMALS, RoundingMode.HALF_UP);
    }

    /**
     * Checks if the card can pay this amount. It cannot if it is frozen, expired or over the limit.
     *
     * @param amount the amount to pay
     * @return true if the card can pay, false otherwise
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
     * Pays with the card: the amount is added to the debt.
     *
     * @param amount the amount to pay
     * @param date the date of the payment
     * @param cause the reason for the payment
     * @param destination who receives the money
     * @return true if the payment was made, false otherwise
     */
    @Override
    public boolean executePayment(final BigDecimal amount, final LocalDate date, final String cause, final String destination) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> p.compareTo(BigDecimal.ZERO) > 0, "Amount");
        InputValidator.checkArgument(date, p -> true, "Date");
        InputValidator.checkArgument(cause, p -> !p.isBlank() && !p.isEmpty(), "Cause");
        InputValidator.checkArgument(destination, p -> !p.isBlank() && !p.isEmpty(), "Destination");
        /* Method execution */
        if (!canAfford(amount)) {
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
