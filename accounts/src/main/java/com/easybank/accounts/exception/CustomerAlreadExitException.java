package com.easybank.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadExitException extends RuntimeException{

     public CustomerAlreadExitException(String message){
         super(message);
     }

}
