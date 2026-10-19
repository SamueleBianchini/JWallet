package project.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;
import project.model.card.PrepaidCard;

/**
 * This class is the implementation of the BankAccountInterface.
 * It will be used to control and manage all the functions necessary for the BankAccount.
 */
public class BankAccountImpl implements BankAccountInterface {

    private final List<Transaction> transactionsList;
    private final String iban;
    private final String cardHolder;
    private BigDecimal balance;

    /**
     * This method is the constructor for the BankAccount implementation.
     * 
     * @param iban the IBAN of the BankAccount.
     * @param cardHolder the name of the card holder.
     */
    public BankAccountImpl(final String iban, final String cardHolder) {
        this.transactionsList = new ArrayList<>();
        this.iban = iban;
        this.cardHolder = cardHolder;
        this.balance = BigDecimal.ZERO;
    }

    /**
     * This method is a getter for the field iban.
     * 
     * @return the iban as a String.
     */
    public String getIban() {
        return this.iban;
    }

    /**
     * This method is a getter for the field cardHolder.
     * 
     * @return the cardHolder's name as a String.
     */
    public String getCardHolder() {
        return this.cardHolder;
    }

    /**
     * This method is used to add an amount to the Bank Account balance, as a result of different types of operations,
     * like cash deposit, wire transfer or others.
     * 
     * @param amount the amount to deposit.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this deposit.
     * @param source the source from which the deposit comes.
     */
    @Override
    public void deposit(final BigDecimal amount, final LocalDate date, final String cause, final String source) {
        this.balance = this.balance.add(amount).subtract(BigDecimal.ONE);
        final Transaction transaction = new Transaction(amount, BigDecimal.ONE, true, date, cause, source);
        this.transactionsList.add(transaction);
    }

    /**
     * This is the specific method used to recharge a Prepaid Card with a specific amount of money.
     * It should lower the balance of the bank account by the specified amount and then call a function
     * of the Prepaid Card to augment its balance.
     * 
     * @param amount the amount we need to recharge.
     * @param destination the PrepaidCard which we want to recharge (we will use this only if we permit the creation of multiple
     *      prepaid cards).
     */
    @Override
    public void prepaidCharge(final BigDecimal amount, final PrepaidCard destination) {

    }

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
     * @param min an optional containing a minimum amount for the operation to be selected.
     *      If not present, no minimum will be considered.
     * @param max an optional containing a maximum amount for the operation to be selected.
     *      If not present, no maximum will be considered.
     * @return a list of the transactions relative to this bank account.
     */
    @Override
    public List<Transaction> viewBankTransactions(
        final Optional<LocalDate> start,
        final Optional<LocalDate> finish,
        final Optional<String> cause,
        final Optional<BigDecimal> min,
        final Optional<BigDecimal> max
    ) {
        return List.of();
    }

    /**
     * This method is used for creating and memorising a negative transaction when we wire 
     * transfer money from our bank account to others.
     * 
     * @param amount the amount of the operation.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this wire transfer.
     * @param destination the destination where the money is going to go.
     */
    @Override
    public void wireTransfer(final BigDecimal amount, final LocalDate date, final String cause, final String destination) {

    }
}
