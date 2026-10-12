package src.main.java.project;

import java.util.List;

/**
 * Interface that defines the methods needed for the functioning of all the operations for the BankAccount.
 */
public interface BankAccount {

    /**
     * This method is used to add an amount to the Bank Account balance, as a result of different types of operations,
     * like cash deposit, wire transfer or others.
     * 
     * @param amount the amount to deposit.
     * @param cause used to set the description of the operation that resulted in this deposit.
     */
    void deposit(float amount, String cause);

    /**
     * This is the specific method used to recharge a Prepaid Card with a specific amount of money.
     * It should lower the balance of the bank account by the specified amount and then call a function
     * of the Prepaid Card to augment its balance.
     * 
     * @param amount the amount we need to recharge.
     */
    void prepaidCharge(float amount);

    /**
     * This method is used to extract the list of the transactions made with the bank account,
     * for an external view to process and show on screen.
     * 
     * @return a list of the transactions relative to this bank account.
     */
    List<Transaction> viewBankTransactions();
}
