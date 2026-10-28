package project.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import project.model.Transaction;

/**
 * This class is an interface used to define all of the methods that BankAccountControllerImpl
 * will implement. As the controller, its methods will be called directly by the inputs from
 * the View, and then it will implement all the logic behind the operation and use
 * the methods from the model to execute the operations.
 */
public interface BankAccountControllerInterface {

    /**
     * This is the getter for the field iban of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the iban of the BankAccount as a String.
     */
    String getIban();

    /**
     * This method is the getter for the field cardHolder of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the cardHolder's name as a String.
     */
    String getCardHolder();

    /**
     * This method is the getter for the balance of the BankAccount.
     * We will need this method to show it on the view.
     * 
     * @return the balance of the BankAccount as a BigDecimal.
     */
    BigDecimal getBalance();

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
    List<Transaction> viewBankTransactions(
        Optional<LocalDate> start,
        Optional<LocalDate> finish,
        Optional<String> otherParty,
        Optional<Boolean> isPositive,
        Optional<BigDecimal> min,
        Optional<BigDecimal> max
    );

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
    boolean deposit(BigDecimal amount, LocalDate date);

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
    boolean wiretransfer(BigDecimal amount, LocalDate date, String cause, String destination);

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
    boolean prepaidCharge(BigDecimal amount, LocalDate date, String cardNumber);

}
