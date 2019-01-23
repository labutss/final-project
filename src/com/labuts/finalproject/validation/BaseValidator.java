package com.labuts.finalproject.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BaseValidator class is used to check validity
 */
public class BaseValidator {
    /**
     * Regular expression for amount of money
     */
    private static final String CASH_REGEX = "^([1-9]{1}[0-9]{0,5}(\\.[0-9]{0,2})?|0(\\.[1-9]{1}([0-9]{1})?)|0(\\.0[1-9]{1}))$";
    /**
     * Regular expression for date
     */
    private static final String DATE_REGEX = "(((0[13578]|10|12)([-./])(0[1-9]|[12][0-9]|3[01])([-./])(\\d{4}))|((0[469]|11)([-./])([0][1-9]|[12][0-9]|30)([-./])(\\d{4}))|((2)([-./])(0[1-9]|1[0-9]|2[0-8])([-./])(\\d{4}))|((2)(\\.|-|\\/)(29)([-./])([02468][048]00))|((2)([-./])(29)([-./])([13579][26]00))|((2)([-./])(29)([-./])([0-9][0-9][0][48]))|((2)([-./])(29)([-./])([0-9][0-9][2468][048]))|((2)([-./])(29)([-./])([0-9][0-9][13579][26])))";
    /**
     * Date formatter
     */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Check if valid amount of money
     * @param amountOfMoney amount of money to check
     * @return if amount of money is valid or not
     */
    public boolean isValidAmountOfMoney(String amountOfMoney){
        return Validator.isValid(CASH_REGEX, amountOfMoney);
    }

    /**
     * Check if date is valid or not
     * @param date date to check
     * @return if date is valid
     */
    public boolean isValidDate(String date){
        return Validator.isValid(DATE_REGEX, date);
    }

    /**
     * Check if accommodation date is valid or not
     * @param date date to check
     * @return if accommodation date is valid
     */
    public boolean isValidAccommodationDate(String date){
        boolean result = false;
        try {
            Date accommodationDate = DATE_FORMATTER.parse(date);
            Date currentDate = new Date();
            result = isValidDate(date) && (accommodationDate.compareTo(currentDate) >= 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
