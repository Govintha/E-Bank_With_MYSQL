package com.easybank.loan.exception;

public class LoanAlreadyExistException extends RuntimeException{

    public LoanAlreadyExistException(String messgae){
        super(messgae);
    }

}
