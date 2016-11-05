package com.example.data.exception;

/**
 * Created by Tom on 5.11.2016..
 *
 * Exception throw by the application when a there is a api connection exception.
 */
public class ApiLimitException extends Exception {

    public ApiLimitException() {
        super();
    }

    public ApiLimitException(final String message) {
        super(message);
    }

    public ApiLimitException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApiLimitException(final Throwable cause) {
        super(cause);
    }
}

