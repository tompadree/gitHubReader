package com.example.data.exception;

/**
 * Created by Tom on 3.11.2016..
 */

/**
 * Exception throw by the application when a string from search is not valid
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