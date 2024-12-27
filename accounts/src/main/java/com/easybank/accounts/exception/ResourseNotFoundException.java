package com.easybank.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends RuntimeException{

     public ResourseNotFoundException(String resource, String fieldName,String fieldValue){
         super(String.format("%s not found with given input data %s: %s",resource,fieldName,fieldValue));

     }

}
