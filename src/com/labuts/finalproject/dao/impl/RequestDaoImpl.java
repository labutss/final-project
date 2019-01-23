package com.labuts.finalproject.dao.impl;

import com.labuts.finalproject.dao.RequestDao;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create RequestDaoImpl from RequestDao
 */
public class RequestDaoImpl implements RequestDao {
    private static final String SQL_QUERY_FIND_ALL_REQUESTS = "SELECT * FROM requests";
    private static final String SQL_QUERY_FIND_NEW_REQUESTS = "SELECT * FROM requests where requestStatus=0";
    private static final String SQL_QUERY_UPDATE_REQUEST_STATUS = "UPDATE requests SET requestStatus=? WHERE requestId=?";
    private static final String SQL_QUERY_UPDATE_REQUEST_BILL = "UPDATE requests SET requestBill=?, executionDate=? WHERE requestId=?";
    private static final String SQL_QUERY_FIND_REQUEST_BY_ID = "SELECT * FROM requests where requestId=?";
    private static final String SQL_QUERY_ADD_REQUEST = "INSERT INTO requests (requestId, requestBill, requestStatus, executionDate, expectedExecutionDate) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY_ADD_ACCOMMODATION_TO_REQUEST ="INSERT INTO requestaccommodationrelation (requestId, accommodationId, markForAccommodation) VALUES (?,?,?)";
    private static final String SQL_QUERY_FIND_ASSOCIATED_ACCOMMODATIONS = "SELECT Accommodations.accommodationId, markForAccommodation FROM RequestAccommodationRelation\n" +
            "inner join Accommodations ON RequestAccommodationRelation.accommodationId = Accommodations.accommodationId where requestId=?";

    private static final String REQUEST_ID_LABEL = "requestId";
    private static final String REQUEST_BILL_LABEL = "requestBill";
    private static final String REQUEST_STATUS_LABEL = "requestStatus";
    private static final String EXECUTION_DATE_LABEL = "executionDate";
    private static final String EXPECTED_EXECUTION_DATE_LABEL = "expectedExecutionDate";
    private static final String ACCOMMODATION_ID_LABEL = "Accommodations.accommodationId";
    private static final String ACCOMMODATION_MARK_LABEL = "markForAccommodation";
    private static final int DEFAULT_ACCOMMODATION_MARK = 0;

    private static RequestDaoImpl instance;

    private RequestDaoImpl(){ }

    public static RequestDaoImpl getInstance(){
        if(instance == null){
            instance = new RequestDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Request> findAll() throws DaoException{
        return findRequests(SQL_QUERY_FIND_ALL_REQUESTS);
    }

    @Override
    public  List<Request> findNewRequests() throws DaoException{
        return findRequests(SQL_QUERY_FIND_NEW_REQUESTS);
    }

    @Override
    public void updateRequestBillAndDate(int requestId, double bill, java.util.Date executionDate) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_UPDATE_REQUEST_BILL);
            statement.setDouble(1, bill);
            statement.setDate(2, new java.sql.Date(executionDate.getTime()));
            statement.setInt(3, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during updating request bill and date", e);
        }finally {
            closeActions(connection, statement, null);
        }
    }

