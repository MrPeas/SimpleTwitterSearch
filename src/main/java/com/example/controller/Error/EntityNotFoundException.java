package com.example.controller.Error;

/**
 * Created by Janusz on 12.01.2017.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String messsage){
        super(messsage);
    }

    public EntityNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
