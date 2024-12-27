package com.easybank.accounts.service;

import com.easybank.accounts.dto.CustomerDTO;

public interface IAccountService  {

    void creatCustomer(CustomerDTO customerDTO);
    CustomerDTO fetchCustomerDetails(String mobileNumber);
    boolean updateAccountDetails(CustomerDTO customerDTO);
    boolean deleteAccount(String mobileNumber);
}
