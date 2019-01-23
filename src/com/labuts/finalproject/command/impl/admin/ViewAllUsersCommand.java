package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.AdminService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create ViewAllUsersCommand from Command
 */
public class ViewAllUsersCommand extends Command {
    private static final String ALL_USERS_PAGE = "path.page.all.users";
    private static final String ERROR_PAGE = "path.page.error";

    private static final String ALL_USERS_ATTRIBUTE = "users";

    private AdminService adminService;

    /**
     * Constructor to initialize
     */
    public ViewAllUsersCommand(){
        adminService = new AdminService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(ERROR_PAGE);
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == ADMIN_STATUS) {
            try{
                List<User> allUsers = adminService.findAllUsers();
                request.setAttribute(ALL_USERS_ATTRIBUTE, allUsers);
                page = ConfigurationManager.getProperty(ALL_USERS_PAGE);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return page;
    }
}
