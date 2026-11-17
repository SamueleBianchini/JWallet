package project.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

/**
 * This class is the test for the parsing method inside the DialogUtils class, which need a specific
 * test class because they are an important aspect of the interaction with the user.
 */
class TestParsingUtility {

    private static final int YEAR = 2026;
    private static final int END_OF_YEAR = 12;
    private static final int YEAR_WITH_ZEROS = 2000;
    private static final int LEAP_YEAR = 2024;
    private static final int LEAP_YEAR_DATE = 29;
    private static final String EXAMPLE_NUMBER = "1234.50";

    /**
     * This method is a test for the method getBigDecimal of the class DialogUtils,
     * which is supposed to parse a String to get a BigDecimal.
     */
    @Test
    void testGetBigDecimal() {
        final JTextField field = DialogUtils.createTextField();
        /* Tests for empty and blank text */
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
        field.setText("      ");
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
        /* Tests for strings with letters */
        field.setText("Hello world");
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
        field.setText("1234.12 Hello world");
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
        /* Tests for correct parsing */
        field.setText("1");
        assertEquals(BigDecimal.ONE, DialogUtils.getBigDecimalValue(field));
        field.setText("1.00");
        assertEquals(BigDecimal.ONE.compareTo(DialogUtils.getBigDecimalValue(field).stripTrailingZeros()), 0);
        field.setText("1000.00");
        assertEquals(new BigDecimal("1000").compareTo(DialogUtils.getBigDecimalValue(field)), 0);
        field.setText(EXAMPLE_NUMBER);
        assertEquals(new BigDecimal(EXAMPLE_NUMBER).compareTo(DialogUtils.getBigDecimalValue(field)), 0);
        assertNotEquals(new BigDecimal("1234.00").compareTo(DialogUtils.getBigDecimalValue(field)), 0);
        /* Tests for numbers with , instead of . */
        field.setText("1234,50");
        assertEquals(new BigDecimal(EXAMPLE_NUMBER).compareTo(DialogUtils.getBigDecimalValue(field)), 0);
        /* Tests for impossible numbers */
        field.setText("1234.50.50");
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
        field.setText("1234,50,50");
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue(field));
    }

    /**
     * This method is a test for the method getLocalDate of the class DialogUtils,
     * which is supposed to parse a String to get a LocalDate.
     */
    @Test
    void testGetLocalDate() {
        final JTextField field = DialogUtils.createTextField();
        /* Tests for empty and blank text */
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        field.setText("      ");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        /* Tests for strings with letters */
        field.setText("Hello world");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        field.setText("12/12/2026 Hello world");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        /* Tests for correct parsing */
        field.setText("12/12/2026");
        assertEquals(LocalDate.of(YEAR, END_OF_YEAR, END_OF_YEAR), DialogUtils.getLocalDateValue(field));
        field.setText("01/01/2000");
        assertEquals(LocalDate.of(YEAR_WITH_ZEROS, 1, 1), DialogUtils.getLocalDateValue(field));
        /* Tests for impossible dates */
        field.setText("32/01/2026");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        field.setText("31/06/2026");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        field.setText("29/02/2026");
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue(field));
        /* Tests for leap year */
        field.setText("29/02/2024");
        assertEquals(LocalDate.of(LEAP_YEAR, 2, LEAP_YEAR_DATE), DialogUtils.getLocalDateValue(field));
    }
}
