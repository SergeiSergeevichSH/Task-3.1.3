package ru.itmentor.spring.boot_security.demo.exception_hendling;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String mass){
        super(mass);
    }
}
