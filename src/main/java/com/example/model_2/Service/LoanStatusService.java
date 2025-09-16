package com.example.model_2.Service;

import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Repository.CustomerRepository;
import com.example.model_2.Repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LoanStatusService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<LoanApplication> getLoansByCustomerEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return loanApplicationRepository.findByCustomerId(customer);
        }
        return Collections.emptyList();
    }
}