package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create AdminViewNewRequestsCommand from Command
 */
public class AdminViewNewRequestsCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_REQUESTS_PAGE = "path.page.admin.requests";

    private static final String REQUESTS_ATTRIBUTE = "requests";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String NEW_REQUESTS = "new";

    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public AdminViewNewRequestsCommand(){
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            try {
                List<Request> requests = adminService.findNewRequests();
                request.setAttribute(REQUESTS_ATTRIBUTE, requests);
                request.setAttribute(MESSAGE_ATTRIBUTE, NEW_REQUESTS);
                page = ConfigurationManager.getProperty(ADMIN_REQUESTS_PAGE);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }

        return page;
    }
}
