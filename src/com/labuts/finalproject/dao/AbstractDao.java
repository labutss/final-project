package com.labuts.finalproject.dao;

import com.labuts.finalproject.entity.Entity;
import com.labuts.finalproject.exception.DaoException;
import com.labuts.finalproject.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * AbstractDao class is used to make requests to database
 * @param <T> entity type
 */
public interface AbstractDao<T extends Entity> {
    /**
     * find all entities
     * @return all T objects
     * @throws DaoException when something goes wrong
     */
    List<T> findAll() throws DaoException;

    /**
     * find entity by id
     * @param entityId entity id
     * @return entity or Optional.empty()
     * @throws DaoException when something goes wrong
     */
    Optional<T> findEntityById(int entityId) throws DaoException;

    /**
     * add entity
     * @param entity entity
     * @return if entity is added or no
     * @throws DaoException when something goes wrong
     */
    boolean add(T entity) throws DaoException;

    /**
     * update entity
     * @param entity entity
     * @return if entity is updated or no
     * @throws DaoException when something goes wrong
     */
    boolean update(T entity) throws DaoException;

    /**
     * close result set, statement and release connection
     * @param connection connection
     * @param statement statement
     * @param resultSet result set
     */
    default void closeActions(Connection connection, Statement statement, ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ConnectionPool.getInstance().releaseConnection(connection);
    }
}
