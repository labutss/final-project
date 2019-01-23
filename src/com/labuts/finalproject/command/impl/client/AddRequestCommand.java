package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ClientService;
import com.labuts.finalproject.service.ConfigurationManager;
import com.labuts.finalproject.validation.BaseValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create AddRequestCommand from Command
 */
public class AddRequestCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String REQUEST_PAGE = "path.page.request";
    private static final String CLIENT_MAIN_PAGE = "path.page.client.main";

    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String DATE_PARAMETER = "date";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String AVAILABLE_ACCOMMODATIONS_ATTRIBUTE = "availableAccommodations";

    private static final String NO_ACCOMMODATIONS_MESSAGE = "no accommodations";
    private static final String INVALID_DATE_MESSAGE = "invalid date";

    private ClientService clientService;
    private BaseService baseService;

    /**
     * Constructor to initialize
     */
    public AddRequestCommand(){
        clientService = new ClientService();
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            String date = request.getParameter(DATE_PARAMETER);
            String[] accommodationsId = request.getParameterValues(ACCOMMODATION_ID_PARAMETER);

            BaseValidator baseValidator = new BaseValidator();
            if(baseValidator.isValidAccommodationDate(date)){
                if(accommodationsId != null && accommodationsId.length != 0){
                    try{
                        int userId = (int)session.getAttribute(USER_ID_PARAMETER);
                        clientService.addRequest(userId, date, accommodationsId);
                        page = ConfigurationManager.getProperty(CLIENT_MAIN_PAGE);

                        List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
                        request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);
                    } catch (ServiceException e) {
                        logger.log(Level.ERROR, e);
                        page = ConfigurationManager.getProperty(ERROR_PAGE);
                    }
                }else {
                    request.setAttribute(DATE_PARAMETER, date);
                    request.setAttribute(MESSAGE_ATTRIBUTE, NO_ACCOMMODATIONS_MESSAGE);
                    try {
                        List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
                        request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);
                    } catch (ServiceException e) {
                        logger.log(Level.ERROR, e);
                    }
                    page = ConfigurationManager.getProperty(REQUEST_PAGE);
                }
            }else {
                try {
                    List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
                    request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                }
                request.setAttribute(MESSAGE_ATTRIBUTE, INVALID_DATE_MESSAGE);
                page = ConfigurationManager.getProperty(REQUEST_PAGE);
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
