package project.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import project.model.BankAccountImpl;
import project.model.BankAccountInterface;
import project.model.card.DebitCard;
import project.model.card.PrepaidCard;
import project.view.BankAccountView;

/**
 * This is the MasterController for the project, and as such it will have many purposes.
 * It should be created by the LoginController, and it should create every class needed
 * by the application, as this situation is considered the true start of the application.
 * (The classes needed are the BankAccount, all the cards and the assets/investments)
 * It should also be the responsible of switching between different views.
 */
public class MasterControllerImpl implements MasterControllerInterface {

    /** The username used to log-in during the tests. */
    private static final String TEST_USERNAME = "user1";
    /** The password used to log-in during the tests. */
    private static final String TEST_PASSWORD = "password";

    /** The number of years of validity of the cards created during the tests. */
    private static final int CARD_VALIDITY_YEARS = 5;
    /** The width of the bank account window. */
    private static final int WINDOW_WIDTH = 900;
    /** The height of the bank account window. */
    private static final int WINDOW_HEIGHT = 600;

    private DebitCard debitCard;

    private final List<PrepaidCard> prepaidCards;

    /**
     * This is the constructor for the master controller.
     */
    public MasterControllerImpl() {
        this.prepaidCards = new ArrayList<>();
    }

    /**
     * This method is the getter for the list of prepaid cards.
     * 
     * @return an immodifyable copy of the PrepaidCards list.
     */
    @Override
    public List<PrepaidCard> getPrepaidCards() {
        return List.copyOf(prepaidCards);
    }

    /**
     * This method is used to get a specific Prepaidcard, chosen by its card number.
     * 
     * @param cardNumber a string containing the number of the chosen card.
     * @return the chosen prepaid card.
     */
    @Override
    public PrepaidCard getSinglePrepaidCard(final String cardNumber) {
        for (final PrepaidCard card : this.prepaidCards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        throw new IllegalArgumentException("No card registered with that number");
    }

    /**
     * This method is the getter for the debit card of the user.
     *
     * @return the debit card created after the login.
     */
    @Override
    public DebitCard getDebitCard() {
        return this.debitCard;
    }

    /**
     * This method is the one responsible for verifying the login and, if correct,
     * closing the LoginView and starting the BankAccountView, as well as constructing
     * everything necessary for the application.
     * 
     * @param username the username to verify.
     * @param password the password to verify.
     * @return returns true if the username and password were correct and the application
     *      started successfully, false otherwise.
     */
    @Override
    public boolean login(final String username, final String password) {
        if (!TEST_USERNAME.equals(username) || !TEST_PASSWORD.equals(password)) {
            return false;
        }

        final BankAccountInterface bankAccount = new BankAccountImpl("IT60X0542811101000000123456", "user");
        this.debitCard = new DebitCard(
            "4000123412341234",
            "user",
            LocalDate.now().plusYears(CARD_VALIDITY_YEARS),
            "132",
            "12345"
        );
        final BankAccountView view = new BankAccountView(this, bankAccount);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        return true;
    }
}
