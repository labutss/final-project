package com.labuts.finalproject.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class Command is used to create different types of commands
 */
public abstract class Command {
    /**
     * current page parameter
     */
    private static final String CURRENT_PAGE = "currentPage";
    /**
     * attributes parameter
     */
    private static final String ATTRIBUTES = "attributes";

    /**
     * role parameter
     */
    protected static final String ROLE_PARAMETER = "role";
    /**
     * client status
     */
    protected static final int CLIENT_STATUS = 1;
    /**
     * admin status
     */
    protected static final int ADMIN_STATUS = 0;

    /**
     * logger
     */
    protected static final Logger logger = LogManager.getLogger();

    /**
     * execute command
     * @param request httpServletRequest
     * @return page where to go next
     */
    public abstract String execute(HttpServletRequest request);

    /**
     * store data in session
     * @param request httpServletRequest
     * @param page current page
     */
    public void storeData(HttpServletRequest request, String page){
        HttpSession session = request.getSession(true);
        session.setAttribute(CURRENT_PAGE, page);
        Map<String, Object> requestAttributes = getRequestAttributes(request);
        session.setAttribute(ATTRIBUTES, requestAttributes);
    }

    /**
     * get request attributes
     * @param request httpServletRequest
     * @return map of attributes
     */
    private Map<String, Object> getRequestAttributes(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> attributesEnumeration = request.getAttributeNames();
        while (attributesEnumeration.hasMoreElements()){
            String attribute = attributesEnumeration.nextElement();
            Object value = request.getAttribute(attribute);
            map.put(attribute, value);
        }
        return map;
    }

    /**
     * compare Command objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
