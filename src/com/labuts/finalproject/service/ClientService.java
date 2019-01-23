package com.labuts.finalproject.service;

import com.labuts.finalproject.dao.impl.AccommodationDaoImpl;
import com.labuts.finalproject.dao.impl.RequestDaoImpl;
import com.labuts.finalproject.dao.impl.UserDaoImpl;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.exception.ServiceException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * ClientService class is used to manage client's information
 */
public class ClientService {
    /**
     * date formatter
     */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
    /**
     * default request bill
     */
    private static final double DEFAULT_REQUEST_BILL = 0.0;
    /**
     * empty date
     */
    private static final Date EMPTY_DATE = new Date();
    /**
     * default request status
     */
    private static final int DEFAULT_REQUEST_STATUS = 0;
    /**
     * paid request status
     */
    private static final int PAID_REQUEST_STATUS = 2;


    /**
     * Add request to user
     * @param userId user's id
     * @param date expected execution date
     * @param accommodations accommodation's ids
     * @throws ServiceException when something goes wrong
     */
    public void addRequest(int userId, String date, String[] accommodations) throws ServiceException{
        Date expectedDateOfExecution;
        try {
            expectedDateOfExecution = DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            throw new ServiceException("Exception during adding request", e);
        }
        int requestId = IdGenerator.getId();
        List<Accommodation> accommodationList = new ArrayList<>();
        for(String id: accommodations){
            int accommodationId = Integer.parseInt(id);
            Accommodation accommodation = new Accommodation(accommodationId);
            accommodationList.add(accommodation);
        }
        Request request = new Request(requestId, DEFAULT_REQUEST_BILL, EMPTY_DATE,
                expectedDateOfExecution, DEFAULT_REQUEST_STATUS, accommodationList);
        try {
            RequestDaoImpl.getInstance().add(request);
            UserDaoImpl.getInstance().addRequestToUser(userId, requestId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during adding request", e);
        }
    }

    /**
     * Find user's requests
     * @param userId user's id
     * @return list of user's requests
     * @throws ServiceException when something goes wrong
     */
    public List<Request> findUsersRequests(int userId) throws ServiceException{
        List<Request> requests = new ArrayList<>();
        List<Integer> associatedRequests;
        try{
            associatedRequests = UserDaoImpl.getInstance().findAssociatedRequests(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding user's request", e);
        }
        for (Integer requestId: associatedRequests){
            try {
                Optional<Request> optionalRequest = RequestDaoImpl.getInstance().findEntityById(requestId);
                if(optionalRequest.isPresent()){
                    Request request = optionalRequest.get();
                    requests.add(request);
                }
            }catch (DaoException e) {
                throw new ServiceException("Exception during finding user's request", e);
            }
        }
        return requests;
    }

    /**
     * Top up user's balance
     * @param currentBalance current balance
     * @param amountOfMoney amount of money
     * @param userId user's id
     * @return new balance
     * @throws ServiceException when something goes wrong
     */
    public double topUpBalance(double currentBalance, String amountOfMoney, int userId) throws ServiceException{
        BigDecimal decimalAmountOfMoney = new BigDecimal(amountOfMoney);

        double newBalance = currentBalance + decimalAmountOfMoney.doubleValue();
        try {
            UserDaoImpl.getInstance().updateUserBalance(userId, newBalance);
        } catch (DaoException e) {
            throw new ServiceException("Exception during topping up balance", e);
        }
        return newBalance;
    }

    /**
     * Pay bill for request
     * @param userId user's id
     * @param reqId request's id
     * @param newAmountOfMoney new amount of money
     * @throws ServiceException when something goes wrong
     */
    public void payBill(int userId, String reqId, double newAmountOfMoney) throws ServiceException{
        int requestId = Integer.parseInt(reqId);
        try {
            UserDaoImpl.getInstance().updateUserBalance(userId, newAmountOfMoney);
            RequestDaoImpl.getInstance().updateRequestStatus(requestId, PAID_REQUEST_STATUS);
        } catch (DaoException e) {
            throw new ServiceException("Exception during paying bill", e);
        }
    }

    /**
     * Rate accommodation
     * @param accommodationIdValue accommodation's id
     * @param requestIdValue request's id
     * @param accommodationMarkValue mark for accommodation
     * @throws ServiceException when something goes wrong
     */
    public void rateAccommodation(String accommodationIdValue, String requestIdValue, String accommodationMarkValue) throws ServiceException{
        int accommodationId = Integer.parseInt(accommodationIdValue);
        int requestId = Integer.parseInt(requestIdValue);
        double accommodationMark = Double.parseDouble(accommodationMarkValue);
        try{
            AccommodationDaoImpl.getInstance().updateMarkForAccommodation(accommodationId, requestId, accommodationMark);
        } catch (DaoException e) {
            throw new ServiceException("Exception during rating accommodation", e);
        }
    }

}
