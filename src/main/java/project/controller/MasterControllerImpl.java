package project.controller;

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

    /*
     * This method is the constructor for the MasterControllerImpl.

    public MasterControllerImpl() {

    }*/

    /**
     * This method is the getter for the list of prepaid cards.
     * 
     * @return an immodifyable copy of the PrepaidCards list.
     */
    @Override
    public List<PrepaidCard> getPrepaidCards() {
        return List.of();
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
