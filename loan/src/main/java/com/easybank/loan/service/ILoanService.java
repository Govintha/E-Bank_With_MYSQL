package com.easybank.loan.service;


import com.easybank.loan.dto.LoansDTO;

public interface ILoanService {

    void createLoan(String mobileNumber);
    LoansDTO fetchLoanDetails(String mobileNumber);
    boolean updateLoanDetails(LoansDTO loansDTO);
    boolean deleteLoan(String mobileNumber);
}
