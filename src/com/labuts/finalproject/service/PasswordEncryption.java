package com.labuts.finalproject.service;

import java.math.BigInteger;
import java.util.Base64;

/**
 * PasswordEncryption is used to encrypt passwords
 */
public class PasswordEncryption {
    /**
     * Private constructor
     */
    private PasswordEncryption(){}

    /**
     * Encrypt password
     * @param password password to encrypt
     * @return encrypted password
     */
    public static String encryptPassword(String password){
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode(password.getBytes());
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        return bigInt.toString(16);
    }
}
