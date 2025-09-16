package com.example.model_2.Service;

import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Model.LoanProduct;
import com.example.model_2.Model.Repayment;
import com.example.model_2.Repository.CustomerRepository;
import com.example.model_2.Repository.LoanApplicationRepository;
import com.example.model_2.Repository.LoanProductRepository;
import com.example.model_2.Repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    @Autowired
    private RepaymentRepository repaymentRepository;


    public LoanApplication applyForLoan(Integer customerId, Integer productId, Double loanAmount) {
        // Get customer and product entities from repositories
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Optional<LoanProduct> productOpt = loanProductRepository.findById(Long.valueOf(productId));

        // Check if both entities exist
        if (customerOpt.isEmpty() || productOpt.isEmpty()) {
            throw new RuntimeException("Customer or product not found");
        }

        Customer customer = customerOpt.get();
        LoanProduct product = productOpt.get();

        // Create and populate the loan application
        LoanApplication loanApp = LoanApplication.builder()
                .customerId(customer)                // Set the customer entity
                .loanProductId(product)              // Set the product entity
                .loanAmount(loanAmount)
                .applicationDate(LocalDate.now())
                .approvalStatus(LoanApplication.ApprovalStatus.PENDING)
                .build();

        return loanApplicationRepository.save(loanApp);
    }


    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    public List<LoanApplication> getPendingLoanApplications() {
        return loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.PENDING);
    }

    public LoanApplication processLoanApplication(Integer id) {

        LoanApplication loanApp = loanApplicationRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Loan Application not found"));

        LoanProduct product = loanApp.getLoanProductId();
        Double amount =  loanApp.getLoanAmount();

        if(amount>=product.getMinAmount() && amount<=product.getMaxAmount()){
            loanApp.setApprovalStatus(LoanApplication.ApprovalStatus.APPROVED);
        }

        else{
            loanApp.setApprovalStatus(LoanApplication.ApprovalStatus.REJECTED);
        }

        return loanApplicationRepository.save(loanApp);
    }

//    public LoanApplication updateApprovalStatus(Integer applicationId, LoanApplication.ApprovalStatus newStatus) {
//        // Cast the Integer to a Long to match the repository method signature
//        Optional<LoanApplication> optionalApplication = loanApplicationRepository.findById(Long.valueOf(applicationId));
//
//        if (optionalApplication.isPresent()) {
//            LoanApplication application = optionalApplication.get();
//            application.setApprovalStatus(newStatus);
//            return loanApplicationRepository.save(application);
//        }
//        return null;
//    }
public LoanApplication updateApprovalStatus(Integer applicationId, LoanApplication.ApprovalStatus newStatus) {
    // Cast the Integer to a Long to match the repository method signature
    Optional<LoanApplication> optionalApplication = loanApplicationRepository.findById(Long.valueOf(applicationId));

    if (optionalApplication.isPresent()) {
        LoanApplication application = optionalApplication.get();
        application.setApprovalStatus(newStatus);
        LoanApplication savedApplication = loanApplicationRepository.save(application);

        // If the loan is approved, create a repayment record
        if (newStatus == LoanApplication.ApprovalStatus.APPROVED) {
            createRepaymentRecord(savedApplication);
        }

        return savedApplication;
    }
    return null;
}

    private void createRepaymentRecord(LoanApplication application) {
        // Get loan details
        LoanProduct product = application.getLoanProductId();
        Double loanAmount = application.getLoanAmount();

        // Calculate repayment details (simple implementation)
        LocalDate dueDate = LocalDate.now().plusMonths(product.getTenure());
        Double amountDue = loanAmount + (loanAmount * product.getInterestRate() / 100);

        // Create repayment record
        Repayment repayment = Repayment.builder()
                .applicationId(application)
                .dueDate(dueDate)
                .amountDue(amountDue)
                .paymentStatus(Repayment.PaymentStatus.PENDING)
                .build();

        // Save repayment record
        repaymentRepository.save(repayment);
    }



}