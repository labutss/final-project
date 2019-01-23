package com.labuts.finalproject.dao.impl;

import com.labuts.finalproject.dao.UserDao;
import com.labuts.finalproject.entity.Client;
import com.labuts.finalproject.entity.User;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create UserDaoImpl from UserDao
 */
public class UserDaoImpl implements UserDao {
    private static final String SQL_QUERY_FIND_ALL_USERS ="SELECT * FROM users";
    private static final String SQL_QUERY_ADD_REQUEST ="INSERT INTO userrequestrelation (userId, requestId) VALUES (?,?)";
    private static final String SQL_QUERY_FIND_USER_BY_ID = "SELECT * FROM users WHERE userId=?";
    private static final String SQL_QUERY_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE username=?";
    private static final String SQL_QUERY_ADD_USER = "INSERT INTO Users (userId, username, password, userStatus, currency) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE_USER = "UPDATE Users SET username=?, password=?, currency=? WHERE userId=?";
    private static final String SQL_QUERY_FIND_ASSOCIATED_REQUESTS = "SELECT requestId from userrequestrelation where userId=?";
    private static final String SQL_QUERY_UPDATE_USER_BALANCE = "UPDATE Users SET currency=? WHERE userId=?";
    private static final String SQL_QUERY_FIND_USER_ID = "SELECT userId FROM users WHERE username=?";

    private static final String USER_ID_LABEL = "userId";
    private static final String USERNAME_LABEL = "username";
    private static final String USER_STATUS_LABEL = "userStatus";
    private static final String PASSWORD_LABEL = "password";
    private static final String CURRENCY_LABEL = "currency";
    private static final String REQUEST_ID_LABEL = "requestId";
    private static final int CLIENT_STATUS = 1;

    private static UserDaoImpl instance;

    private UserDaoImpl(){}

    public static UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<User> entity = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_USER_BY_LOGIN);
            statement.setString(1, login);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                User user = createUser(resultSet);
                entity = Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding user", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return entity;
    }

    @Override
    public void addRequestToUser(int userId, int requestId) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        if (findEntityById(userId).isPresent()){
            try{
                connection = ConnectionPool.getInstance().takeConnection();
                statement = connection.prepareStatement(SQL_QUERY_ADD_REQUEST);
                statement.setInt(1, userId);
                statement.setInt(2, requestId);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException("Exception during adding request to user", e);
            }finally {
                closeActions(connection, statement, null);
            }
        }
    }

    @Override
    public void updateUserBalance(int userId, double newBalance) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            if (findEntityById(userId).isPresent()){
                statement = connection.prepareStatement(SQL_QUERY_UPDATE_USER_BALANCE);

                statement.setDouble(1, newBalance);
                statement.setInt(2, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during updating user balance", e);
        }finally {
            closeActions(connection, statement, null);
        }
    }

    @Override
    public List<User> findAll() throws DaoException{
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_ALL_USERS);

            resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding all users", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return users;
    }

    @Override
    public Optional<User> findEntityById(int entityId) throws DaoException{
        Optional<User> entity = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_USER_BY_ID);
            statement.setInt(1, entityId);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                User user = createUser(resultSet);
                entity = Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding user by id", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return entity;
    }

    @Override
    public boolean add(User entity) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_ADD_USER);

            statement.setInt(1, entity.getEntityId());
            statement.setString(2, entity.getLogin());
            statement.setString(3, entity.getPassword());
            statement.setInt(4, entity.getUserStatus());
            if(entity.getUserStatus() == CLIENT_STATUS){
                Client client = (Client)entity;
                statement.setDouble(5, client.getCurrency());
            }else {
                statement.setDouble(5, 0);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during adding user", e);
        }finally {
            closeActions(connection, statement, null);
        }
        return true;
    }

    @Override
    public boolean update(User entity) throws DaoException{
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            if (findEntityById(entity.getEntityId()).isPresent()){
                statement = connection.prepareStatement(SQL_QUERY_UPDATE_USER);

                statement.setString(1, entity.getLogin());
                statement.setString(2, entity.getPassword());
                if(entity.getUserStatus() == CLIENT_STATUS){
                    Client client = (Client)entity;
                    statement.setDouble(3, client.getCurrency());
                }else {
                    statement.setDouble(3, 0);
                }
                statement.setInt(4, entity.getEntityId());
                statement.executeUpdate();
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during updating user", e);
        }finally {
            closeActions(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<Integer> findAssociatedRequests(int userId) throws DaoException{
        List<Integer> associatedRequests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_ASSOCIATED_REQUESTS);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int requestId = resultSet.getInt(REQUEST_ID_LABEL);
                associatedRequests.add(requestId);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding associated requests", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return associatedRequests;
    }

    @Override
    public int findUserId(String username) throws DaoException {
        int userId = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_USER_ID);
            statement.setString(1, username);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                userId = resultSet.getInt(USER_ID_LABEL);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding user's id", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return userId;
    }

    /**
     * create user
     * @param resultSet result set
     * @return user
     * @throws SQLException when something goes wrong
     * @throws DaoException when something goes wrong
     */
    private User createUser(ResultSet resultSet) throws SQLException, DaoException {
        User user;
        int userId = resultSet.getInt(USER_ID_LABEL);
        String username = resultSet.getString(USERNAME_LABEL);
        String password = resultSet.getString(PASSWORD_LABEL);
        int userStatus = resultSet.getInt(USER_STATUS_LABEL);
        if(userStatus == CLIENT_STATUS){
            double currency = resultSet.getDouble(CURRENCY_LABEL);
            List<Integer> requestsId = findAssociatedRequests(userId);
            user = new Client(userId, userStatus, username, password, currency, requestsId);
        }else {
            user = new User(userId, userStatus, username, password);
        }
        return user;
    }
}
