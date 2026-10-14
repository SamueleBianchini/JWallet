package project.model;

import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * This is a record used to store the data regarding the transactions, which we will need both
 * for memorisation and also for creating graphs.
 * 
 * @param amount the amount of money for the transaction.
 * @param fee the fee paid for the specific operation. Can be 0 if no fee was applied, or any specific amount
 *      depending on the operation that resulted in this fee.
 * @param isPositive this boolean is used to represent if the transaction is positive or negative. A positive value means that the
 *      transaction resulted in adding the amount to the balance, a negative one menas that we substracted the
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
}
