package com.feyon.codeset.exception;

/**
 * @author Feng Yong
 */
public class AdminException extends CodeSetException {

    public AdminException() {
    }

    public AdminException(String message) {
        super(message);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
