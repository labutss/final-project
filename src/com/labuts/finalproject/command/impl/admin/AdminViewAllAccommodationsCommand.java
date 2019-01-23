package com.labuts.finalproject.command.impl.admin;

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
 * Create AdminViewAllAccommodationsCommand from Command
 */
public class AdminViewAllAccommodationsCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_MAIN_PAGE = "path.page.admin.main";

    private static final String ACCOMMODATIONS_ATTRIBUTE = "accommodations";

    private BaseService baseService;

    /**
     * Constructor to initialize
     */
    public AdminViewAllAccommodationsCommand(){
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            try {
                List<Accommodation> accommodations = baseService.findAllAccommodations();
                request.setAttribute(ACCOMMODATIONS_ATTRIBUTE, accommodations);
                page = ConfigurationManager.getProperty(ADMIN_MAIN_PAGE);
            }catch (ServiceException e){
                logger.log(Level.ERROR, e);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }

        return page;
    }
}
