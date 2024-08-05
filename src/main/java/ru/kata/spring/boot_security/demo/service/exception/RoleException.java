package ru.kata.spring.boot_security.demo.service.exception;

public class RoleException extends RuntimeException{
    public RoleException(String message){
        super(message);
    }
}
