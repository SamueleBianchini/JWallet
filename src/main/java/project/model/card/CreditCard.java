package project.model.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import project.model.BankAccountImpl;

/**
 * Represents a credit card implementation of {@link AbstractCard}.
 * Manages credit limits, interest rates, debt, and monthly settlement.
 */
public final class CreditCard extends AbstractCard {

    private final BigDecimal creditLimit;
    private final BigDecimal interestRate;
    private final BankAccountImpl conto;
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
     * @param conto               the associated bank account
     */
    public CreditCard(
        final String cardNumber,
        final String cardHolder,
        final LocalDate expiryDate,
        final String cvv,
        final String pin,
        final double creditLimit,
        final double interestRatePercent,
        final BankAccountImpl conto
    ) {
        super(cardNumber, cardHolder, expiryDate, cvv, pin);
        this.creditLimit = BigDecimal.valueOf(creditLimit);
        this.interestRate = BigDecimal.valueOf(interestRatePercent)
                .divide(BigDecimal.valueOf(100));
        this.currentDebt = BigDecimal.ZERO;
        this.conto = conto;
    }

    /**
     * Settles the monthly debt by charging the associated bank account.
     *
     * @return true if settlement was successful or no debt existed, false otherwise
     */
    public boolean endMonth() {
        if (currentDebt.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }

        final BigDecimal withdrawAmount = currentDebt.add(currentDebt.multiply(interestRate));
        final String cause = "addebito mensile estratto conto carta di credito di " + withdrawAmount;

        if (conto.wireTransfer(
                withdrawAmount,
                LocalDate.now(),
                cause,
                this.getMaskedCardNumber()
        )) {
            this.currentDebt = BigDecimal.ZERO;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the card can afford a payment of the specified amount.
     *
     * @param amount the amount to check
     * @return true if the payment can be afforded, false otherwise
     */
    public boolean canAfford(final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (isFrozen() || getExpiryDate().isBefore(LocalDate.now())) {
            return false;
        }
        return amount.compareTo(getAvailableCredit()) <= 0;
    }

    private BigDecimal getAvailableCredit() {
        return creditLimit.subtract(currentDebt);
    }

    /**
     * Executes a payment of the specified amount.
     *
     * @param amount the amount of the payment
     * @return true if the payment was successful, false otherwise
     */
    @Override
    public boolean executePayment(final BigDecimal amount) {
        throw new UnsupportedOperationException("Unimplemented method 'executePayment'");
    }
}
