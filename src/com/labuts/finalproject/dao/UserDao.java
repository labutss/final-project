package com.labuts.finalproject.dao;

import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Create UserDao from AbstractDao
 * UserDao class is used to to make User requests to database
 */
public interface UserDao extends AbstractDao<User> {
    /**
     *find user by login
     * @param login login
     * @return user or Optional.empty()
     * @throws DaoException when something goes wrong
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     *add request to user
     * @param userId user id
     * @param requestId request id
     * @throws DaoException when something goes wrong
     */
    void addRequestToUser(int userId, int requestId) throws DaoException;

    /**
     *update user balance
     * @param userId user id
     * @param newBalance new balance
     * @throws DaoException when something goes wrong
     */
    void updateUserBalance(int userId, double newBalance) throws DaoException;

    /**
     *find associated requests
     * @param userId user id
     * @return list of associated requests
     * @throws DaoException when something goes wrong
     */
    List<Integer> findAssociatedRequests(int userId) throws DaoException;

    /**
     *find user id by username
     * @param username username
     * @return user's id
     * @throws DaoException when something goes wrong
     */
    int findUserId(String username) throws DaoException;
}