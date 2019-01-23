package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.ClientService;
import com.labuts.finalproject.service.ConfigurationManager;
import com.labuts.finalproject.validation.BaseValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Create TopUpBalanceCommand from Command
 */
public class TopUpBalanceCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";

    private static final String CURRENT_PAGE_PARAMETER = "currentPage";
    private static final String AMOUNT_OF_MONEY_PARAMETER = "amountOfMoney";
    private static final String USER_ID_PARAMETER = "userId";
    private static final String CURRENT_BALANCE_PARAMETER = "currentBalance";
    private static final String BALANCE_MESSAGE_PARAMETER = "balanceMessage";
    private static final String ATTRIBUTES = "attributes";

    private static final String INVALID_AMOUNT_OF_MONEY_MESSAGE = "invalid amount of money";

    private ClientService clientService;

    /**
     * Constructor to initialize
     */
    public TopUpBalanceCommand(){
        clientService = new ClientService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            page = (String)session.getAttribute(CURRENT_PAGE_PARAMETER);
            String amountOfMoney = request.getParameter(AMOUNT_OF_MONEY_PARAMETER);

            BaseValidator baseValidator = new BaseValidator();
            if(baseValidator.isValidAmountOfMoney(amountOfMoney)){
                double currentBalance = 0.0;
                if (session.getAttribute(CURRENT_BALANCE_PARAMETER) != null){
                    currentBalance = (double)session.getAttribute(CURRENT_BALANCE_PARAMETER);
                }
                int userId = (int)session.getAttribute(USER_ID_PARAMETER);
                try {
                    double balance = clientService.topUpBalance(currentBalance, amountOfMoney, userId);
                    session.setAttribute(CURRENT_BALANCE_PARAMETER, balance);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                    page = ConfigurationManager.getProperty(ERROR_PAGE);
                }
            }else {
                request.setAttribute(BALANCE_MESSAGE_PARAMETER, INVALID_AMOUNT_OF_MONEY_MESSAGE);
            }
            Map<String, Object> attributes = (Map<String, Object>)session.getAttribute(ATTRIBUTES);
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }

    @Override
    public void storeData(HttpServletRequest request, String page) {}
}
