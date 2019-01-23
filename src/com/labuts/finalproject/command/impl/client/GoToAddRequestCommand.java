package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create GoToAddRequestCommand from Command
 */
public class GoToAddRequestCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String AVAILABLE_ACCOMMODATIONS = "availableAccommodations";
    private static final String REQUEST_PAGE = "path.page.request";

    private final BaseService baseService;

    /**
     * Constructor to initialize
     */
    public GoToAddRequestCommand(){
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ERROR_PAGE);
        try {
            List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
            request.setAttribute(AVAILABLE_ACCOMMODATIONS, availableAccommodations);
            page = ConfigurationManager.getProperty(REQUEST_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
