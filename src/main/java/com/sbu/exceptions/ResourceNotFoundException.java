package com.sbu.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    String type;
    
    public ResourceNotFoundException(String stream) {

    }
    
    @Override
    public String getLocalizedMessage(){
        return type + "constant";
    }
}
