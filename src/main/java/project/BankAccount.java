package project;

import java.util.List;
import java.time.LocalDate;

/**
 * The interface that defines the methods needed for the functioning of all the operations for the BankAccount.
 */
public interface BankAccount {

    /**
     * This method is used to add an amount to the Bank Account balance, as a result of different types of operations,
     * like cash deposit, wire transfer or others.
     * 
     * @param amount the amount to deposit.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this deposit.
     * @param source the source from which the deposit comes.
     */
    void deposit(float amount, LocalDate date, String cause, String source);

    /**
     * This is the specific method used to recharge a Prepaid Card with a specific amount of money.
     * It should lower the balance of the bank account by the specified amount and then call a function
     * of the Prepaid Card to augment its balance.
     * 
     * @param amount the amount we need to recharge.
     * @param destination the PrepaidCard which we want to recharge (we will use this only if we permit the creation of multiple
     *      prepaid cards).
     */
    void prepaidCharge(float amount, PrepaidCard destination);

    /**
     * This method is used to extract the list of the transactions made with the bank account,
     * for an external view to process and show on screen.
     * 
     * @return a list of the transactions relative to this bank account.
     */
    List<Transaction> viewBankTransactions();

    /**
     * This method is used for creating and memorising a negative transaction when we wire 
     * transfer money from our bank account to others.
     * 
     * @param amount the amount of the operation.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this wire transfer.
     * @param destination the destination where the money is going to go.
     */
    void wireTransfer(float amount, LocalDate date, String cause, String destination);
}
