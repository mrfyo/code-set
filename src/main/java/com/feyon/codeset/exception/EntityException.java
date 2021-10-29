package com.feyon.codeset.exception;

/**
 * @author Feng Yong
 */
public class EntityException extends CodeSetException {
    public EntityException() {
    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
