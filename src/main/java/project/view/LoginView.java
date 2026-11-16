package project.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import project.controller.MasterControllerInterface;
import project.utility.DialogUtils;
import project.utility.InputValidator;

/**
 * This class is the view for the login part of the project.
 * It should be called by the main at the start of the application, display
 * a prompt with username and password, and then use the MasterController to
 * verify the username and password.
 */
public final class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int FIELD_COLUMNS = 30;

    private final transient MasterControllerInterface masterController;

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    /**
     * This is the constructor for the LoginView.
     *
     * @param masterController the masterController this class will use to verify the login.
     */
    public LoginView(final MasterControllerInterface masterController) {
        super("JWallet - Login");

        /* Input validation */

        InputValidator.checkArgument(masterController, p -> true, "Master controller");

        /* Actual constructor */

        this.masterController = masterController;

        this.usernameField = DialogUtils.createTextField();
        this.passwordField = new JPasswordField(FIELD_COLUMNS);
        this.loginButton = new JButton("Login");

        final JPanel formPanel = DialogUtils.createFormPanel();
        DialogUtils.addField(formPanel, "Username", this.usernameField, 0);
        DialogUtils.addField(formPanel, "Password", this.passwordField, 1);

        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(this.loginButton);

        this.loginButton.addActionListener(event -> this.attemptLogin());

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(this.loginButton);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is used by the login button to ask the masterController to verify
     * the username and password. If they are correct the masterController opens the
     * BankAccountView and this window is closed, otherwise
     * an error message is shown and the password field is cleared.
     */
    private void attemptLogin() {
        final String username = this.usernameField.getText().trim();
        final String password = new String(this.passwordField.getPassword());

        if (this.masterController.login(username, password)) {
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Wrong username or password",
                "Login failed",
                JOptionPane.ERROR_MESSAGE
            );
            this.passwordField.setText("");
        }
    }
}
