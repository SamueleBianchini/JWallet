package project;

import javax.swing.SwingUtilities;

import project.controller.MasterControllerImpl;
import project.controller.MasterControllerInterface;
import project.view.LoginView;

/**
 * This class is the main of the project.
 * It should prepare the environment and then call the ControllerMaster and start the login GUI.
 */
public final class Main {

    /**
     * This constructor is private and empty because this class only contains the main method.
     */
    private Main() {

    }

    /**
     * This method is the entry point of the application.
     * It creates the MasterController and then opens the login GUI, passing the controller to it.
     *
     * @param args the arguments of the command line, which are not used.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MasterControllerInterface master = new MasterControllerImpl();
            new LoginView(master).setVisible(true);
        });
    }
}
