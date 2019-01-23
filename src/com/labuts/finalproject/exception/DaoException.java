package com.labuts.finalproject.exception;

/**
 * DaoException class is used when some mistakes arise during using Dao
 */
public class DaoException extends Exception {
    public DaoException(){}

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Throwable cause){ super(message, cause);}

    public DaoException(Throwable cause){ super(cause);}
}
