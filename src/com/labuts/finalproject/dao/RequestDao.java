package com.labuts.finalproject.dao;

import com.labuts.finalproject.entity.Request;
import com.labuts.finalproject.exception.DaoException;

import java.util.Date;
import java.util.List;

/**
 * Create RequestDao from AbstractDao
 * RequestDao class is used to to make Request requests to database
 */
public interface RequestDao extends AbstractDao<Request> {
    /**
     * find new requests
     * @return list of new requests
     * @throws DaoException when something goes wrong
     */
    List<Request> findNewRequests() throws DaoException;

    /**
     * update request's bill and execution date
     * @param requestId request id
     * @param bill request bill
     * @param executionDate request execution date
     * @throws DaoException when something goes wrong
     */
    void updateRequestBillAndDate(int requestId, double bill, Date executionDate) throws DaoException;

    /**
     * update request status
     * @param requestId request id
     * @param requestStatus request status
     * @throws DaoException when something goes wrong
     */
    void updateRequestStatus(int requestId, int requestStatus) throws DaoException;
}
