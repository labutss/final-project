package com.labuts.finalproject.validation;

/**
 * AccommodationValidator class is used to check accommodation's parts validity
 */
public class AccommodationValidator {
    /**
     * regular expression for accommodation name
     */
    private static final String ACCOMMODATION_NAME_REGEX = "[A-Za-z-\\s]{4,20}";
    /**
     * regular expression for accommodation description
     */
    private static final String ACCOMMODATION_DESCRIPTION_REGEX = "[\\d\\w\\s-,.!:;()]{10,100}";
    /**
     * regular expression for accommodation cost
     */
    private static final String ACCOMMODATION_COST_REGEX = "^([1-9]{1}[0-9]{0,5}(\\.[0-9]{0,2})?|0(\\.[1-9]{1}([0-9]{1})?)|0(\\.0[1-9]{1}))$";

    /**
     * Check if accommodation name is valid
     * @param accommodationName name to check
     * @return if name valid or not
     */
    public boolean isValidAccommodationName(String accommodationName){
        return Validator.isValid(ACCOMMODATION_NAME_REGEX, accommodationName);
    }

    /**
     * Check if accommodation description is valid
     * @param accommodationDescription description to check
     * @return if description valid or not
     */
    public boolean isValidAccommodationDescription(String accommodationDescription){
        return Validator.isValid(ACCOMMODATION_DESCRIPTION_REGEX, accommodationDescription);
    }

    /**
     * Check if accommodation cost is valid
     * @param accommodationCost cost to check
     * @return if cost valid or not
     */
    public boolean isValidAccommodationCost(String accommodationCost){
        return Validator.isValid(ACCOMMODATION_COST_REGEX, accommodationCost);
    }
}
