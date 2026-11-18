package project.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    void updateTransactionsFilter(
        String newStart,
        String newFinish,
        String newOtherParty,
        boolean positivePressed,
        boolean negativePressed,
        String newMin,
        String newMax
    );

    /**
     * This method is used to extract the list of the transactions made with the bankAccount,
     * using the filters currently saved.
     * 
     * @return a list of the transactions relative to this bank account.
     */
    List<Transaction> viewBankTransactions();

    /**
     * This method is used to deposit cash directly to the bankAccount.
     * For this reason, the cause will not be needed as an input as it will be
     * a standard "cash deposit" and the otherParty will just be the cardHolder's name.
     * Furthermore, there is no fee on cash deposits.
     * 
     * @param amount the amount we want to deposit, not parsed yet.
     * @param date the date on which the deposit has occurred, not parsed yet.
     */
    void deposit(String amount, String date);

    /**
     * This method is used to transfer the money via wireTransfer.
     * This type of operations will have a fee attached, equal to one euro.
     * 
     * @param amount the amount we want to transfer, not parsed yet.
     * @param date the date on which the transfer occurs, not parsed yet.
     * @param cause the cause of the operation.
     * @param destination the destination of the wireTransfer.
     */
    void wireTransfer(String amount, String date, String cause, String destination);

    /**
     * This method is used to charge a PrepaidCard by adding to its balance money directly taken
     * from the BankAccount. This type of operation will have a fee of one euro, and the
     * standard cause "Recharging the Prepaidcard balance".
     * 
     * @param amount the amount we want to recharge, not parsed yet.
     * @param date the date on which we will recharge, not parsed yet.
     * @param cardNumber the number of the card we want to recharge.
     */
    void prepaidCharge(String amount, String date, String cardNumber);

    /**
     * This method is used to parse the string in a field to get its represented BigDecimal.
     * 
     * @param amount the string to parse.
     * @return the parsed BigDecimal
     */
    BigDecimal amountParser(String amount);

    /**
     * This method is used to parse the string in a field to get its represented LocalDate.
     * 
     * @param date the string to parse.
     * @return the parsed LocalDate.
     */
    LocalDate dateParser(String date);

}
