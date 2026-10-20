package project.model;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import project.model.card.PrepaidCard;
import java.util.Optional;

/**
 * The interface that defines the methods needed for the functioning of all the operations for the BankAccount.
 */
public interface BankAccountInterface {

    /**
     * This method is a getter for the field iban.
     * 
     * @return the iban as a String.
     */
    String getIban();

    /**
     * This method is a getter for the field cardHolder.
     * 
     * @return the cardHolder's name as a String.
     */
    String getCardHolder();

    /**
     * This method is a getter for the field balance.
     * 
     * @return the balance as a BigDecimal.
     */
    BigDecimal getBalance();

    /**
     * This method is used to add an amount to the Bank Account balance, as a result of different types of operations,
     * like cash deposit, wire transfer or others.
     * 
     * @param amount the amount to deposit.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this deposit.
     * @param source the source from which the deposit comes.
     */
    void deposit(BigDecimal amount, LocalDate date, String cause, String source);

    /**
     * This is the specific method used to recharge a Prepaid Card with a specific amount of money.
     * It should lower the balance of the bank account by the specified amount and then call a function
     * of the Prepaid Card to augment its balance.
     * 
     * @param amount the amount we need to recharge.
     * @param destination the PrepaidCard which we want to recharge (we will use this only if we permit the creation of multiple
     *      prepaid cards).
     * @param date the date of the operation.
     */
    void prepaidCharge(BigDecimal amount, PrepaidCard destination, LocalDate date);

    /**
     * This method is used to extract the list of the transactions made with the bank account,
     * while also selecting them based on specific instructions that can be put as parameters
     * to specify the period, the cause, or the amount of the selected operations.
     * 
     * @param start an optional that may contain a date from where to start the selection.
     *      If not present, there will not be a minimum date to consider.
     * @param finish an optional that may contain a date after which the operation is not selected
     *      anymore. If not present, there will not be a maximum date do consider.
     * @param cause an optional that may contain the specific cause for the operations we want
     *      to select. If not present, all possible causes will be considered.
     * @param isPositive an optional that may contain a boolean which, if positive, means
     *      that the operations we need to select are only positive, if false only negative ones,
     *      if not present means both.
     * @param min an optional containing a minimum amount for the operation to be selected.
     *      If not present, no minimum will be considered.
     * @param max an optional containing a maximum amount for the operation to be selected.
     *      If not present, no maximum will be considered.
     * @return a list of the transactions relative to this bank account.
     */
    List<Transaction> viewBankTransactions(
        Optional<LocalDate> start,
        Optional<LocalDate> finish,
        Optional<String> cause,
        Optional<Boolean> isPositive,
        Optional<BigDecimal> min,
        Optional<BigDecimal> max
    );

    /**
     * This method is used for creating and memorising a negative transaction when we wire 
     * transfer money from our bank account to others.
     * 
     * @param amount the amount of the operation.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this wire transfer.
     * @param destination the destination where the money is going to go.
     */
    void wireTransfer(BigDecimal amount, LocalDate date, String cause, String destination);

    /*
    The methods we will use to register the payments for buying and seling assets, I will implement these
    once Enrico does the interface for the assets so I know how to call and implement this function.
    void invest();
    void retireInvestment();
    */
}
