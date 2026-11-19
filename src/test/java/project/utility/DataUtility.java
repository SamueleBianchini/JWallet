package project.utility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will be a utility class to get the data useful for the parameters for the tests.
 */
public final class DataUtility {

    public static final String BLANK = "    ";
    public static final String IBAN = "IT00A0000000000000000000000";
    public static final String OTHER_IBAN = "IT00A0000000000000000000001";
    public static final String NAME = "Nathan Drake";
    public static final String CAUSE = "Cruise vacation";
    public static final String CARD_NUMBER = "0000000000000000";
    public static final String CVV = "000";
    public static final String PIN = "00000";
    public static final String DATE_TEXT = "01/01/2026";
    public static final String EXPIRY_DATE_TEXT = "01/01/2036";
    public static final LocalDate DATE = LocalDate.of(2026, 1, 1);
    public static final LocalDate EXPIRY_DATE = LocalDate.of(2036, 1, 1);
    public static final BigDecimal AMOUNT = new BigDecimal("1000.00");
    public static final BigDecimal NEG_AMOUNT = new BigDecimal("-1000.00");

    /**
     * This constructor is private because this is a utility class.
     */
    private DataUtility() {

    }

    /**
     * This method is used to return a copy of a list but with a specific element removed from it. 
     * 
     * @param <T> the type of the list we are using
     * @param list the list which we are working on
     * @param element the element to remove
     * @return the new list
     */
    public static <T> List<T> removeElement(final List<T> list, final T element) {
        InputValidator.checkArgument(list, p -> !p.isEmpty(), "List");
        InputValidator.checkArgument(element, p -> list.contains(element), "Element ");
        final List<T> result = new ArrayList<>(list);
        result.remove(element);
        return result;
    }
}
