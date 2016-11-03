package com.example.domain.exception;

/**
 * Created by Tom on 28.10.2016..
 */

/**
 * Interface to represent a wrapper around an {@link java.lang.Exception} to manage errors.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
