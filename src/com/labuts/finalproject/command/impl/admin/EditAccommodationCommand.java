package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import com.labuts.finalproject.validation.AccommodationValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create EditAccommodationCommand from Command
 */
public class EditAccommodationCommand extends Command {
    private static final String ACCOMMODATION_PAGE = "path.page.accommodation";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_MAIN_PAGE = "path.page.admin.main";

    private static final String ACCOMMODATION_ID_PARAMETER = "accommodationId";
    private static final String ACCOMMODATION_NAME_PARAMETER = "accommodationName";
    private static final String ACCOMMODATION_DESCRIPTION_PARAMETER = "accommodationDescription";
    private static final String ACCOMMODATION_COST_PARAMETER = "accommodationCost";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ACCOMMODATIONS_ATTRIBUTE = "accommodations";
    private static final String COMMAND_ATTRIBUTE = "command";
    private static final String COMMAND_VALUE = "edit-accommodation-command";
    private static final String ACCOMMODATION_ATTRIBUTE = "accommodation";
    private static final String WRONG_ACCOMMODATION_DATA = "wrong accommodation data";

    private BaseService baseService;
    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public EditAccommodationCommand(){
        baseService = new BaseService();
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            String accommodationId = request.getParameter(ACCOMMODATION_ID_PARAMETER);
            String accommodationName = request.getParameter(ACCOMMODATION_NAME_PARAMETER);
            String accommodationDescription = request.getParameter(ACCOMMODATION_DESCRIPTION_PARAMETER);
            String accommodationCost = request.getParameter(ACCOMMODATION_COST_PARAMETER);

            AccommodationValidator validator = new AccommodationValidator();
            if(validator.isValidAccommodationName(accommodationName) && validator.isValidAccommodationDescription(accommodationDescription)
                    && validator.isValidAccommodationCost(accommodationCost)){
                try {
                    adminService.editAccommodation(accommodationId, accommodationName, accommodationDescription, accommodationCost);

                    List<Accommodation> accommodations = baseService.findAllAccommodations();
                    request.setAttribute(ACCOMMODATIONS_ATTRIBUTE, accommodations);
                    page = ConfigurationManager.getProperty(ADMIN_MAIN_PAGE);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                    page = ConfigurationManager.getProperty(ERROR_PAGE);
                    e.printStackTrace();
                }
            }else {
                request.setAttribute(MESSAGE_ATTRIBUTE, WRONG_ACCOMMODATION_DATA);
                request.setAttribute(COMMAND_ATTRIBUTE, COMMAND_VALUE);
                Accommodation accommodation = new Accommodation(Integer.parseInt(accommodationId));
                if(validator.isValidAccommodationName(accommodationName)){
                    accommodation.setAccommodationName(accommodationName);
                }
                if(validator.isValidAccommodationDescription(accommodationDescription)){
                    accommodation.setAccommodationDescription(accommodationDescription);
                }
                if(validator.isValidAccommodationCost(accommodationCost)){
                    accommodation.setAccommodationCost(Double.parseDouble(accommodationCost));
                }
                request.setAttribute(ACCOMMODATION_ATTRIBUTE, accommodation);
                page = ConfigurationManager.getProperty(ACCOMMODATION_PAGE);
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
