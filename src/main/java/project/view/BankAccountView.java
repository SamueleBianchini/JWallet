package project.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import project.controller.BankAccountControllerInterface;
import project.model.BankAccountInterface;
import project.controller.BankAccountControllerImpl;
import project.controller.MasterControllerInterface;
import project.utility.InputValidator;
import project.utility.DialogUtils;

/**
 * This class is the GUI for the window for the BankAccount.
 * BankAccountView
 */
public final class BankAccountView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String FONT = "Arial";
    private static final String INVALID_INPUT = "Invalid input";

    private static final int SIDE_MENU_BUTTONS_WIDTH = 180;
    private static final int SIDE_MENU_BUTTONS_HEIGHT = 50;
    private static final int SIDE_MENU_BUTTONS_DISTANCE = 15;
    private static final int SIDE_MENU_WIDTH = 200;
    private static final int SIDE_MENU_VERTICAL_BORDER = 20;
    private static final int SIDE_MENU_HORIZONTAL_BORDER = 10;

    private static final int MAIN_MENU_VERTICAL_GAP = 20;
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

    private static final int TRANSACTIONS_PANEL_VERTICAL_GAP = 15;
    private static final int TRANSACTIONS_PANEL_BORDER_NORTH = 15;
    private static final int TRANSACTIONS_PANEL_FONT_SIZE = 20;
    private static final int TRANSACTIONS_PANEL_TABLE_FONT_SIZE = 13;
    private static final int TRANSACTIONS_PANEL_TABLE_TITLE_FONT_SIZE = 16;
    private static final int TRANSACTIONS_PANEL_TABLE_ROW_HEIGHT = 35;

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

    private final JLabel balanceLabel;

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
        InputValidator.checkArgument(bankAccount, p -> true, "Bank account");

        /* Actual constructor (panel creation section) */

        this.setLayout(new BorderLayout());
        this.sideMenu = new JPanel();
        this.mainMenu = new JPanel();
        this.infoPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.transactionsPanel = new JPanel();

        this.add(this.sideMenu, BorderLayout.WEST);
        this.add(this.mainMenu, BorderLayout.CENTER);

        /* Actual constructor (buttons creation section) */

        this.goToInvestmentsButton = new JButton();
        this.goToAssetsButton = new JButton();
        this.goToCardsButton = new JButton();

        this.depositButton = new JButton();
        this.wireTransferButton = new JButton();
        this.prepaidChargeButton = new JButton();

        this.transactionsFilterButton = new JButton();
        this.transactionsSwitchButton = new JButton();

        /* Actual constructor (controllers section) */

        this.bankAccountController = new BankAccountControllerImpl(bankAccount);
        this.masterController = masterController;

        this.balanceLabel = new JLabel(this.bankAccountController.getBalance().toString().concat(" €"));

        /* Actual constructor (view definition section) */

        this.createSideMenu();
        this.createMainMenu();
        this.mainMenu.add(this.infoPanel, BorderLayout.NORTH);
        this.mainMenu.add(this.buttonsPanel, BorderLayout.SOUTH);
        this.mainMenu.add(this.transactionsPanel, BorderLayout.CENTER);
        this.createInfoPanel();
        this.createButtonsPanel();
        this.createTransactionsPanel();
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
        this.mainMenu.setLayout(new BorderLayout(0, MAIN_MENU_VERTICAL_GAP));
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

        /* This listener is just temporary until Davide finishes the masterController */
        this.prepaidChargeButton.addActionListener(p -> {
            this.bankAccountController.prepaidCharge(
                BigDecimal.ZERO,
                LocalDate.now(),
                this.masterController.getSinglePrepaidCard("Numero carta"));
        });

        this.depositButton.addActionListener(p -> this.showDepositDialog());
        this.wireTransferButton.addActionListener(p -> this.showWireTransferDialog());
    }

    /**
     * This method is used by the constructor to setup the buttonsPanel.
     */
    private void createTransactionsPanel() {
        this.transactionsPanel.setLayout(new BorderLayout(0, TRANSACTIONS_PANEL_VERTICAL_GAP));

        this.transactionsPanel.setBorder(BorderFactory.createEmptyBorder(
            TRANSACTIONS_PANEL_BORDER_NORTH,
            0,
            0,
            0
        ));

        /* Panel header */

        final JPanel headerPanel = new JPanel(new BorderLayout());

        final JLabel title = new JLabel("Recent transactions");
        title.setFont(new Font(FONT, Font.BOLD, TRANSACTIONS_PANEL_FONT_SIZE));

        this.transactionsFilterButton.setText("Filter");
        this.transactionsSwitchButton.setText("Switch view");

        final JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerButtons.add(this.transactionsFilterButton);
        headerButtons.add(this.transactionsSwitchButton);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(headerButtons, BorderLayout.EAST);

        /* Transactions table */

        final String[] columns = {"Date", "Cause", "Other party", "Amount", "Fee", "Direction"};

        final JTable transactionsTable = new JTable(
            this.bankAccountController.viewBankTransactions(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            ).size(),
            columns.length
        );

        transactionsTable.setFont(new Font(FONT, Font.PLAIN, TRANSACTIONS_PANEL_TABLE_FONT_SIZE));
        transactionsTable.setRowHeight(TRANSACTIONS_PANEL_TABLE_ROW_HEIGHT);
        transactionsTable.getTableHeader().setFont(new Font(FONT, Font.BOLD, TRANSACTIONS_PANEL_TABLE_TITLE_FONT_SIZE));

        transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        transactionsTable.setFillsViewportHeight(true);

        final JScrollPane scrollPane = new JScrollPane(transactionsTable);

        /* Final panel composition */

        this.transactionsPanel.add(headerPanel, BorderLayout.NORTH);
        this.transactionsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This method is used to update the balance in the view.
     */
    private void updateBalance() {
        this.balanceLabel.setText(this.bankAccountController.getBalance().toString().concat(" €"));
    }

    /**
     * This method is used to create the dialog for the operation deposit.
     */
    private void showDepositDialog() {
        final JDialog dialog = DialogUtils.createDialog(SwingUtilities.getWindowAncestor(this), "Deposit");

        final JPanel formPanel = DialogUtils.createFormPanel();

        final JTextField amountField = DialogUtils.createTextField();
        final JTextField dateField = DialogUtils.createDateField();

        DialogUtils.addField(formPanel, "Amount (€) :", amountField, 0);
        DialogUtils.addField(formPanel, "Date :", dateField, 1);

        final JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(p -> {
            final BigDecimal amount;
            try {
                amount = DialogUtils.getBigDecimalValue(amountField);
            } catch (final IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Enter a valid amount.",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Amount must be positive.",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            final LocalDate date;

            try {
                date = DialogUtils.getLocalDateValue(dateField);
            } catch (final DateTimeParseException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Enter a valid date (dd/MM/yyyy).",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                this.bankAccountController.deposit(amount, date);
            } catch (final IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Internal error",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            this.updateBalance();
            dialog.dispose();
        });
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(p -> dialog.dispose());

        final JPanel depositPanel = DialogUtils.createDialogButtons(confirmButton, cancelButton);

        depositPanel.add(cancelButton);
        depositPanel.add(confirmButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(depositPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * This method is used to create the dialog for the operation wireTransfer.
     */
    private void showWireTransferDialog() {
        final JDialog dialog = DialogUtils.createDialog(SwingUtilities.getWindowAncestor(this), "Wire transfer");

        final JPanel formPanel = DialogUtils.createFormPanel();

        final JTextField amountField = DialogUtils.createTextField();
        final JTextField dateField = DialogUtils.createDateField();
        final JTextField causeField = DialogUtils.createTextField();
        final JTextField destinationField = DialogUtils.createTextField();

        DialogUtils.addField(formPanel, "Amount (€) :", amountField, 0);
        DialogUtils.addField(formPanel, "Date :", dateField, 1);
        DialogUtils.addField(formPanel, "Cause :", causeField, 2);
        DialogUtils.addField(formPanel, "Destination :", destinationField, 3);

        final JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(p -> {
            final BigDecimal amount;
            try {
                amount = DialogUtils.getBigDecimalValue(amountField);
            } catch (final IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Enter a valid amount.",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Amount must be positive.",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            final LocalDate date;

            try {
                date = DialogUtils.getLocalDateValue(dateField);
            } catch (final DateTimeParseException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Enter a valid date (dd/MM/yyyy).",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            final String cause;
            final String destination;

            if (causeField.getText().isBlank()) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Insert a cause",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            } else if (destinationField.getText().isBlank()) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Insert a cause",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            } else {
                cause = causeField.getText();
                destination = destinationField.getText();
            }

            try {
                this.bankAccountController.wireTransfer(amount, date, cause, destination);
            } catch (final IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Not enough balance or internal error",
                    INVALID_INPUT,
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            this.updateBalance();
            dialog.dispose();
        });
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(p -> dialog.dispose());

        final JPanel depositPanel = DialogUtils.createDialogButtons(confirmButton, cancelButton);

        depositPanel.add(cancelButton);
        depositPanel.add(confirmButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(depositPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}
