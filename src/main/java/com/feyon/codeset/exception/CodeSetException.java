package com.feyon.codeset.exception;

/**
 * @author Feng Yong
 */
public class CodeSetException extends RuntimeException {

    public CodeSetException() {
    }

    public CodeSetException(String message) {
        super(message);
    }

    public CodeSetException(String message, Throwable cause) {
        super(message, cause);
    }
}
