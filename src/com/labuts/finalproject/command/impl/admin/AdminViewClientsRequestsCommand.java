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
 * Create AdminViewClientsRequestsCommand from Command
 */
public class AdminViewClientsRequestsCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_REQUESTS_PAGE = "path.page.admin.requests";

    private static final String USER_ID_PARAMETER = "userId";
    private static final String USERNAME_PARAMETER = "username";
    private static final String REQUESTS_PARAMETER = "requests";
    private static final String MESSAGE_PARAMETER = "message";

    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public AdminViewClientsRequestsCommand(){
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            String userId = request.getParameter(USER_ID_PARAMETER);
            String username = request.getParameter(USERNAME_PARAMETER);
            try {
                List<Request> requests = adminService.findClientsRequests(userId);
                request.setAttribute(REQUESTS_PARAMETER, requests);
                request.setAttribute(MESSAGE_PARAMETER, username);
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
