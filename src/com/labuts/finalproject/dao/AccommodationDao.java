package com.labuts.finalproject.dao;

import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.DaoException;

import java.util.List;

/**
 * Create AccommodationDao from AbstractDao
 * AccommodationDao class is used to to make Accommodation requests to database
 */
public interface AccommodationDao extends AbstractDao<Accommodation> {
    /**
     * find available accommodations
     * @return list of available accommodations
     * @throws DaoException when something goes wrong
     */
    List<Accommodation> findAvailableAccommodations() throws DaoException;

    /**
     * find average mark for accommodation
     * @param accommodationId accommodation id
     * @return average mark for accommodation
     * @throws DaoException when something goes wrong
     */
    double findAverageMarkForAccommodation(int accommodationId) throws DaoException;

    /**
     * change accommodation availability
     * @param accommodationId accommodation id
     * @param isAvailable accommodation availability
     * @throws DaoException when something goes wrong
     */
    void changeAccommodationAvailability(int accommodationId, boolean isAvailable) throws DaoException;

    /**
     * find accommodation cost
     * @param accommodationId accommodation id
     * @return accommodation cost
     * @throws DaoException when something goes wrong
     */
    double findAccommodationCost(int accommodationId) throws DaoException;

    /**
     *update mark for accommodation
     * @param accommodationId accommodation id
     * @param requestId request id
     * @param markForAccommodation mark for accommodation
     * @throws DaoException when something goes wrong
     */
    void updateMarkForAccommodation(int accommodationId, int requestId, double markForAccommodation) throws DaoException;
}