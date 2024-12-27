package com.easybank.accounts.controller;

import com.easybank.accounts.constants.AccountsConstants;
import com.easybank.accounts.dto.AccountsContactInfoDto;
import com.easybank.accounts.dto.CustomerDTO;
import com.easybank.accounts.dto.ResponseDTO;
import com.easybank.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api",produces = {MediaType.APPLICATION_JSON_VALUE}) // this ensure my return type is always JOSN
@Validated // this is applies all API's
public class AccountController {


    private final IAccountService iAccountsService;

    @Autowired
    public AccountController(IAccountService iAccountsService) {

        this.iAccountsService = iAccountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;


   @PostMapping("/create")
   public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){

       iAccountsService.creatCustomer(customerDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED) // this is header
                .body(new ResponseDTO(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
   }

   @GetMapping("fetch")
   public ResponseEntity<CustomerDTO> fetchAccountDeatils(@RequestParam
                                                             @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                             String mobileNumber){



        CustomerDTO customerDTO= iAccountsService.fetchCustomerDetails(mobileNumber);

        return   ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);


   }

   @PutMapping("/update")
   public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO){

       boolean isUpdated  = iAccountsService.updateAccountDetails(customerDTO);

       return isUpdated?
               ResponseEntity
                       .status(HttpStatus.OK)
                       .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200))
               :
               ResponseEntity // Resource available  but due to some reason not able update
                       .status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(new ResponseDTO(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));



   }

   @DeleteMapping("/delete")
   public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                       String mobileNumber){

       boolean isDelete = iAccountsService.deleteAccount(mobileNumber);

      return isDelete ? ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200))
                     :
                       ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));


   }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
