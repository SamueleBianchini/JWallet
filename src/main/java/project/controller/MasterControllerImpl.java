package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.card.PrepaidCard;

/**
 * This is the MasterController for the project, and as such it will have many purposes.
 * It should be created by the LoginController, and it should create every class needed
 * by the application, as this situation is considered the true start of the application.
 * (The classes needed are the BankAccount, all the cards and the assets/investments)
 * It should also be the responsible of switching between different views.
 */
public class MasterControllerImpl implements MasterControllerInterface {

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
        return false;
    }
}
