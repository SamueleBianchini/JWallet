package project.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import project.model.BankAccountInterface;
import project.model.Transaction;
import project.utility.DialogUtils;
import project.utility.InputValidator;

/**
 * This class is the implementation of the BankAccountControllerInterface.
 * As the controller, its methods will be called directly by the inputs from
 * the View, and then it will implement all the logic behind the operation and use
 * the methods from the model to execute the operations.
 */
public class BankAccountControllerImpl implements BankAccountControllerInterface {

    private static final String AMOUNT_ERROR_MESSAGE = "Amount";
    private static final String DATE_ERROR_MESSAGE = "Date";

    private Optional<LocalDate> start;
    private Optional<LocalDate> finish;
    private Optional<Boolean> positiveFilter;
    private Optional<String> otherParty;
    private Optional<BigDecimal> min;
    private Optional<BigDecimal> max;

    private final BankAccountInterface bankAccount;
    /*I need to create the controllerMaster to know what and how to import it
     in this class to finish the method for recharging the card without getting warnings.*/

    /**
     * This is the constructor for the BankAccountControllerImpl.
     * 
     * @param bankAccount the bankAccount on which the controller will work.
     *      For now this is a parameter because I don't know yet if the masterController
     *      will be in charge of creating the bankAccount or if this class will be.
     * 
     */
    public BankAccountControllerImpl(final BankAccountInterface bankAccount) {
        /* Input validation */
        InputValidator.checkArgument(bankAccount, p -> true, "Bank account");
        /* Actual constructor */
        this.bankAccount = bankAccount;
        this.start = Optional.empty();
        this.finish = Optional.empty();
        this.positiveFilter = Optional.empty();
        this.otherParty = Optional.empty();
        this.min = Optional.empty();
        this.max = Optional.empty();
    }

    /**
     * This is the getter for the field iban of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the iban of the BankAccount as a String.
     */
    @Override
    public String getIban() {
        return this.bankAccount.getIban();
    }

    /**
     * This method is the getter for the field cardHolder of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the cardHolder's name as a String.
     */
    @Override
    public String getCardHolder() {
        return this.bankAccount.getCardHolder();
    }

    /**
     * This method is the getter for the balance of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the balance of the BankAccount as a BigDecimal.
     */
    @Override
    public BigDecimal getBalance() {
        return this.bankAccount.getBalance();
    }

    /**
     * This method is used to extract the list of the transactions made with the bankAccount,
     * while also selecting them based on specific instructions that can be put as parameters
     * to specify the period, the cause, positive or negative, or the amount of the selected operations.
     * 
     * @return a list of the transactions relative to this bank account.
     */
    @Override
    public List<Transaction> viewBankTransactions() {
        return this.bankAccount.viewBankTransactions(this.start,
            this.finish,
            this.otherParty,
            this.positiveFilter,
            this.min,
            this.max
        );
    }

    /**
     * This method is used to save new filters for the extraction of the list of the transactions
     * made with the bankAccount.
     * 
     * @param newStart a String that contains the input value for the filter for the minimum date of the selection.
     *      If not present, there will not be a minimum date to consider.
     * @param newFinish a String that contains the input value for the filter for the maximum date of the selection.
     *      If not present, there will not be a maximum date do consider.
     * @param newOtherParty a String that contains the input value for the filter for the selected otherParty.
     *      If not present, all possible other parties will be considered.
     * @param positivePressed a boolean which is true if we are selecting only positive transactions.
     * @param negativePressed a boolean which is true if we are selecting only negative transactions.
     * @param newMin a String that contains the input value for the filter for the minimum amount of the selection.
     *      If not present, no minimum will be considered.
     * @param newMax a String that contains the input value for the filter for the maximum amount of the selection.
     *      If not present, no maximum will be considered.
     */
    @Override
    public void updateTransactionsFilter(
        final String newStart,
        final String newFinish,
        final String newOtherParty,
        final boolean positivePressed,
        final boolean negativePressed,
        final String newMin,
        final String newMax
    ) {
        InputValidator.checkArgument(newStart, p -> true, "Starting date");
        InputValidator.checkArgument(newFinish, p -> true, "Finishing date");
        InputValidator.checkArgument(newOtherParty, p -> true, "Other party");
        InputValidator.checkArgument(newMin, p -> true, "Minimum amount");
        InputValidator.checkArgument(newMax, p -> true, "Maximum amount");

        if (newMin.isBlank()) {
            this.min = Optional.empty();
        } else {
            this.min = Optional.of(this.amountParser(newMin));
        }

        if (newMax.isBlank()) {
            this.max = Optional.empty();
        } else {
            this.max = Optional.of(this.amountParser(newMax));
        }

        if (this.min.isPresent() && this.max.isPresent() && this.max.get().compareTo(this.min.get()) < 0) {
            throw new IllegalArgumentException("Minimum amount cannot be higher than maximum amount");
        }

        if (newStart.isBlank()) {
            this.start = Optional.empty();
        } else {
            this.start = Optional.of(this.dateParser(newStart));
        }

        if (newFinish.isBlank()) {
            this.finish = Optional.empty();
        } else {
            this.finish = Optional.of(this.dateParser(newFinish));
        }

        if (this.start.isPresent() && this.finish.isPresent() && this.finish.get().isBefore(this.start.get())) {
            throw new IllegalArgumentException("Starting date cannot be after finishing date");
        }

        if (positivePressed) {
            this.positiveFilter = Optional.of(true);
        } else if (negativePressed) {
            this.positiveFilter = Optional.of(false);
        } else {
            this.positiveFilter = Optional.empty();
        }

        if (newOtherParty.isBlank()) {
            this.otherParty = Optional.empty();
        } else {
            this.otherParty = Optional.of(newOtherParty);
        }
    }

