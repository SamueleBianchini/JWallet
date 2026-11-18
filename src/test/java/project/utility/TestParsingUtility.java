package project.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        /* Tests for empty and blank text */
        assertThrows(IllegalArgumentException.class, () -> DialogUtils.getBigDecimalValue(null));
        assertThrows(IllegalArgumentException.class, () -> DialogUtils.getBigDecimalValue("      "));
        /* Tests for strings with letters */
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue("Hello world"));
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue("12/12/2026 Hello world"));
        /* Tests for correct parsing */
        assertEquals(BigDecimal.ONE, DialogUtils.getBigDecimalValue("1"));
        assertEquals(BigDecimal.ONE.compareTo(DialogUtils.getBigDecimalValue("1.00").stripTrailingZeros()), 0);
        assertEquals(new BigDecimal("1000").compareTo(DialogUtils.getBigDecimalValue("1000.00")), 0);
        assertEquals(new BigDecimal(EXAMPLE_NUMBER).compareTo(DialogUtils.getBigDecimalValue(EXAMPLE_NUMBER)), 0);
        assertNotEquals(new BigDecimal("1234.00").compareTo(DialogUtils.getBigDecimalValue(EXAMPLE_NUMBER)), 0);
        /* Tests for numbers with , instead of . */
        assertEquals(new BigDecimal(EXAMPLE_NUMBER).compareTo(DialogUtils.getBigDecimalValue("1234,50")), 0);
        /* Tests for impossible numbers */
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue("1234.50.50"));
        assertThrows(NumberFormatException.class, () -> DialogUtils.getBigDecimalValue("1234,50,50"));
    }

    /**
     * This method is a test for the method getLocalDate of the class DialogUtils,
     * which is supposed to parse a String to get a LocalDate.
     */
    @Test
    void testGetLocalDate() {
        /* Tests for empty and blank text */
        assertThrows(IllegalArgumentException.class, () -> DialogUtils.getLocalDateValue(null));
        assertThrows(IllegalArgumentException.class, () -> DialogUtils.getLocalDateValue("      "));
        /* Tests for strings with letters */
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue("Hello world"));
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue("12/12/2026 Hello world"));
        /* Tests for correct parsing */
        assertEquals(LocalDate.of(YEAR, END_OF_YEAR, END_OF_YEAR), DialogUtils.getLocalDateValue("12/12/2026"));
        assertEquals(LocalDate.of(YEAR_WITH_ZEROS, 1, 1), DialogUtils.getLocalDateValue("01/01/2000"));
        /* Tests for impossible dates */
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue("32/01/2026"));
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue("31/06/2026"));
        assertThrows(DateTimeParseException.class, () -> DialogUtils.getLocalDateValue("29/02/2026"));
        /* Tests for leap year */
        assertEquals(LocalDate.of(LEAP_YEAR, 2, LEAP_YEAR_DATE), DialogUtils.getLocalDateValue("29/02/2024"));
    }
}
