package com.ms.user.exception.customException;

public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(Integer id, String var) {
        super(var + " not found with id: " + id);
    }
}