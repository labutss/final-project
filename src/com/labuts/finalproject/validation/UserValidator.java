package com.labuts.finalproject.validation;

/**
 * UserValidator is used to check user's parts validity
 */
public class UserValidator {
    /**
     * regular expression for username
     */
    private static final String USERNAME_REGEX = "[A-Za-z0-9]{4,20}";
    /**
     * regular expression for password
     */
    private static final String PASSWORD_REGEX = "[A-Za-z0-9]{4,15}";

    /**
     * Check if username is valid
     * @param username username to check
     * @return if username valid or not
     */
    public boolean isValidUsername(String username){
        return Validator.isValid(USERNAME_REGEX, username);
    }

    /**
     * Check if password is valid
     * @param password password to check
     * @return if password valid or not
     */
    public boolean isValidPassword(String password){
        return Validator.isValid(PASSWORD_REGEX, password);
    }
}
