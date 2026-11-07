package project.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import project.controller.BankAccountControllerInterface;
import project.model.BankAccountInterface;
import project.controller.BankAccountControllerImpl;
import project.controller.MasterControllerInterface;
import project.utility.InputValidator;

/**
 * This class is the GUI for the window for the BankAccount.
 * BankAccountView
 */
public final class BankAccountView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String FONT = "Arial";

    private static final int SIDE_MENU_BUTTONS_WIDTH = 180;
    private static final int SIDE_MENU_BUTTONS_HEIGHT = 50;
    private static final int SIDE_MENU_BUTTONS_DISTANCE = 15;
    private static final int SIDE_MENU_WIDTH = 200;
    private static final int SIDE_MENU_VERTICAL_BORDER = 20;
    private static final int SIDE_MENU_HORIZONTAL_BORDER = 10;

    private static final int MAIN_MENU_HEIGHT = 20;
    private static final int MAIN_MENU_VERTICAL_BORDER = 25;
    private static final int MAIN_MENU_HORIZONTAL_BORDER = 30;
    private static final int MAIN_MENU_RED = 245;
    private static final int MAIN_MENU_GREEN = 247;
    private static final int MAIN_MENU_BLUE = 250;

    private static final int INFO_PANEL_VERTICAL_BORDER = 20;
    private static final int INFO_PANEL_HORIZONTAL_BORDER = 25;
    private static final int INFO_PANEL_FONT_TITLE_SIZE = 16;
    private static final int INFO_PANEL_FONT_NUMBER_SIZE = 34;
    private static final int INFO_PANEL_TITLE_SPACING = 10;
    private static final int INFO_PANEL_IBAN_SPACING = 15;

    private static final int BUTTONS_PANEL_GRID_ROWS = 1;
    private static final int BUTTONS_PANEL_GRID_COLS = 3;
    private static final int BUTTONS_PANEL_GRID_SPACING = 15;
    private static final int BUTTONS_PANEL_VERTICAL_BORDER = 10;
    private static final int BUTTONS_PANEL_FONT_SIZE = 14;

    private final transient BankAccountControllerInterface bankAccountController;
    private final transient MasterControllerInterface masterController;

    private final JPanel sideMenu;                    //Contains the buttons to switch GUI
    private final JPanel mainMenu;                    //Contains the rest of the panels
    private final JPanel infoPanel;                   //Contains the info about the bankAccount
    private final JPanel buttonsPanel;                //Contains the buttons to make new operations
    private final JPanel transactionsPanel;           //Contains the view of previous operations

    private final JButton goToInvestmentsButton;      //Switches to the Investments GUI
    private final JButton goToAssetsButton;           //Switches to the Assets GUI
    private final JButton goToCardsButton;            //Switches to the cards GUI
    private final JButton depositButton;              //Opens the deposit input prompt
    private final JButton wireTransferButton;         //Opens the wireTransfer input prompt
    private final JButton prepaidChargeButton;        //Opens the prepaidCharge input prompt
    private final JButton transactionsFilterButton;   //Opens the transactions selecion filter prompt
    private final JButton transactionsSwitchButton;   //Switches the type of view from list to graph

    /**
     * This method is the constructor for the BankAccountView.
     * 
     * @param masterController the masterController this class will use.
     * @param bankAccount the bankAccount this class will control.
     */
    public BankAccountView(final MasterControllerInterface masterController, final BankAccountInterface bankAccount) {
        super();

        /* Input validation */

        InputValidator.checkArgument(masterController, p -> true, "Master controller");
        InputValidator.checkArgument(bankAccount, p -> true, "Master controller");

        /* Actual constructor (panel creation section) */

        this.setLayout(new BorderLayout());
        this.sideMenu = new JPanel();
        this.mainMenu = new JPanel();
        this.infoPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.transactionsPanel = new JPanel();

        this.add(this.sideMenu, BorderLayout.WEST);
        this.add(this.mainMenu, BorderLayout.CENTER);
        this.mainMenu.add(this.infoPanel, BorderLayout.NORTH);
        this.mainMenu.add(this.buttonsPanel, BorderLayout.SOUTH);
        this.mainMenu.add(this.transactionsPanel, BorderLayout.CENTER);

        /* Actual constructor (buttons creation section) */

        this.goToInvestmentsButton = new JButton();
        this.goToAssetsButton = new JButton();
        this.goToCardsButton = new JButton();

        this.depositButton = new JButton();
        this.wireTransferButton = new JButton();
        this.prepaidChargeButton = new JButton();

        this.transactionsFilterButton = new JButton();
        this.transactionsSwitchButton = new JButton();

        /* Actual constructor (view definition section) */

        this.createSideMenu();
        this.createMainMenu();
        this.createInfoPanel();
        this.createButtonsPanel();
        this.createTransactionsPanel();

        /* Actual constructor (controllers section) */

        this.bankAccountController = new BankAccountControllerImpl(bankAccount);
        this.masterController = masterController;
    }

    /**
     * This method is used by the constructor to setup the sideMenu.
     */
    private void createSideMenu() {
        this.sideMenu.setLayout(new BoxLayout(this.sideMenu, BoxLayout.Y_AXIS));
        this.sideMenu.setPreferredSize(new Dimension(SIDE_MENU_WIDTH, 0));
        this.sideMenu.setBorder(BorderFactory.createEmptyBorder(
            SIDE_MENU_VERTICAL_BORDER,
            SIDE_MENU_HORIZONTAL_BORDER,
            SIDE_MENU_VERTICAL_BORDER,
            SIDE_MENU_HORIZONTAL_BORDER
        ));

        this.goToInvestmentsButton.setText("Investments");
        this.goToAssetsButton.setText("Assets");
        this.goToCardsButton.setText("Cards");

        final Dimension buttonSize = new Dimension(SIDE_MENU_BUTTONS_WIDTH, SIDE_MENU_BUTTONS_HEIGHT);

        for (final JButton button : List.of(
            this.goToInvestmentsButton,
            this.goToAssetsButton,
            this.goToCardsButton)) {

            button.setMaximumSize(buttonSize);

            this.sideMenu.add(button);
            this.sideMenu.add(Box.createVerticalStrut(SIDE_MENU_BUTTONS_DISTANCE));
        }
    }

    /**
     * This method is used by the constructor to setup the mainMenu.
     */
    private void createMainMenu() {
        this.mainMenu.setLayout(new BorderLayout(0, MAIN_MENU_HEIGHT));
        this.mainMenu.setBorder(BorderFactory.createEmptyBorder(
            MAIN_MENU_VERTICAL_BORDER,
            MAIN_MENU_HORIZONTAL_BORDER,
            MAIN_MENU_VERTICAL_BORDER,
            MAIN_MENU_HORIZONTAL_BORDER
        ));
        this.mainMenu.setBackground(new Color(MAIN_MENU_RED, MAIN_MENU_GREEN, MAIN_MENU_BLUE));
    }

    /**
     * This method is used by the constructor to setup the infoPanel.
     */
    private void createInfoPanel() {
        this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
        this.infoPanel.setBorder(BorderFactory.createEmptyBorder(
            INFO_PANEL_VERTICAL_BORDER,
            INFO_PANEL_HORIZONTAL_BORDER,
            INFO_PANEL_VERTICAL_BORDER,
            INFO_PANEL_HORIZONTAL_BORDER
        ));
        this.infoPanel.setBackground(Color.WHITE);

        final JLabel balanceTitle = new JLabel("Current balance");
        balanceTitle.setFont(new Font(FONT, Font.PLAIN, INFO_PANEL_FONT_TITLE_SIZE));
        balanceTitle.setForeground(Color.GRAY);

        final JLabel balanceLabel = new JLabel(this.bankAccountController.getBalance().toString().concat(" €"));
        balanceLabel.setFont(new Font(FONT, Font.BOLD, INFO_PANEL_FONT_NUMBER_SIZE));
        balanceLabel.setForeground(Color.BLACK);

        final JLabel ibanLabel = new JLabel(this.bankAccountController.getIban());
        ibanLabel.setFont(new Font(FONT, Font.PLAIN, INFO_PANEL_FONT_TITLE_SIZE));
        ibanLabel.setForeground(Color.GRAY);

        this.infoPanel.add(balanceTitle);
        this.infoPanel.add(Box.createVerticalStrut(INFO_PANEL_TITLE_SPACING));
        this.infoPanel.add(balanceLabel);
        this.infoPanel.add(Box.createVerticalStrut(INFO_PANEL_IBAN_SPACING));
        this.infoPanel.add(ibanLabel);
    }

    /**
     * This method is used by the constructor to setup the transactionsPanel.
     */
    private void createButtonsPanel() {
        this.buttonsPanel.setLayout(new GridLayout(
            BUTTONS_PANEL_GRID_ROWS,
            BUTTONS_PANEL_GRID_COLS,
            BUTTONS_PANEL_GRID_SPACING,
            0
        ));
        this.buttonsPanel.setBorder(BorderFactory.createEmptyBorder(
            BUTTONS_PANEL_VERTICAL_BORDER,
            0,
            BUTTONS_PANEL_VERTICAL_BORDER,
            0
        ));

        this.depositButton.setText("Deposit");
        this.wireTransferButton.setText("Wire transfer");
        this.prepaidChargeButton.setText("Prepaid card recharge");

        for (final JButton button : List.of(
            this.depositButton,
            this.wireTransferButton,
            this.prepaidChargeButton
        )) {
            button.setFont(new Font(FONT, Font.BOLD, BUTTONS_PANEL_FONT_SIZE));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.GRAY);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
        }
    }

    /**
     * This method is used by the constructor to setup the buttonsPanel.
     */
    private void createTransactionsPanel() {

    }

}
