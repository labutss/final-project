package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create ClientViewAllAccommodationsCommand from Command
 */
public class ClientViewAllAccommodationsCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String CLIENT_MAIN_PAGE = "path.page.client.main";

    private static final String AVAILABLE_ACCOMMODATIONS_ATTRIBUTE = "availableAccommodations";

    private BaseService baseService;

    /**
     * Constructor to initialize
     */
    public ClientViewAllAccommodationsCommand(){
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            try {
                List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
                request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);

                page = ConfigurationManager.getProperty(CLIENT_MAIN_PAGE);
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
