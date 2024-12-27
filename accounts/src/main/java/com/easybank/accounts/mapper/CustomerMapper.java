package com.easybank.accounts.mapper;

import com.easybank.accounts.dto.CustomerDTO;
import com.easybank.accounts.entity.Customers;

public class CustomerMapper {

    public static CustomerDTO mapToCustomerDto(Customers customer, CustomerDTO customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customers mapToCustomer(CustomerDTO customerDto, Customers customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}