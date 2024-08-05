package ru.kata.spring.boot_security.demo.service.exception;

public class UserUpdateException extends RuntimeException{

    public UserUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
