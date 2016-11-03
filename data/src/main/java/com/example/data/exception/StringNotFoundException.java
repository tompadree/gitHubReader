package com.example.data.exception;

/**
 * Created by Tom on 3.11.2016..
 */

/**
 * Exception throw by the application when a Result search can't return a valid result.
 */
public class StringNotFoundException extends Exception {

    public StringNotFoundException() {
        super();
    }

    public StringNotFoundException(final String message) {
        super(message);
    }

    public StringNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public StringNotFoundException(final Throwable cause) {
        super(cause);
    }
}