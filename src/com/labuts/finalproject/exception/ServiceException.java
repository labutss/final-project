package com.labuts.finalproject.exception;

/**
 * ServiceException class is used when some mistakes arise during using Service
 */
public class ServiceException extends Exception {
    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){ super(message, cause);}

    public ServiceException(Throwable cause){ super(cause);}
}
