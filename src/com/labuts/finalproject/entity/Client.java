package com.labuts.finalproject.entity;

import java.util.List;
import java.util.Objects;

/**
 * Create class Client from User
 * Client class is used for storing client information
 */
public class Client extends User {
    /**
     * client's currency
     */
    private double currency;
    /**
     * list of client's request's id
     */
    private List<Integer> requestsId;

    /**
     * Constructor to initialize Client object
     * @param entityId entity id
     * @param userStatus user's status
     * @param login user's login
     * @param password user's password
     * @param currency currency
     * @param requestsId list of client's request's id
     */
    public Client(int entityId, int userStatus,
                  String login, String password,
                  double currency, List<Integer> requestsId) {
        super(entityId, userStatus, login, password);
        this.currency = currency;
        this.requestsId = requestsId;
    }

    /**
     * get client's currency
     * @return client's currency
     */
    public double getCurrency() {
        return currency;
    }

    /**
     * set client's currency
     * @param currency new client's currency
     */
    public void setCurrency(double currency) {
        this.currency = currency;
    }

    /**
     * get list of request's id
     * @return list of request's id
     */
    public List<Integer> getRequestsId() {
        return requestsId;
    }

    /**
     * set list of request's id
     * @param requestsId new list of request's id
     */
    public void setRequestsId(List<Integer> requestsId) {
        this.requestsId = requestsId;
    }

    /**
     * compare Client objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Double.compare(client.currency, currency) == 0 &&
                Objects.equals(requestsId, client.requestsId);
    }

    /**
     * get client's hash code
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currency, requestsId);
    }
}
