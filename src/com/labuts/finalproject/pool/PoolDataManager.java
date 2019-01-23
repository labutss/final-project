package com.labuts.finalproject.pool;

import java.util.ResourceBundle;

/**
 * PoolDataManager class is used to get pool properties from resources
 */
class PoolDataManager {
    /**
     * resource bundle
     */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.pool");
    /**
     * username key
     */
    private static final String USERNAME_KEY = "username";
    /**
     * password key
     */
    private static final String PASSWORD_KEY = "password";
    /**
     * connection url key
     */
    private static final String CONNECTION_URL_KEY = "connection.url";
    /**
     * classname key
     */
    private static final String CLASSNAME_KEY = "classname";

    /**
     * Private constructor
     */
    private PoolDataManager() { }

    /**
     * Get username from properties
     * @return username
     */
    static String getUsername(){ return resourceBundle.getString(USERNAME_KEY);}

    /**
     * Get password from properties
     * @return password
     */
    static String getPassword(){ return resourceBundle.getString(PASSWORD_KEY);}

    /**
     * Get connection url from properties
     * @return connection url
     */
    static String getConnectionUrl(){ return resourceBundle.getString(CONNECTION_URL_KEY);}

    /**
     * Get classname from properties
     * @return classname
     */
    static String getClassName(){ return resourceBundle.getString(CLASSNAME_KEY);}
}
