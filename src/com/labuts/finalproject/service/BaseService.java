package com.labuts.finalproject.service;

import com.labuts.finalproject.dao.impl.AccommodationDaoImpl;
import com.labuts.finalproject.dao.impl.UserDaoImpl;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Client;
import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BaseService class is used to manage base information
 */
public class BaseService {
    /**
     * client's status
     */
    private static final int CLIENT_STATUS = 1;
    /**
     * start currency
     */
    private static final double START_CURRENCY = 0.0;

    /**
     * Add client
     * @param username username
     * @param password password
     * @return added client or Optional.empty() if such client already exists
     * @throws ServiceException when something goes wrong
     */
    public Optional<User> addClient(String username, String password) throws ServiceException{
        int userId = IdGenerator.getId();
        username = XssAttackProtection.preventAttack(username);
        String encryptedPassword = PasswordEncryption.encryptPassword(password);
        User user = new Client(userId, CLIENT_STATUS, username, encryptedPassword, START_CURRENCY, new ArrayList<>());

        Optional<User> result = Optional.empty();
        try {
            Optional<User> similarUser = UserDaoImpl.getInstance().findByLogin(username);
            if(!similarUser.isPresent()){
                UserDaoImpl.getInstance().add(user);
                result = Optional.of(user);
            }
        }catch (DaoException e){
            throw new ServiceException("Exception during adding client", e);
        }
        return result;
    }

    /**
     * Find available accommodations
     * @return list of available accommodations
     * @throws ServiceException when something goes wrong
     */
    public List<Accommodation> findAvailableAccommodations() throws ServiceException{
        List<Accommodation> accommodations;
        try {
            accommodations = AccommodationDaoImpl.getInstance().findAvailableAccommodations();
            setMarksForAccommodations(accommodations);
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding available accommodations", e);
        }
        return accommodations;
    }

    /**
     * Find all accommodations
     * @return list of all accommodations
     * @throws ServiceException when something goes wrong
     */
    public List<Accommodation> findAllAccommodations() throws ServiceException{
        List<Accommodation> accommodations;
        try {
            accommodations = AccommodationDaoImpl.getInstance().findAll();
            setMarksForAccommodations(accommodations);
        } catch (DaoException e) {
            throw new ServiceException("Exception during finding all accommodations", e);
        }
        return accommodations;
    }

    /**
     * Set average marks for accommodations
     * @param accommodations accommodations to set marks
     * @throws DaoException when something goes wrong
     */
    private void setMarksForAccommodations(List<Accommodation> accommodations) throws DaoException{
        for (Accommodation accommodation : accommodations) {
            int accommodationId = accommodation.getEntityId();
            double averageMark = AccommodationDaoImpl.getInstance().findAverageMarkForAccommodation(accommodationId);
            accommodation.setAccommodationMark(averageMark);
        }
    }

    /**
     *Log in
     * @param username username
     * @param password password
     * @return user or Optional.empty() is such user doesn't exist
     * @throws ServiceException when something goes wrong
     */
    public Optional<User> logIn(String username, String password) throws ServiceException{
        Optional<User> result = Optional.empty();
        try {
            Optional<User> optionalUser = UserDaoImpl.getInstance().findByLogin(username);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();

                String realPassword = user.getPassword();
                String encryptedPassword = PasswordEncryption.encryptPassword(password);
                if(realPassword.equals(encryptedPassword)){
                    result = optionalUser;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during logging in", e);
        }
        return result;
    }
}
