package com.easybank.loan.service.impl;

import com.easybank.loan.constant.LoansConstants;
import com.easybank.loan.dto.LoansDTO;
import com.easybank.loan.entity.Loans;
import com.easybank.loan.exception.LoanAlreadyExistException;
import com.easybank.loan.exception.ResourceNotFound;
import com.easybank.loan.mapper.LoansMapper;
import com.easybank.loan.repository.ILoanRepository;
import com.easybank.loan.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    ILoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> loans=loanRepository.findByMobileNumber(mobileNumber);

        if(loans.isPresent()){
             throw new LoanAlreadyExistException("Loan Already Exist for this Number");
        }

        loanRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoansDTO fetchLoanDetails(String mobileNumber) {
       Loans loans=loanRepository.findByMobileNumber(mobileNumber)
               .orElseThrow(()->new ResourceNotFound("Loan not avaiable for this number"));
       return LoansMapper.mapToLoansDto(loans,new LoansDTO());

    }

    @Override
    public boolean updateLoanDetails(LoansDTO loansDTO) {
          Loans loans=loanRepository.findByLoanNumber(loansDTO.getLoanNumber())
                  .orElseThrow(()->new ResourceNotFound("Loan not avaiable for this loan number"));

         Loans updatedLoan= LoansMapper.mapToLoans(loansDTO,loans);
          Loans saveLoan=loanRepository.save(updatedLoan);

          return saveLoan.getId()>0?true:false;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans =loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFound("Loan not found for this mobile number"));

        loanRepository.deleteById(loans.getId());
        return  true;
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
