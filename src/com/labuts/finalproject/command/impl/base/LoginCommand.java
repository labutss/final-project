package com.labuts.finalproject.command.impl.base;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Client;
import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Create LoginCommand from Command
 */
public class LoginCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String LOGIN_PAGE = "path.page.login";
    private static final String CLIENT_MAIN_PAGE = "path.page.client.main";
    private static final String ADMIN_MAIN_PAGE = "path.page.admin.main";


    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String ROLE_PARAMETER = "role";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String LOCALE_PARAMETER = "locale";
    private static final String CURRENT_BALANCE_PARAMETER = "currentBalance";
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String AVAILABLE_ACCOMMODATIONS_ATTRIBUTE = "availableAccommodations";
    private static final String ACCOMMODATIONS_ATTRIBUTE = "accommodations";

    private static final String ERROR_LOGIN = "error login";

    private BaseService baseService;

    /**
     * Constructor to initialize
     */
    public LoginCommand(){
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        try {
            Optional<User> optionalUser = baseService.logIn(username, password);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                if(user.getUserStatus() == CLIENT_STATUS){
                    page = ConfigurationManager.getProperty(CLIENT_MAIN_PAGE);

                    List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
                    request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);
                }else{
                    page = ConfigurationManager.getProperty(ADMIN_MAIN_PAGE);

                    List<Accommodation> accommodations = baseService.findAllAccommodations();
                    request.setAttribute(ACCOMMODATIONS_ATTRIBUTE, accommodations);
                }
                storeDataInSession(request, user);
            }else {
                request.setAttribute(MESSAGE_ATTRIBUTE, ERROR_LOGIN);
                page = ConfigurationManager.getProperty(LOGIN_PAGE);
            }
        }catch (ServiceException e){
            logger.log(Level.ERROR, e);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }

    /**
     * store data in session
     * @param request httpServletRequest request
     * @param user user
     */
    private void storeDataInSession(HttpServletRequest request, User user){
        HttpSession session = request.getSession(true);
        if(session.getAttribute(ROLE_PARAMETER) == null) {
            session.setAttribute(ROLE_PARAMETER, user.getUserStatus());
        }
        if(session.getAttribute(USER_ID_PARAMETER) == null){
            session.setAttribute(USER_ID_PARAMETER, user.getEntityId());
        }
        if(session.getAttribute(LOCALE_PARAMETER) == null){
            session.setAttribute(LOCALE_PARAMETER, DEFAULT_LOCALE);
        }
        if(user.getUserStatus() == 1){
            if(session.getAttribute(CURRENT_BALANCE_PARAMETER) == null){
                session.setAttribute(CURRENT_BALANCE_PARAMETER, ((Client)user).getCurrency());
            }
        }
    }
}
