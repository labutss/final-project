package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.ConfigurationManager;
import com.labuts.finalproject.validation.BaseValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Create SetRequestBillAndDateCommand from Command
 */
public class SetRequestBillAndDateCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_REQUESTS_PAGE = "path.page.admin.requests";

    private static final String REQUEST_ID_PARAMETER = "requestId";
    private static final String REQUEST_MESSAGE_PARAMETER = "requestMessage";
    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String DATE_PARAMETER = "date";

    private static final String REQUESTS_ATTRIBUTE = "requests";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String WRONG_DATE_MESSAGE_ATTRIBUTE = "wrongDateMessage";
    private static final String ALL_REQUESTS = "all";
    private static final String NEW_REQUESTS = "new";
    private static final String WRONG_DATE = "wrong date";
    private static final String ATTRIBUTES = "attributes";

    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public SetRequestBillAndDateCommand(){
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            String date = request.getParameter(DATE_PARAMETER);
            BaseValidator baseValidator = new BaseValidator();
            if(date != null && baseValidator.isValidAccommodationDate(date)){
                String requestId = request.getParameter(REQUEST_ID_PARAMETER);
                String[] accommodations = request.getParameterValues(ACCOMMODATION_ID_PARAMETER);
                if(accommodations != null && accommodations.length != 0){
                    try {
                        adminService.setRequestBillAndDate(requestId, accommodations, date);
                        page = ConfigurationManager.getProperty(ADMIN_REQUESTS_PAGE);

                        String message = request.getParameter(REQUEST_MESSAGE_PARAMETER);
                        List<Request> requests = defineRequestsType(message);
                        request.setAttribute(REQUESTS_ATTRIBUTE, requests);
                        request.setAttribute(MESSAGE_ATTRIBUTE, message);
                    }catch (ServiceException e){
                        logger.log(Level.ERROR, e);
                        page = ConfigurationManager.getProperty(ERROR_PAGE);
                    }
                }else {
                    page = ConfigurationManager.getProperty(ERROR_PAGE);
                }
            }else {
                page = ConfigurationManager.getProperty(ADMIN_REQUESTS_PAGE);
                request.setAttribute(WRONG_DATE_MESSAGE_ATTRIBUTE, WRONG_DATE);
                Map<String, Object> attributes = (Map<String, Object>)session.getAttribute(ATTRIBUTES);
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }

    /**
     * define requests type
     * @param message message
     * @return list of requests
     * @throws ServiceException when something goes wrong
     */
    private List<Request> defineRequestsType(String message) throws ServiceException{
        List<Request> requests;
        switch (message){
            case ALL_REQUESTS:
                requests = adminService.findAllRequests();
                break;
            case NEW_REQUESTS:
                requests = adminService.findNewRequests();
                break;
            default:
                requests = adminService.findClientsRequestsByName(message);
                break;
        }
        return requests;
    }
}
