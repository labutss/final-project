package com.labuts.finalproject.service;

/**
 * XssAttackProtection class is used to protect application from xss attacks
 */
public class XssAttackProtection {
    /**
     * String to define script
     */
    private static final String SCRIPT = "</?script>";

    /**
     * Private constructor
     */
    private XssAttackProtection(){}

    /**
     * Remove all scripts from string
     * @param string string to modify
     * @return string without scripts
     */
    public static String preventAttack(String string){
        return string.replaceAll(SCRIPT,"");
    }
}
