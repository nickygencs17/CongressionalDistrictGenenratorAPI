package com.sbu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by nicholasgenco on 4/23/17.
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception{
    public BadRequestException(){}
    public BadRequestException(String message){
        super(message);
    }
}
