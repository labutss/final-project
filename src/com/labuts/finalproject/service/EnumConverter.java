package com.labuts.finalproject.service;

/**
 * EnumConverter class is used to convert string
 */
public class EnumConverter {
    /**
     * Private constructor
     */
    private EnumConverter(){}

    /**
     * Converts string
     * @param string string to convert
     * @return converted string
     */
    public static String convert(String string){
        return string.toUpperCase().replaceAll("-", "_");
    }
}
