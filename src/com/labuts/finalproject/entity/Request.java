package com.labuts.finalproject.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create class Request from Entity
 * Request class is used for storing request information
 */
public class Request extends Entity {
    /**
     * Bill for request
     */
    private double requestBill;
    /**
     * Request date of execution
     */
    private Date executionDate;
    /**
     * Request expected date of execution
     */
    private Date expectedExecutionDate;
    /**
     * Status of request
     */
    private int requestStatus;
    /**
     * List of request accommodations
     */
    private List<Accommodation> requestAccommodations;

    /**
     * Constructor to initialize request object
     * @param entityId entity id
     * @param requestBill initial bill for request
     * @param executionDate initial execution date
     * @param expectedExecutionDate initial expected day of execution
     * @param requestStatus initial request status
     * @param requestAccommodations initial request's accommodations
     */
    public Request(int entityId, double requestBill,
                   Date executionDate, Date expectedExecutionDate,
                   int requestStatus, List<Accommodation> requestAccommodations) {
        super(entityId);
        this.requestBill = requestBill;
        this.executionDate = executionDate;
        this.expectedExecutionDate = expectedExecutionDate;
        this.requestStatus = requestStatus;
        this.requestAccommodations = requestAccommodations;
    }

    /**
     * get bill for request
     * @return request's bill
     */
    public double getRequestBill() {
        return requestBill;
    }

    /**
     * set bill for request
     * @param requestBill new bill for request
     */
    public void setRequestBill(double requestBill) {
        this.requestBill = requestBill;
    }

    /**
     * get request execution date
     * @return request execution date
     */
    public Date getExecutionDate() {
        return executionDate;
    }

    /**
     * set request execution date
     * @param executionDate new execution date
     */
    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * get request expected execution date
     * @return request expected execution date
     */
    public Date getExpectedExecutionDate() {
        return expectedExecutionDate;
    }

    /**
     * set request expected execution date
     * @param expectedExecutionDate new expected execution date
     */
    public void setExpectedExecutionDate(Date expectedExecutionDate) {
        this.expectedExecutionDate = expectedExecutionDate;
    }

    /**
     * get request status
     * @return request status
     */
    public int getRequestStatus() {
        return requestStatus;
    }

    /**
     * set request status
     * @param requestStatus new request status
     */
    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * get request's list of accommodations
     * @return request's list of accommodations
     */
    public List<Accommodation> getRequestAccommodations() {
        return requestAccommodations;
    }

    /**
     * set request's list of accommodations
     * @param requestAccommodations new request's list of accommodations
     */
    public void setRequestAccommodations(List<Accommodation> requestAccommodations) {
        this.requestAccommodations = requestAccommodations;
    }

    /**
     * compare Request objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Request request = (Request) o;
        return Double.compare(request.requestBill, requestBill) == 0 &&
                requestStatus == request.requestStatus &&
                Objects.equals(executionDate, request.executionDate) &&
                Objects.equals(expectedExecutionDate, request.expectedExecutionDate) &&
                Objects.equals(requestAccommodations, request.requestAccommodations);
    }

    /**
     * get request's hash code
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requestBill, executionDate, expectedExecutionDate, requestStatus, requestAccommodations);
    }
}
