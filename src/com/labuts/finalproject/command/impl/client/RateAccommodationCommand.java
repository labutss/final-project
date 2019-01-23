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
 * Create RateAccommodationCommand from Command
 */
public class RateAccommodationCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String CLIENT_REQUESTS_PAGE = "path.page.client.requests";

    private static final String REQUEST_ID_PARAMETER = "requestId";
    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String MARK_PARAMETER = "mark";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String REQUESTS_ATTRIBUTE = "requests";

    private ClientService clientService;

    /**
     * Constructor to initialize
     */
    public RateAccommodationCommand(){
        clientService = new ClientService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ERROR_PAGE);
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            String accommodationId = request.getParameter(ACCOMMODATION_ID_PARAMETER);
            String requestId = request.getParameter(REQUEST_ID_PARAMETER);
            String mark = request.getParameter(MARK_PARAMETER);
            if(accommodationId != null && requestId != null && mark != null){
                try {
                    clientService.rateAccommodation(accommodationId, requestId, mark);

                    int userId = (int)session.getAttribute(USER_ID_PARAMETER);
                    List<Request> requests = clientService.findUsersRequests(userId);
                    request.setAttribute(REQUESTS_ATTRIBUTE, requests);

                    page = ConfigurationManager.getProperty(CLIENT_REQUESTS_PAGE);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                }
            }
        }
        return page;
    }
}
