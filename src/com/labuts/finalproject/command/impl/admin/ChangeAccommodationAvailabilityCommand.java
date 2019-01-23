package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create ChangeAccommodationAvailabilityCommand from Command
 */
public class ChangeAccommodationAvailabilityCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_MAIN_PAGE = "path.page.admin.main";

    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String ACCOMMODATION_AVAILABLE_PARAMETER = "newAvailable";
    private static final String ACCOMMODATIONS_ATTRIBUTE = "accommodations";

    private AdminService adminService;
    private BaseService baseService;

    /**
     * Constructor to initialize
     */
    public ChangeAccommodationAvailabilityCommand(){
        adminService = new AdminService();
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ERROR_PAGE);
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            String accommodationId = request.getParameter(ACCOMMODATION_ID_PARAMETER);
            String accommodationAvailable = request.getParameter(ACCOMMODATION_AVAILABLE_PARAMETER);
            try {
                adminService.changeAccommodationAvailability(accommodationId, accommodationAvailable);
                page = ConfigurationManager.getProperty(ADMIN_MAIN_PAGE);

                List<Accommodation> accommodations = baseService.findAllAccommodations();
                request.setAttribute(ACCOMMODATIONS_ATTRIBUTE, accommodations);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return page;
    }
}
