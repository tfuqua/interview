package com.interview.exception;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(long id){
        super("Could not find car " + id);
    }
}
