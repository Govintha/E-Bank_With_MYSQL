package com.easybank.accounts.service.impl;

import com.easybank.accounts.constants.AccountsConstants;
import com.easybank.accounts.dto.AccountDTO;
import com.easybank.accounts.dto.CustomerDTO;
import com.easybank.accounts.entity.Accounts;
import com.easybank.accounts.entity.Customers;
import com.easybank.accounts.exception.CustomerAlreadExitException;
import com.easybank.accounts.exception.ResourseNotFoundException;
import com.easybank.accounts.mapper.AccountMapper;
import com.easybank.accounts.mapper.CustomerMapper;
import com.easybank.accounts.repository.AccountRepository;
import com.easybank.accounts.repository.CustomerRepository;
import com.easybank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    public void creatCustomer(CustomerDTO custsomerDTO) {
        Optional<Customers> exitCustomer=customerRepository.findByMobileNumber(custsomerDTO.getMobileNumber());
        if(exitCustomer.isPresent()){
             throw new CustomerAlreadExitException("Customer Already Present with "+custsomerDTO.getMobileNumber());
        }
        Customers customer= CustomerMapper.mapToCustomer(custsomerDTO,new Customers());
        Customers saveCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(saveCustomer));
    }


    private Accounts createNewAccount(Customers customer){

        Accounts newAccount=new Accounts();
        Long accountNumber=1000000000L+new Random().nextInt(900000000);

        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCustomerId(customer.getCustomerId());
        return  newAccount;
    }

    @Override
    public CustomerDTO fetchCustomerDetails(String mobileNumber) {
        Customers byMobileNumber = customerRepository
                                        .findByMobileNumber(mobileNumber)
                                        .orElseThrow(()-> new ResourseNotFoundException("Customer","MobileNumber",mobileNumber));
        Accounts accounts = accountRepository
                                .findByCustomerId(byMobileNumber.getCustomerId())
                                .orElseThrow(() -> new ResourseNotFoundException("Account", "CustomerId", byMobileNumber.getCustomerId().toString()));

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(byMobileNumber, new CustomerDTO());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountsDto(accounts, new AccountDTO()));
        return customerDTO;

    }

    @Override
    public boolean updateAccountDetails(CustomerDTO customerDTO) {
        boolean isUpdated=false;

        AccountDTO accountDTO=customerDTO.getAccountDTO();
        if(accountDTO!=null){
            Accounts accounts = accountRepository.findById(accountDTO.getAccountNumber())
                                                 .orElseThrow(() -> new ResourseNotFoundException("Account", "AccountNumber", accountDTO.getAccountNumber().toString()));

            AccountMapper.mapToAccounts(accountDTO,accounts);
            // in customers object id PK there i will update if PK not it will insert
            Accounts save = accountRepository.save(accounts);
            Customers customers = customerRepository.findById(save.getCustomerId())
                                                    .orElseThrow(() -> new ResourseNotFoundException("Customer", "CustomerId", save.getCustomerId().toString()));
            CustomerMapper.mapToCustomer(customerDTO,customers);
            // in customers object id PK there i will update if PK not it will insert
            customerRepository.save(customers);
            isUpdated=true;
        }
        return isUpdated;



    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customers customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourseNotFoundException("Customer", "MobileNumber", mobileNumber));
        Accounts account = accountRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourseNotFoundException("Account", "AccountID", customer.getCustomerId().toString()));

        accountRepository.deleteByCustomerId(account.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
         return true;

    }

}
