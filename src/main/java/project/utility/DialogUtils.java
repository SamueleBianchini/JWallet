package project.utility;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListCellRenderer;

import project.model.card.PrepaidCard;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * This class is a utility class that defines the mothods to simplify the creation
 * and management of the dialogs that the different views use to create a prompt
 * for writing the necessary parameters for the operations.
 */
public final class DialogUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT);

    private static final String FIELD = "Field";

    private static final int DIALOG_PADDING = 10;
    private static final int PANEL_PADDING = 10;
    private static final int FIELD_INSET = 8;
    private static final int TEXT_FIELD_SIZE = 30;
    private static final int DATE_FIELD_SIZE = 30;

    /**
     * This constructor is private and empty because this is a utility class,
     * therefore it is only used to define useful methods for other classes.
     */
    private DialogUtils() {

    }

    /**
     * This method is used to create a basic dialog.
     * 
     * @param parent the parent of the new dialog
     * @param title the title of the new dialog
     * 
     * @return the new dialog
     */
    public static JDialog createDialog(final Component parent, final String title) {

        InputValidator.checkArgument(parent, p -> true, "Parent");
        InputValidator.checkArgument(title, p -> !p.isBlank(), "Title");

        final JDialog dialog = new JDialog(
            SwingUtilities.getWindowAncestor(parent),
            title,
            Dialog.ModalityType.APPLICATION_MODAL
        );

        dialog.setLayout(new BorderLayout(DIALOG_PADDING, DIALOG_PADDING));

        return dialog;
    }

    /**
     * This method is used to create a panel inside the dialog.
     * 
     * @return the new panel
     */
    public static JPanel createFormPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(
            PANEL_PADDING,
            PANEL_PADDING,
            PANEL_PADDING,
            PANEL_PADDING
        ));
        return panel;
    }

    /**
     * This method is used to add a new field to the dialog.
     * 
     * @param panel the panel in which we want to add the new field
     * @param label the label for the field
     * @param field the field we want to add
     * @param row the row in which the field needs to go
     */
    public static void addField(
        final JPanel panel,
        final String label,
        final JComponent field,
        final int row
    ) {

        InputValidator.checkArgument(panel, p -> true, "Panel");
        InputValidator.checkArgument(label, p -> !p.isBlank(), "Label");
        InputValidator.checkArgument(field, p -> true, FIELD);
        if (row < 0) {
            throw new IllegalArgumentException("Row cannot be lower than 0");
        }

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(FIELD_INSET, FIELD_INSET, FIELD_INSET, FIELD_INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    /**
     * This method is used to construct a specific textField for Strings and numbers.
     * 
     * @return the new textField
     */
    public static JTextField createTextField() {
        return new JTextField(TEXT_FIELD_SIZE);
    }

    /**
     * This method is used to construct a specific textField for dates.
     * 
     * @return the new textField
     */
    public static JTextField createDateField() {
        return new JTextField(LocalDate.now().format(DATE_FORMATTER), DATE_FIELD_SIZE);
    }

    /**
     * This method is used to extract the BigDecimal from the input String.
     * 
     * @param field the string to parse.
     * 
     * @return the BigDecimal inserted as input.
     */
    public static BigDecimal getBigDecimalValue(final String field) {

        InputValidator.checkArgument(field, p -> !p.isBlank(), FIELD);

        return new BigDecimal(
            field.trim().replace(',', '.')
        );
    }

    /**
     * This method is used to extract the LocalDate from the input String.
     * 
     * @param field the string to parse.
     * 
     * @return the LocalDate inserted as input.
     */
    public static LocalDate getLocalDateValue(final String field) {

        InputValidator.checkArgument(field, p -> !p.isBlank(), FIELD);

        return LocalDate.parse(
            field.trim(),
            DATE_FORMATTER
        );
    }

    /**
     * This method is used to get a DefaultListCell renderer, which is the renderer necessary to show
     * the Prepaidcards inside the JComboBox.
     * 
     * @return the newly created renderer for the PrepaidCards.
     */
    public static DefaultListCellRenderer getCardsListRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus
            ) {

                super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );

                if (value instanceof PrepaidCard card) {
                    setText(card.getCardNumber());
                }

                return this;
            }
        };
    }

    /**
     * This method is used to create the panel for the confirm and cancel button.
     * 
     * @param confirmButton the confirm button
     * @param cancelButton the cancel button
     * 
     * @return the new panel
     */
    public static JPanel createDialogButtons(
        final JButton confirmButton,
        final JButton cancelButton) {

        final JPanel buttonsPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        return buttonsPanel;
    }
}
