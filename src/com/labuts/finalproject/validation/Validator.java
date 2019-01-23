package com.labuts.finalproject.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class is used to check if information is valid
 */
class Validator {
    /**
     * Check if string matches regular expression
     * @param REGULAR_EXPRESSION regular expression
     * @param stringToCheck string to check
     * @return if string matches of not
     */
    static boolean isValid(final String REGULAR_EXPRESSION, String stringToCheck){
        Pattern pattern = Pattern.compile(REGULAR_EXPRESSION);
        Matcher matcher = pattern.matcher(stringToCheck);

        return matcher.matches();
    }
}
