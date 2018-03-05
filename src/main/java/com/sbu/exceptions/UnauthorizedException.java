package com.sbu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by nicholasgenco on 4/23/17.
 */

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{


    public UnauthorizedException(){}

    public UnauthorizedException(String message){
        super(message);
    }
}
