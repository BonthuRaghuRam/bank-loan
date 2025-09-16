package com.example.model_2.Repository;


import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByCustomerId(Customer customer);
    List<LoanApplication> findByApprovalStatus(LoanApplication.ApprovalStatus approvalStatus);
//    List<LoanApplication> findByCustomerId(Integer customerId);
//    List<LoanApplication> findByCustomerEmail(String email);
}


