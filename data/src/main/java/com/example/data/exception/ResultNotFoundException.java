package com.example.data.exception;

/**
 * Created by Tom on 28.10.2016..
 */

/**
 * Exception throw by the application when a Result search can't return a valid result.
 */
public class ResultNotFoundException extends Exception {

    public ResultNotFoundException() {
        super();
    }

    public ResultNotFoundException(final String message) {
        super(message);
    }

    public ResultNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResultNotFoundException(final Throwable cause) {
        super(cause);
    }
}

