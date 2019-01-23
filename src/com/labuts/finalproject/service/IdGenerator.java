package com.labuts.finalproject.service;

import java.util.UUID;

/**
 * IdGenerator class is used to generate ids for objects
 */
public class IdGenerator {
    /**
     * Private constructor
     */
    private IdGenerator(){}

    /**
     * Get id
     * @return generated id
     */
    public static int getId(){
        UUID idOne = UUID.randomUUID();
        String str = "" + idOne;
        int uid = str.hashCode();
        String filterStr = "" + uid;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }
}
