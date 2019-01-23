package com.labuts.finalproject.entity;

import java.util.Objects;

/**
 * Create User class from Entity
 * User class is used for storing information of user
 */
public class User extends Entity {
    /**
     * User's status
     */
    private int userStatus;
    /**
     * User's login
     */
    private String login;
    /**
     * User's password
     */
    private String password;

    /**
     * Constructor to initialize user object
     * @param entityId entity id
     * @param userStatus initial user status
     * @param login initial user login
     * @param password initial user password
     */
    public User(int entityId, int userStatus, String login, String password) {
        super(entityId);
        this.userStatus = userStatus;
        this.login = login;
        this.password = password;
    }

    /**
     * get user's status
     * @return user's status
     */
    public int getUserStatus() {
        return userStatus;
    }

    /**
     * set user's status
     * @param userStatus new user's status
     */
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * get user's login
     * @return user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * set user's login
     * @param login new user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * get user's password
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set user's password
     * @param password new user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * compare User objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return userStatus == user.userStatus &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    /**
     * get user's hash code
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userStatus, login, password);
    }
}

