package com.example.the_open_book.exception;

/**
 * ApplicationException
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException() {
    }


}
