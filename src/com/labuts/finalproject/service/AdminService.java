package com.labuts.finalproject.service;

import com.labuts.finalproject.dao.impl.AccommodationDaoImpl;
import com.labuts.finalproject.dao.impl.RequestDaoImpl;
import com.labuts.finalproject.dao.impl.UserDaoImpl;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.entity.User;
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
 * AdminService class is used to manage information
 */
public class AdminService {
    /**
     * date formatter
     */
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
    /**
     * default availability
     */
    private static final boolean DEFAULT_IS_AVAILABLE = true;
    /**
     * default accommodation mark
     */
    private static final double DEFAULT_ACCOMMODATION_MARK = 0;
    /**
     * accepted request status
     */
    private static final int REQUEST_ACCEPTED_STATUS = 1;

    /**
     * Find accommodation by id
     * @param accommodationId accommodation's id
     * @return accommodation or Optional.empty()
     * @throws ServiceException when something goes wrong
     */
    public Optional<Accommodation> findAccommodation(String accommodationId) throws ServiceException {
        Optional<Accommodation> result = Optional.empty();
        if( accommodationId != null){
            int id = Integer.parseInt(accommodationId);
            try {
                result = AccommodationDaoImpl.getInstance().findEntityById(id);
            } catch (DaoException e) {
                throw new ServiceException("Exception during finding accommodation", e);
            }
        }
        return result;
    }

    /**
     * Add accommodation
     * @param name initial accommodation name
     * @param description initial accommodation description
     * @param cost initial accommodation cost
     * @throws ServiceException when something goes wrong
     */
    public void addAccommodation(String name, String description, String cost) throws ServiceException {
        String accommodationName = XssAttackProtection.preventAttack(name);
        String accommodationDescription = XssAttackProtection.preventAttack(description);
        Double accommodationCost = new BigDecimal(cost).doubleValue();
        int accommodationId = IdGenerator.getId();

        Accommodation accommodation = new Accommodation(accommodationId, accommodationName, accommodationDescription,
                accommodationCost, DEFAULT_IS_AVAILABLE, DEFAULT_ACCOMMODATION_MARK);
        try {
            AccommodationDaoImpl.getInstance().add(accommodation);
        } catch (DaoException e) {
            throw new ServiceException("Exception during adding accommodation", e);
        }
    }

    /**
     * Edit accommodation
     * @param id accommodation id
     * @param name new accommodation name
     * @param description new accommodation description
     * @param cost new accommodation cost
     * @throws ServiceException when something goes wrong
     */
    public void editAccommodation(String id, String name, String description, String cost) throws ServiceException {
        String accommodationName = XssAttackProtection.preventAttack(name);
        String accommodationDescription = XssAttackProtection.preventAttack(description);
        Double accommodationCost = Double.parseDouble(cost);
        int accommodationId = Integer.parseInt(id);

        Accommodation accommodation = new Accommodation(accommodationId, accommodationName, accommodationDescription,
                accommodationCost, DEFAULT_IS_AVAILABLE, DEFAULT_ACCOMMODATION_MARK);
        try {
            AccommodationDaoImpl.getInstance().update(accommodation);
        } catch (DaoException e) {
            throw new ServiceException("Exception during editing accommodation", e);
        }
    }

    /**
     *Find all users
     * @return list of all users
     * @throws ServiceException when something goes wrong
     */
    public List<User> findAllUsers()throws ServiceException{
        List<User> users;
        try{
            users = UserDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding all users", e);
        }
        return users;
    }

    /**
     *Change accommodation availability
     * @param id accommodation id
     * @param isAvailable new accommodation availability
     * @throws ServiceException when something goes wrong
     */
    public void changeAccommodationAvailability(String id, String isAvailable) throws ServiceException{
        int accommodationId = Integer.parseInt(id);
        boolean accommodationAvailable = Boolean.parseBoolean(isAvailable);
        try{
            AccommodationDaoImpl.getInstance().changeAccommodationAvailability(accommodationId, accommodationAvailable);
        } catch (DaoException e) {
            throw new ServiceException("Exception during changing accommodation availability", e);
        }
    }

    /**
     *Find all requests
     * @return list of all requests
     * @throws ServiceException when something goes wrong
     */
    public List<Request> findAllRequests() throws ServiceException{
        List<Request> requests;
        try {
            requests = RequestDaoImpl.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding all requests", e);
        }
        return requests;
    }

    /**
     *Find new requests
     * @return list of new requests
     * @throws ServiceException when something goes wrong
     */
    public List<Request> findNewRequests() throws ServiceException{
        List<Request> requests;
        try {
            requests = RequestDaoImpl.getInstance().findNewRequests();
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding new requests", e);
        }
        return requests;
    }

    /**
     *Find client's requests by id
     * @param id client's id
     * @return list of client's requests
     * @throws ServiceException when something goes wrong
     */
    public List<Request> findClientsRequests(String id) throws ServiceException{
        int clientId = Integer.parseInt(id);
        List<Integer> requestsId;
        try {
            requestsId = UserDaoImpl.getInstance().findAssociatedRequests(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding client's requests", e);
        }
        return findRequests(requestsId);
    }

    /**
     *Find client's requests by username
     * @param username client's username
     * @return list of client's requests
     * @throws ServiceException when something goes wrong
     */
    public List<Request> findClientsRequestsByName(String username) throws ServiceException{
        List<Integer> requestsId;
        try {
            int userId = UserDaoImpl.getInstance().findUserId(username);
            requestsId = UserDaoImpl.getInstance().findAssociatedRequests(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding client's requests", e);
        }
        return findRequests(requestsId);
    }

    /**
     *Set request's bill and date
     * @param id request id
     * @param accommodationsId accommodation's ids
     * @param date execution date
     * @throws ServiceException when something goes wrong
     */
    public void setRequestBillAndDate(String id, String[] accommodationsId, String date) throws ServiceException{
        int requestId = Integer.parseInt(id);
        double billForRequest = countRequestBill(accommodationsId);
        Date executionDate;
        try {
            executionDate = DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            throw new ServiceException("Exception during setting request bill and date", e);
        }
        try{
            RequestDaoImpl.getInstance().updateRequestBillAndDate(requestId, billForRequest, executionDate);
            RequestDaoImpl.getInstance().updateRequestStatus(requestId, REQUEST_ACCEPTED_STATUS);
        } catch (DaoException e) {
            throw new ServiceException("Exception during setting request bill and date", e);
        }
    }

    /**
     *Count request's bill
     * @param idList list of ids
     * @return bill for request
     * @throws ServiceException when something goes wrong
     */
    private double countRequestBill(String[] idList) throws ServiceException{
        double sum = 0.0;
        try{
            for(String id: idList){
                int accommodationId = Integer.parseInt(id);
                double cost = AccommodationDaoImpl.getInstance().findAccommodationCost(accommodationId);
                sum = sum + cost;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during counting request's bill", e);
        }
        return sum;
    }

    /**
     *Find requests
     * @param requestsId list of request's ids
     * @return list of requests
     * @throws ServiceException when something goes wrong
     */
    private List<Request> findRequests(List<Integer> requestsId) throws ServiceException{
        List<Request> requests = new ArrayList<>();
        try{
            for(Integer requestId: requestsId){
                Optional<Request> optionalRequest = RequestDaoImpl.getInstance().findEntityById(requestId);
                optionalRequest.ifPresent(requests::add);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding client's requests", e);
        }
        return requests;
    }
}
