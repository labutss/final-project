package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.ClientService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create ClientViewAllRequestsCommand from Command
 */
public class ClientViewAllRequestsCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String CLIENT_REQUESTS_PAGE = "path.page.client.requests";

    private static final String USER_ID_PARAMETER = "userId";
    private static final String REQUESTS_ATTRIBUTE = "requests";

    private ClientService clientService;

    /**
     * Constructor to initialize
     */
    public ClientViewAllRequestsCommand(){
        clientService = new ClientService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            try {
                int userId = (int)session.getAttribute(USER_ID_PARAMETER);
                List<Request> requests = clientService.findUsersRequests(userId);
                request.setAttribute(REQUESTS_ATTRIBUTE, requests);
                page = ConfigurationManager.getProperty(CLIENT_REQUESTS_PAGE);
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
