package project.model;

import java.time.LocalDate;
import java.math.BigDecimal;

import project.utility.InputValidator;

/**
 * This is a record used to store the data regarding the transactions, which we will need both
 * for storage and also for creating graphs.
 * 
 * @param amount the amount of money for the transaction.
 * @param fee the fee paid for the specific operation. Can be 0 if no fee was applied, or any specific amount
 *      depending on the operation that resulted in this fee.
 * @param isPositive this boolean is used to represent if the transaction is positive or negative. A positive value means that the
 *      transaction resulted in adding the amount to the balance, a negative value means that we subtracted the
 *      amount from the balance.
 * @param date the date on which the transaction has taken place.
 * @param cause the description of the operation that originated this transaction.
 * @param otherParty used to define the other party involved in the transaction. Essentially, if the transaction is
 *      a positive one, this will contain the name or IBAN of who is paying, and if the transaction is a negative one,
 *      this will contain the name or IBAN of who is receiving the money.
 */
public record Transaction(
    BigDecimal amount,
    BigDecimal fee,
    boolean isPositive,
    LocalDate date,
    String cause,
    String otherParty) {

        /**
         * This constructor is specified because we need to implement the Input validation process
         * before creating the actual Transaction.
         * 
         * @param amount the amount of money
         * @param fee the fee paid for the specific operation
         * @param isPositive true if the operation is positive, false if it is negative
         * @param date the date of the transaction
         * @param cause the cause of the transaction
         * @param otherParty the other party in the transaction, which is the payer if the transaction is positive
         *      or the receiver if the transaction is negative
         */
        public Transaction {
            InputValidator.checkArgument(amount, p -> p.compareTo(BigDecimal.ZERO) > 0, "Amount");
            InputValidator.checkArgument(fee, p -> p.compareTo(BigDecimal.ZERO) >= 0, "Fee");
            InputValidator.checkArgument(date, p -> true, "Date");
            InputValidator.checkArgument(cause, p -> !p.isBlank(), "Cause");
            InputValidator.checkArgument(otherParty, p -> !p.isBlank(), "Other Party");
        }
}
