package com.labuts.finalproject.command.impl.base;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Client;
import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import com.labuts.finalproject.service.ConfigurationManager;
import com.labuts.finalproject.validation.UserValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Create RegistrationCommand from Command
 */
public class RegistrationCommand extends Command {
    private static final String REGISTRATION_PAGE = "path.page.registration";
    private static final String CLIENT_MAIN_PAGE = "path.page.client.main";
    private static final String ERROR_PAGE = "path.page.error";

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String REP_PASSWORD_PARAMETER = "reppassword";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String LOCALE_PARAMETER = "locale";
    private static final String CURRENT_BALANCE_PARAMETER = "currentBalance";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String AVAILABLE_ACCOMMODATIONS_ATTRIBUTE = "availableAccommodations";
    private static final String DEFAULT_LOCALE = "ru_RU";

    private static final String INVALID_REGISTRATION_DATA = "invalid";
    private static final String PASSWORDS_DO_NOT_MATCH = "passwords";
    private static final String DUPLICATED_USERNAME = "duplicated";

    private final BaseService baseService;

    /**
     * Constructor to initialize
     */
    public RegistrationCommand(){
        baseService = new BaseService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String repeatPassword = request.getParameter(REP_PASSWORD_PARAMETER);

        UserValidator userValidator = new UserValidator();
        if(userValidator.isValidUsername(username) && userValidator.isValidPassword(password)){
            if(password.equals(repeatPassword)){
                try {
                    Optional<User> optionalUser = baseService.addClient(username, password);
                    if(optionalUser.isPresent()){
                        page = ConfigurationManager.getProperty(CLIENT_MAIN_PAGE);
                        registration((Client)optionalUser.get(), request);
                    }else {
                        request.setAttribute(MESSAGE_ATTRIBUTE, DUPLICATED_USERNAME);
                        page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
                    }
                }catch (ServiceException e){
                    logger.log(Level.ERROR, e);
                    page = ConfigurationManager.getProperty(ERROR_PAGE);
                }
            }else {
                request.setAttribute(MESSAGE_ATTRIBUTE, PASSWORDS_DO_NOT_MATCH);
                page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
            }
        }else {
            request.setAttribute(MESSAGE_ATTRIBUTE, INVALID_REGISTRATION_DATA);
            page = ConfigurationManager.getProperty(REGISTRATION_PAGE);
        }
        return page;
    }

    /**
     * registration
     * @param client client
     * @param request httpServletRequest
     * @throws ServiceException when something goes wrong
     */
    private void registration(Client client, HttpServletRequest request) throws ServiceException{
        HttpSession session = request.getSession(true);
        if(session.getAttribute(ROLE_PARAMETER) == null) {
            session.setAttribute(ROLE_PARAMETER, client.getUserStatus());
        }
        if(session.getAttribute(USER_ID_PARAMETER) == null){
            session.setAttribute(USER_ID_PARAMETER, client.getEntityId());
        }
        if(session.getAttribute(LOCALE_PARAMETER) == null){
            session.setAttribute(LOCALE_PARAMETER, DEFAULT_LOCALE);
        }
        request.setAttribute(CURRENT_BALANCE_PARAMETER, client.getCurrency());
        request.setAttribute(LOCALE_PARAMETER, DEFAULT_LOCALE);

        List<Accommodation> availableAccommodations = baseService.findAvailableAccommodations();
        request.setAttribute(AVAILABLE_ACCOMMODATIONS_ATTRIBUTE, availableAccommodations);
    }
}
