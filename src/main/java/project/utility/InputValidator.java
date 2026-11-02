package project.utility;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class is a utility class, used to validate every type of input by using
 * the method checkArgument to check if it is null and a specific predicate.
 * ImputValidator
 */
public final class InputValidator {

    private static final String INPUT_ERROR_STRING = " is null or not valid.";

    /**
     * This constructor is private because this is a utility class, and so it should not be constructed.
     */
    private InputValidator() {

    }

    /**
     * This is the utility method used to validate any input by checking if it is null and also
     * the predicate input as a parameter.
     * 
     * @param <T> the type of the input we need to validate.
     * @param obj the object we want to validate.
     * @param predicate the predicate that the object needs to respect to be validated.
     * @param field the name of the field of the input, used to personalize the error message.
     */
    public static <T> void checkArgument(final T obj, final Predicate<? super T> predicate, final String field) {
        if (Objects.isNull(obj) || !predicate.test(obj)) {
            throw new IllegalArgumentException(field.concat(INPUT_ERROR_STRING));
        }
    }
}
