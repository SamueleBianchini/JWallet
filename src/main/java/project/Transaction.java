package src.main.java.project;

import java.time.LocalDate;

/**
 * This is a record used to store the data regarding the transactions, which we will need both
 * for memorisation and also for creating graphs.
 * 
 * @param amount the amount of money for the transaction.
 * @param isPositive this boolean is used to represent if the transaction is positive or negative. A positive value means that the
 *      transaction resulted in adding the amount to the balance, a negative one menas that we substracted the
 *      amount from the balance.
 * @param date the date on which the transaction has taken place.
 * @param cause the description of the operation that originated this transaction.
 */
public record Transaction(
    float amount,
    boolean isPositive,
    LocalDate date,
    String cause) {
}
