package com.example.the_open_book.exception;

/**
 * StaticResourceNotInitializeException
 */

public class ResourceNotFoundException extends  ApplicationException{

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message , Throwable cause) {
        super(message, cause);
    }


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }



}
