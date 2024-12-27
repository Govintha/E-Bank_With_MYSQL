package com.easybank.loan.controller;

import com.easybank.loan.constant.LoansConstants;
import com.easybank.loan.dto.LoansContactInfoDto;
import com.easybank.loan.dto.LoansDTO;
import com.easybank.loan.dto.ResponseDTO;
import com.easybank.loan.service.ILoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/loans",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LoansConstroller {

    private ILoanService loanService;

    public LoansConstroller(ILoanService iLoansService) {
        this.loanService = iLoansService;
    }

//    @Value("${build.version}")
//    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createLoan(@RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {

        LoansDTO loansDTO=loanService.fetchLoanDetails(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDTO);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateLoanDetails(@Valid  @RequestBody LoansDTO loansDTO){

        boolean isUpdated=loanService.updateLoanDetails(loansDTO);

        if(isUpdated){
             return  ResponseEntity
                     .status(HttpStatus.OK)
                     .body(new ResponseDTO(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }else{
            return  ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteLoanDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {

        boolean isUpdated=loanService.deleteLoan(mobileNumber);

        if(isUpdated){
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }else{
            return  ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
        }
    }

//    @GetMapping("/build-info")
//    public ResponseEntity<String> getBuildInfo() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(buildVersion);
//    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }


    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }

}