    /**
     * This method is used to deposit cash directly to the bankAccount.
     * For this reason, the cause will not be needed as an input as it will be
     * a standard "cash deposit" and the otherParty will just be the cardHolder's name.
     * Furthermore, there is no fee on cash deposits.
     * 
     * @param amount the amount we want to deposit, not parsed yet.
     * @param date the date on which the deposit has occurred, not parsed yet.
     */
    @Override
    public void deposit(final String amount, final String date) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> !p.isBlank(), AMOUNT_ERROR_MESSAGE);
        InputValidator.checkArgument(date, p -> !p.isBlank(), DATE_ERROR_MESSAGE);
        final BigDecimal parsedAmount = this.amountParser(amount);
        final LocalDate parsedDate = this.dateParser(date);
        /* Method execution */
        this.bankAccount.executeTransaction(new Transaction(
            parsedAmount,
            BigDecimal.ZERO,
            true,
            parsedDate,
            "cash deposit",
            this.bankAccount.getCardHolder()
        ));
    }

    /**
     * This method is used to transfer the money via wireTransfer.
     * This type of operations will have a fee attached, equal to one euro.
     * 
     * @param amount the amount we want to transfer, not parsed yet.
     * @param date the date on which the transfer occurs, not parsed yet.
     * @param cause the cause of the operation.
     * @param destination the destination of the wireTransfer.
     */
    @Override
    public void wireTransfer(final String amount, final String date, final String cause, final String destination) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> !p.isBlank(), AMOUNT_ERROR_MESSAGE);
        InputValidator.checkArgument(date, p -> !p.isBlank(), DATE_ERROR_MESSAGE);
        InputValidator.checkArgument(cause, p -> !p.isBlank(), "Cause");
        InputValidator.checkArgument(destination, p -> !p.isBlank() && !p.equals(this.getCardHolder()), "Destination");
        final BigDecimal parsedAmount = this.amountParser(amount);
        final LocalDate parsedDate = this.dateParser(date);
        /* Method execution */
        if (this.bankAccount.preAuthorize(parsedAmount.add(BigDecimal.ONE))) {
            this.bankAccount.executeTransaction(new Transaction(
                parsedAmount,
                BigDecimal.ONE,
                false,
                parsedDate,
                cause,
                destination
            ));
        } else {
            throw new IllegalArgumentException("Not enough balance");
        }
    }

    /**
     * This method is used to charge a PrepaidCard by adding to its balance money directly taken
     * from the BankAccount. This type of operation will have a fee of one euro, and the
     * standard cause "Recharging the Prepaidcard balance".
     * 
     * @param amount the amount we want to recharge, not parsed yet.
     * @param date the date on which we will recharge, not parsed yet.
     * @param cardNumber the number of the card we want to recharge.
     */
    @Override
    public void prepaidCharge(final String amount, final String date, final String cardNumber) {
        /* Input validation */
        InputValidator.checkArgument(amount, p -> !p.isBlank(), AMOUNT_ERROR_MESSAGE);
        InputValidator.checkArgument(date, p -> !p.isBlank(), DATE_ERROR_MESSAGE);
        InputValidator.checkArgument(cardNumber, p -> !p.isBlank(), "Prepaid card");
        final BigDecimal parsedAmount = this.amountParser(amount);
        final LocalDate parsedDate = this.dateParser(date);
        /* Method execution */
        if (this.bankAccount.preAuthorize(parsedAmount.add(BigDecimal.ONE))) {
            this.bankAccount.executeTransaction(new Transaction(
                parsedAmount,
                BigDecimal.ONE,
                false,
                parsedDate,
                "Recharging the prepaid card",
                cardNumber
            ));
        }
    }

    /**
     * This method is used to parse the string in a field to get its represented BigDecimal.
     * 
     * @param amount the string to parse.
     * @return the parsed BigDecimal
     */
    @Override
    public BigDecimal amountParser(final String amount) {
        InputValidator.checkArgument(amount, p -> !p.isBlank(), AMOUNT_ERROR_MESSAGE);
        final BigDecimal parsedAmount;
        parsedAmount = DialogUtils.getBigDecimalValue(amount);
        if (parsedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount cannot be zero or lower");
        }
        return parsedAmount;
    }

    /**
     * This method is used to parse the string in a field to get its represented LocalDate.
     * 
     * @param date the string to parse.
     * @return the parsed LocalDate.
     */
    @Override
    public LocalDate dateParser(final String date) {
        InputValidator.checkArgument(date, p -> !p.isBlank(), DATE_ERROR_MESSAGE);
        final LocalDate parsedDate;
        try {
            parsedDate = DialogUtils.getLocalDateValue(date);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(e);
        }
        return parsedDate;
    }
}
