package com.labuts.finalproject.service;

import java.util.ResourceBundle;

/**
 * ConfigurationManager class is used to get configuration properties from resources
 */
public class ConfigurationManager {
    /**
     * resource bundle
     */
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");

    /**
     * Private constructor
     */
    private ConfigurationManager() { }

    /**
     * Find property in resources
     * @param key string key
     * @return needed property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