    @Override
    public void updateRequestStatus(int requestId, int requestStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_UPDATE_REQUEST_STATUS);
            statement.setInt(1, requestStatus);
            statement.setInt(2, requestId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during updating request starus", e);
        }finally {
            closeActions(connection, statement, null);
        }
    }

    @Override
    public Optional<Request> findEntityById(int entityId) throws DaoException{
        Optional<Request> entity = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_REQUEST_BY_ID);
            statement.setInt(1, entityId);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int requestId = resultSet.getInt(REQUEST_ID_LABEL);
                Date executionDate = resultSet.getDate(EXECUTION_DATE_LABEL);
                Date expectedExecutionDate = resultSet.getDate(EXPECTED_EXECUTION_DATE_LABEL);
                double bill = resultSet.getDouble(REQUEST_BILL_LABEL);
                int requestStatus = resultSet.getInt(REQUEST_STATUS_LABEL);

                List<Accommodation> accommodations = findAssociatedAccommodations(requestId);

                Request newRequest = new Request(
                        requestId, bill, executionDate,
                        expectedExecutionDate, requestStatus, accommodations);
                entity = Optional.of(newRequest);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding request", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return entity;
    }

    @Override
    public boolean add(Request entity) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_ADD_REQUEST);

            statement.setInt(1, entity.getEntityId());
            statement.setDouble(2, entity.getRequestBill());
            statement.setInt(3, entity.getRequestStatus());
            statement.setDate(4, new java.sql.Date(entity.getExecutionDate().getTime()));
            statement.setDate(5, new java.sql.Date(entity.getExpectedExecutionDate().getTime()));
            statement.executeUpdate();

            for(Accommodation accommodation: entity.getRequestAccommodations()){
                addAccommodationToRequest(entity.getEntityId(), accommodation.getEntityId(), connection, statement);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during adding request", e);
        }finally {
            closeActions(connection, statement, null);
        }
        return true;
    }

    @Override
    public boolean update(Request entity) {
        return false;
    }


    /**
     * find associated accommodations
     * @param requestId request id
     * @return list of associated accommodations
     * @throws DaoException when something goes wrong
     */
    private List<Accommodation> findAssociatedAccommodations(int requestId) throws DaoException{
        List<Accommodation> accommodations = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet accommodationResultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_ASSOCIATED_ACCOMMODATIONS);
            statement.setInt(1, requestId);

            accommodationResultSet = statement.executeQuery();
            while (accommodationResultSet.next()) {
                AccommodationDaoImpl accommodationDao = AccommodationDaoImpl.getInstance();
                int accommodationId = accommodationResultSet.getInt(ACCOMMODATION_ID_LABEL);
                int mark = accommodationResultSet.getInt(ACCOMMODATION_MARK_LABEL);

                Optional<Accommodation> accommodationOptional = accommodationDao.findEntityById(accommodationId);
                if (accommodationOptional.isPresent()) {
                    Accommodation accommodation = accommodationOptional.get();
                    accommodation.setAccommodationMark(mark);
                    accommodations.add(accommodation);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding associated requests", e);
        }finally {
            closeActions(connection, statement, accommodationResultSet);
        }
        return accommodations;
    }

    /**
     * add accommodation to request
     * @param requestId request id
     * @param accommodationId accommodation id
     * @param connection connection
     * @param statement statement
     * @throws DaoException when something goes wrong
     */
    private void addAccommodationToRequest(int requestId, int accommodationId, Connection connection, PreparedStatement statement) throws DaoException{
        if (findEntityById(requestId).isPresent()){
            try {
                connection = ConnectionPool.getInstance().takeConnection();
                statement = connection.prepareStatement(SQL_QUERY_ADD_ACCOMMODATION_TO_REQUEST);

                statement.setInt(1, requestId);
                statement.setInt(2, accommodationId);
                statement.setInt(3, DEFAULT_ACCOMMODATION_MARK);

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException("Exception during adding accommodation to request", e);
            }finally {
                closeActions(connection, statement, null);
            }
        }
    }

    /**
     * find requests
     * @param SQL_QUERY sql query for finding requests
     * @return list of requests
     * @throws DaoException when something goes wrong
     */
    private List<Request> findRequests(final String SQL_QUERY) throws DaoException{
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int requestId = resultSet.getInt(REQUEST_ID_LABEL);
                Date executionDate = resultSet.getDate(EXECUTION_DATE_LABEL);
                Date expectedExecutionDate = resultSet.getDate(EXPECTED_EXECUTION_DATE_LABEL);
                double bill = resultSet.getDouble(REQUEST_BILL_LABEL);
                int requestStatus = resultSet.getInt(REQUEST_STATUS_LABEL);

                List<Accommodation> accommodations = findAssociatedAccommodations(requestId);
                Request newRequest = new Request(
                        requestId, bill, executionDate,
                        expectedExecutionDate, requestStatus, accommodations);
                requests.add(newRequest);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding requests", e);
        } finally {
            closeActions(connection, statement, resultSet);
        }
        return requests;
    }
}



