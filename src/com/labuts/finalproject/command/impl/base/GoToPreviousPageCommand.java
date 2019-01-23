package com.labuts.finalproject.command.impl.base;

import com.labuts.finalproject.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Create GoToPreviousPageCommand from Command
 */
public class GoToPreviousPageCommand extends Command {
    private static final String CURRENT_PAGE_ATTRIBUTE = "currentPage";
    private static final String ATTRIBUTES = "attributes";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String page = (String)session.getAttribute(CURRENT_PAGE_ATTRIBUTE);

        Map<String, Object> attributes = (Map<String, Object>)session.getAttribute(ATTRIBUTES);
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        return page;
    }

    @Override
    public void storeData(HttpServletRequest request, String page) {}
}
