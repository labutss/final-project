package com.labuts.finalproject.dao.impl;

import com.labuts.finalproject.dao.AccommodationDao;
import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create AccommodationDaoImpl from AccommodationDao
 */
public class AccommodationDaoImpl implements AccommodationDao {
    private static final String SQL_QUERY_FIND_ALL_ACCOMMODATIONS = "SELECT * FROM Accommodations";
    private static final String SQL_QUERY_FIND_AVAILABLE_ACCOMMODATIONS = "SELECT * FROM Accommodations WHERE accommodationAvailable=true";
    private static final String SQL_QUERY_FIND_ACCOMMODATION_BY_ID = "SELECT * FROM Accommodations WHERE accommodationId=?";
    private static final String SQL_QUERY_ADD_ACCOMMODATION = "INSERT INTO Accommodations (accommodationId, accommodationName, accommodationDescription, accommodationCost, accommodationAvailable) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE_ACCOMMODATION = "UPDATE Accommodations SET accommodationName=?, accommodationDescription=?, accommodationCost=?, accommodationAvailable=? WHERE accommodationId=?";
    private static final String SQL_QUERY_AVERAGE_MARK_FOR_ACCOMMODATION = "SELECT AVG(markForAccommodation) from RequestAccommodationRelation where accommodationId = ? and not markForAccommodation = 0";
    private static final String SQL_QUERY_CHANGE_ACCOMMODATION_AVAILABILITY = "UPDATE Accommodations SET accommodationAvailable=? WHERE accommodationId=?";
    private static final String SQL_QUERY_FIND_ACCOMMODATION_PRICE = "SELECT accommodationCost FROM Accommodations WHERE accommodationId=?";
    private static final String SQL_QUERY_UPDATE_ACCOMMODATION_MARK = "UPDATE requestaccommodationrelation SET markForAccommodation=? WHERE accommodationId=? AND requestId=?";

    private static final String ACCOMMODATION_ID_LABEL = "accommodationId";
    private static final String ACCOMMODATION_NAME_LABEL = "accommodationName";
    private static final String ACCOMMODATION_DESCRIPTION_LABEL = "accommodationDescription";
    private static final String ACCOMMODATION_COST_LABEL = "accommodationCost";
    private static final String ACCOMMODATION_AVAILABLE_LABEL = "accommodationAvailable";
    private static int DEFAULT_ACCOMMODATION_MARK = 0;

    private static AccommodationDaoImpl instance;

    private AccommodationDaoImpl(){}

    public static AccommodationDaoImpl getInstance(){
        if(instance == null){
            instance = new AccommodationDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Accommodation> findAll() throws DaoException {
        return findAccommodations(SQL_QUERY_FIND_ALL_ACCOMMODATIONS);
    }

    @Override
    public Optional<Accommodation> findEntityById (int entityId) throws DaoException {
        Optional<Accommodation> entity = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_ACCOMMODATION_BY_ID);
            statement.setInt(1, entityId);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int accommodationId = resultSet.getInt(ACCOMMODATION_ID_LABEL);
                String accommodationName = resultSet.getString(ACCOMMODATION_NAME_LABEL);
                String accommodationDescription = resultSet.getString(ACCOMMODATION_DESCRIPTION_LABEL);
                double accommodationCost = resultSet.getDouble(ACCOMMODATION_COST_LABEL);
                boolean accommodationAvailable = resultSet.getBoolean(ACCOMMODATION_AVAILABLE_LABEL);

                Accommodation newAccommodation = new Accommodation(accommodationId,
                        accommodationName, accommodationDescription,
                        accommodationCost, accommodationAvailable, DEFAULT_ACCOMMODATION_MARK);
                entity = Optional.of(newAccommodation);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding accommodation by id", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return entity;
    }

    @Override
    public boolean add(Accommodation accommodation) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_ADD_ACCOMMODATION);

