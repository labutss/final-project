package com.labuts.finalproject.command.impl.client;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.ClientService;
import com.labuts.finalproject.service.ConfigurationManager;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create PayRequestBillCommand from Command
 */
public class PayRequestBillCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";
    private static final String CLIENT_REQUESTS_PAGE = "path.page.client.requests";

    private static final String USER_ID_PARAMETER = "userId";
    private static final String REQUEST_BILL_PARAMETER = "requestBill";
    private static final String CURRENT_BALANCE_PARAMETER = "currentBalance";
    private static final String REQUEST_ID_PARAMETER = "requestId";

    private static final String REQUESTS_ATTRIBUTE = "requests";
    private static final String MONEY_MESSAGE = "moneyMessage";
    private static final String NOT_ENOUGH_MONEY = "not enough";

    private ClientService clientService;

    /**
     * Constructor to initialize
     */
    public PayRequestBillCommand(){
        clientService = new ClientService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(CLIENT_REQUESTS_PAGE);
        HttpSession session = request.getSession(true);
        if((int)session.getAttribute(ROLE_PARAMETER) == CLIENT_STATUS) {
            double currentBalance = (double)session.getAttribute(CURRENT_BALANCE_PARAMETER);
            String requestBill = request.getParameter(REQUEST_BILL_PARAMETER);
            int userId = (int)session.getAttribute(USER_ID_PARAMETER);

            double moneyLeft = currentBalance-Double.parseDouble(requestBill);
            if(moneyLeft< 0){
                request.setAttribute(MONEY_MESSAGE, NOT_ENOUGH_MONEY);
            }else {
                String requestId = request.getParameter(REQUEST_ID_PARAMETER);
                try {
                    clientService.payBill(userId, requestId, moneyLeft);
                    session.setAttribute(CURRENT_BALANCE_PARAMETER, moneyLeft);
                }catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                    page = ConfigurationManager.getProperty(ERROR_PAGE);
                }
            }
            try {
                List<Request> requests = clientService.findUsersRequests(userId);
                request.setAttribute(REQUESTS_ATTRIBUTE, requests);
            }catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }else {
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
