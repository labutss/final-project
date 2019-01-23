package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Create GoToEditAccommodationCommand from Command
 */
public class GoToEditAccommodationCommand extends Command {
    private static final String ACCOMMODATION_PAGE = "path.page.accommodation";
    private static final String ERROR_PAGE = "path.page.error";

    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String COMMAND_ATTRIBUTE = "command";
    private static final String COMMAND_VALUE = "edit-accommodation-command";
    private static final String ACCOMMODATION_ATTRIBUTE = "accommodation";

    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public GoToEditAccommodationCommand(){
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ERROR_PAGE);
        String accommodationId = request.getParameter(ACCOMMODATION_ID_PARAMETER);
        try {
            Optional<Accommodation> optionalAccommodation = adminService.findAccommodation(accommodationId);
            if(optionalAccommodation.isPresent()){
                Accommodation accommodation = optionalAccommodation.get();
                request.setAttribute(ACCOMMODATION_ATTRIBUTE, accommodation);
                page = ConfigurationManager.getProperty(ACCOMMODATION_PAGE);
                request.setAttribute(COMMAND_ATTRIBUTE, COMMAND_VALUE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