            statement.setInt(1, accommodation.getEntityId());
            statement.setString(2, accommodation.getAccommodationName());
            statement.setString(3, accommodation.getAccommodationDescription());
            statement.setDouble(4, accommodation.getAccommodationCost());
            statement.setBoolean(5, accommodation.getIsAvailable());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during adding accommodation", e);
        }finally {
            closeActions(connection, statement, null);
        }
        return true;
    }

    @Override
    public boolean update(Accommodation accommodation) throws DaoException{
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            if (findEntityById(accommodation.getEntityId()).isPresent()){
                statement = connection.prepareStatement(SQL_QUERY_UPDATE_ACCOMMODATION);

                statement.setString(1, accommodation.getAccommodationName());
                statement.setString(2, accommodation.getAccommodationDescription());
                statement.setDouble(3, accommodation.getAccommodationCost());
                statement.setBoolean(4, accommodation.getIsAvailable());
                statement.setInt(5, accommodation.getEntityId());

                statement.executeUpdate();
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during updating accommodation", e);
        }finally {
            closeActions(connection, statement, null);
        }
        return result;
    }

    @Override
    public List<Accommodation> findAvailableAccommodations() throws DaoException{
        return findAccommodations(SQL_QUERY_FIND_AVAILABLE_ACCOMMODATIONS);
    }

    @Override
    public double findAverageMarkForAccommodation(int accommodationId) throws DaoException{
        double result = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_AVERAGE_MARK_FOR_ACCOMMODATION);
            statement.setInt(1, accommodationId);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding average mark for accommodation", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return result;
    }

    @Override
    public void changeAccommodationAvailability(int accommodationId, boolean isAvailable) throws DaoException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            if (findEntityById(accommodationId).isPresent()){
                statement = connection.prepareStatement(SQL_QUERY_CHANGE_ACCOMMODATION_AVAILABILITY);

                statement.setBoolean(1, isAvailable);
                statement.setInt(2, accommodationId);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during changing accommodation availability", e);
        }finally {
            closeActions(connection, statement, null);
        }
    }

    @Override
    public double findAccommodationCost(int accommodationId) throws DaoException{
        double cost = 0.0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_FIND_ACCOMMODATION_PRICE);
            statement.setInt(1, accommodationId);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                cost = resultSet.getDouble(ACCOMMODATION_COST_LABEL);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during finding accommodation cost", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return cost;
    }

    @Override
    public void updateMarkForAccommodation(int accommodationId, int requestId, double markForAccommodation) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY_UPDATE_ACCOMMODATION_MARK);

            statement.setDouble(1, markForAccommodation);
            statement.setInt(2, accommodationId);
            statement.setInt(3, requestId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during updating accommodation mark", e);
        }finally {
            closeActions(connection, statement, null);
        }
    }

    /**
     * find accommodations
     * @param SQL_QUERY sql query for finding accommodations
     * @return list of accommodations
     * @throws DaoException when something goes wrong
     */
    private List<Accommodation> findAccommodations(final String SQL_QUERY) throws DaoException{
        List<Accommodation> accommodations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(SQL_QUERY);
            resultSet = statement.executeQuery();

            createAccommodations(resultSet, accommodations);
        } catch (SQLException e) {
            throw new DaoException("Exception during finding accommodations", e);
        }finally {
            closeActions(connection, statement, resultSet);
        }
        return accommodations;
    }

    /**
     * create accommodation
     * @param resultSet result set
     * @param accommodations list of accommodations
     * @throws SQLException when something goes wrong
     */
    private void createAccommodations(ResultSet resultSet, List<Accommodation> accommodations) throws SQLException {
        while (resultSet.next()){
            int accommodationId = resultSet.getInt(ACCOMMODATION_ID_LABEL);
            String accommodationName = resultSet.getString(ACCOMMODATION_NAME_LABEL);
            String accommodationDescription = resultSet.getString(ACCOMMODATION_DESCRIPTION_LABEL);
            double accommodationCost = resultSet.getDouble(ACCOMMODATION_COST_LABEL);
            boolean accommodationAvailable = resultSet.getBoolean(ACCOMMODATION_AVAILABLE_LABEL);

            Accommodation newAccommodation = new Accommodation(accommodationId,
                    accommodationName, accommodationDescription,
                    accommodationCost, accommodationAvailable, DEFAULT_ACCOMMODATION_MARK);
            accommodations.add(newAccommodation);
        }
    }
}
