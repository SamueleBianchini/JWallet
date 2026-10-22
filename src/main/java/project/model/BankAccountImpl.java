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
    @Override
    public String getIban() {
        return this.iban;
    }

    /**
     * This method is a getter for the field cardHolder.
     * 
     * @return the cardHolder's name as a String.
     */
    @Override
    public String getCardHolder() {
        return this.cardHolder;
    }

    /**
     * This method is a getter for the field balance.
     * 
     * @return the balance as a BigDecimal.
     */
    @Override
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * This method is used to pre authorize every payment before executing it.
     * Practically, every type of payment will have to call this method to verify if
     * the current balance is sufficient to pay the specified amount.
     * 
     * @param amount the amount we need to verify.
     * @return a boolean which is true if there is enough balance to cover the amount,
     *      false otherwise.
     */
    @Override
    public boolean preAuthorize(final BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }

    /**
     * This method is used to add an amount to the Bank Account balance, as a result of different types of operations,
     * like cash deposit, wire transfer or others.
     * This type of operation will not have a fee because normally the fee is paid by who pays for the operation and
     * not by who receives the money.
     * 
     * @param amount the amount to deposit.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this deposit.
     * @param source the source from which the deposit comes.
     */
    @Override
    public void deposit(final BigDecimal amount, final LocalDate date, final String cause, final String source) {
        this.balance = this.balance.add(amount);
        final Transaction transaction = new Transaction(amount, BigDecimal.ZERO, true, date, cause, source);
        this.transactionsList.add(transaction);
    }

    /**
     * This is the specific method used to recharge a Prepaid Card with a specific amount of money.
     * It should lower the balance of the bank account by the specified amount and then call a function
     * of the Prepaid Card to augment its balance.
     * This type of operation will for now have a fee assigned to 1 euro.
     * 
     * @param amount the amount we need to recharge.
     * @param destination the PrepaidCard which we want to recharge (we will use this only if we permit the creation of multiple
     *      prepaid cards).
     * @param date the date of the operation.
     * @return a boolean which is true if the operation was possible and executed succesfully,
     *      false otherwise.
     */
    @Override
    public boolean prepaidCharge(final BigDecimal amount, final PrepaidCard destination, final LocalDate date) {
        if (preAuthorize(amount.add(BigDecimal.ONE))) {
            this.balance = this.balance.subtract(amount.add(BigDecimal.ONE));
            final Transaction transaction = new Transaction(
                amount,
                BigDecimal.ONE,
                false,
                date,
                "Recharging prepaid card",
                destination.getCardNumber());
            this.transactionsList.add(transaction);
            return true;
            } else {
                return false;
            }
        /*
            This will be uncommented once the method for the prepaidcard is setup.

            destination.recharge(amount, date);
         */
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
        final Optional<Boolean> isPositive,
        final Optional<BigDecimal> min,
        final Optional<BigDecimal> max
    ) {
        /*
            This part checks if the start if after the finish or if the min is higher than the max, in which
            case it throws an IllegalArgumentException which will need to be handled by the controller when it calls this method.
        */
        if (start.isPresent() && finish.isPresent() && start.get().isAfter(finish.get())) {
            throw new IllegalArgumentException("Starting date cannot be after finishing date");
        }
        if (min.isPresent() && max.isPresent() && min.get().compareTo(max.get()) > 0) {
            throw new IllegalArgumentException("Minimum amount cannot be higher than the maximum amount");
        }
        /*
            This part is the output of the method. It uses stream() to filter for every parameter necessary
            and then returns the stream as an unmodifiable list.
        */
        return this.transactionsList.stream()
            .filter(t -> start.isEmpty() || !t.date().isBefore(start.get()))
            .filter(t -> finish.isEmpty() || !t.date().isAfter(finish.get()))
            .filter(t -> cause.isEmpty() || t.cause().equals(cause.get()))
            .filter(t -> isPositive.isEmpty() || t.isPositive() == isPositive.get())
            .filter(t -> min.isEmpty() || t.amount().compareTo(min.get()) >= 0)
            .filter(t -> max.isEmpty() || t.amount().compareTo(max.get()) <= 0)
            .toList();
    }

    /**
     * This method is used for creating and memorising a negative transaction when we wire 
     * transfer money from our bank account to others.
     * 
     * @param amount the amount of the operation.
     * @param date the date on which the operation has occurred.
     * @param cause used for the description of the operation that resulted in this wire transfer.
     * @param destination the destination where the money is going to go.
     * @return a boolean which is true if the operation was possible and executed succesfully,
     *      false otherwise.
     */
    @Override
    public boolean wireTransfer(final BigDecimal amount, final LocalDate date, final String cause, final String destination) {
        if (preAuthorize(amount.add(BigDecimal.ONE))) {
            this.balance = this.balance.subtract(amount.add(BigDecimal.ONE));
            final Transaction transaction = new Transaction(amount, BigDecimal.ONE, false, date, cause, destination);
            this.transactionsList.add(transaction);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will be used to extinguish the debit obtained via paying with the credit card.
     * 
     * @param amount the total amount spent with the credit card that we need to pay back.
     * @param interest the total interest we have to pay for the debit. In the transaction,
     *      this will be put as the fee of the transaction.
     * @param date the date on which the operation has occurred.
     * @param cardNumber the number of the card that originated this operation.
     * @return a boolean which is true if the operation was possible and executed succesfully,
     *      false otherwise.
     */
    @Override
    public boolean payCreditCard(final BigDecimal amount,
        final BigDecimal interest,
        final LocalDate date,
        final String cardNumber) {
        if (preAuthorize(amount.add(BigDecimal.ONE))) {
            this.balance = this.balance.subtract(amount.add(interest));
            final Transaction transaction = new Transaction(amount,
                interest,
                false,
                date,
                "Extinguishing credit card debt",
                cardNumber);
            this.transactionsList.add(transaction);
            return true;
        } else {
            return false;
        }
    }
}
