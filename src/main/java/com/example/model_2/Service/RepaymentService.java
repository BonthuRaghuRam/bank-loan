package com.example.model_2.Service;

import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Model.Repayment;
import com.example.model_2.Repository.CustomerRepository;
import com.example.model_2.Repository.LoanApplicationRepository;
import com.example.model_2.Repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public Optional<Repayment> getOutstandingAmount(Integer applicationId) {
        return repaymentRepository.findByRemainingAmount(applicationId);
    }

    public Optional<Repayment> getDueDate(Integer applicationId) {
        return repaymentRepository.findByDueDate(applicationId);
    }

}



