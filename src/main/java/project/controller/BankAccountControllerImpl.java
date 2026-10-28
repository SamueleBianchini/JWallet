package project.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import project.model.BankAccountInterface;
import project.model.Transaction;

/**
 * This class is the implementation of the BankAccountControllerInterface.
 * As the controller, its methods will be called directly by the inputs from
 * the View, and then it will implement all the logic behind the operation and use
 * the methods from the model to execute the operations.
 * BankAccountControllerImpl
 */
public class BankAccountControllerImpl implements BankAccountControllerInterface {

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
        this.bankAccount = bankAccount;
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
     * @param start an optional that may contain a date from where to start the selection.
     *      If not present, there will not be a minimum date to consider.
     * @param finish an optional that may contain a date after which the operation is not selected
     *      anymore. If not present, there will not be a maximum date do consider.
     * @param otherParty an optional that may contain the specific other party for the operations we want
     *      to select. If not present, all possible other parties will be considered.
     * @param isPositive an optional that may contain a boolean which, if positive, means
     *      that the operations we need to select are only positive, if false only negative ones,
     *      if not present means both.
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
        final Optional<String> otherParty,
        final Optional<Boolean> isPositive,
        final Optional<BigDecimal> min,
        final Optional<BigDecimal> max
    ) {
        return this.bankAccount.viewBankTransactions(start, finish, otherParty, isPositive, min, max);
    }

    /**
     * This method is used to deposit cash directly to the bankAccount.
     * For this reason, the cause will not be needed as an input as it will be
     * a standard "cash deposit" and the otherParty will just be the cardHolder's name.
     * Furthermore, there is no fee on cash deposits.
     * 
     * @param amount the amount we want to deposit.
     * @param date the date on which the deposit has occurred.
     * @return a boolean which is true if the operation was successfull and false otherwise.
     */
    @Override
    public boolean deposit(final BigDecimal amount, final LocalDate date) {
        return this.bankAccount.executeTransaction(new Transaction(
            amount,
            BigDecimal.ZERO,
            true,
            date,
            "cash deposit",
            this.bankAccount.getCardHolder()
        ));
    }

    /**
     * This method is used to trasnfer the money via wireTransfer.
     * This type of operations will have a fee attached, equal to one euro.
     * 
     * @param amount the amount we want to transfer.
     * @param date the date on which the transfer occurs.
     * @param cause the cause of the operation.
     * @param destination the destination of the wireTransfer.
     * @return a boolean which is true if the operation was successfull and false otherwise.
     */
    @Override
    public boolean wiretransfer(final BigDecimal amount, final LocalDate date, final String cause, final String destination) {
        return this.bankAccount.preAuthorize(amount.add(BigDecimal.ONE))
            && this.bankAccount.executeTransaction(new Transaction(
                amount,
                BigDecimal.ONE,
                false,
                date,
                cause,
                destination
            ));
    }

    /**
     * This method is used to charge a PrepaidCard by adding to its balance money directly taken
     * from the BankAccount. This type of operation will have a fee of one euro, and the
     * standard cause "Recharging the Prepaidcard balance".
     * 
     * @param amount the amount we want to recharge
     * @param date the date on which we will recherge.
     * @param cardNumber the number of the card we want to recharge.
     * @return a boolean which is true if the operation was successfull and false otherwise.
     */
    @Override
    public boolean prepaidCharge(final BigDecimal amount, final LocalDate date, final String cardNumber) {
        /*This method just needs to know how to get the List of prepaidaCards and then it will be ready.
        if (this.bankAccount.preAuthorize(amount.add(BigDecimal.ONE))) {
            for (final PrepaidCard card : this.prepaidCards) {
                if (card.getCardNumber().equals(cardNumber) && card.recharge(amount, date, this.bankAccount.getIban())) {
                    return this.bankAccount.executeTransaction(new Transaction(
                        amount,
                        BigDecimal.ONE,
                        false,
                        date,
                        "Recharging the Prepaidcard balance",
                        cardNumber
                    ));
                }
            }
        }*/
        return false;
    }
}
