package com.example.model_2.Controller;

import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Model.Repayment;
import com.example.model_2.Repository.CustomerRepository;
import com.example.model_2.Repository.LoanApplicationRepository;
import com.example.model_2.Repository.RepaymentRepository;
import com.example.model_2.Service.CustomerService;
import com.example.model_2.Service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins ="*")
@Controller
@RequestMapping("/api/repayment")
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;


    private Integer id;


    @GetMapping("/email/{email}")
    public ResponseEntity<List<LoanApplication>> getLoanApplicationsByCustomerEmail(@PathVariable String email) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<LoanApplication> loanApplications = loanApplicationRepository.findByCustomerId(customer);

        if (loanApplications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        }

        return ResponseEntity.ok(loanApplications);
    }


    @GetMapping("/outstanding/{applicationId}")
    public ResponseEntity<Repayment> getOutstandingAmount(@PathVariable Integer applicationId) {
        Optional<Repayment> repaymentOpt = repaymentService.getOutstandingAmount(applicationId);
        return repaymentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/date/{applicationId}")
    public ResponseEntity<Repayment> getDueDate(@PathVariable Integer applicationId) {
        Optional<Repayment> repaymentOpt = repaymentService.getDueDate(applicationId);
        return repaymentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PutMapping("/pay/{applicationId}")
    public ResponseEntity<String> makePayment(@PathVariable Integer applicationId) {
        try {
            Optional<LoanApplication> loanApplicationOpt = loanApplicationRepository.findById(Long.valueOf(applicationId));

            if (loanApplicationOpt.isPresent()) {
                LoanApplication application = loanApplicationOpt.get();
                List<Repayment> repayments = repaymentRepository.findByApplicationId(application);

                if (!repayments.isEmpty()) {
                    for (Repayment repayment : repayments) {
                        repayment.setPaymentStatus(Repayment.PaymentStatus.COMPLETED);
                        repaymentRepository.save(repayment);
                    }
                    return ResponseEntity.ok("Payment processed successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No repayments found for this application");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment: " + e.getMessage());
        }
    }

}
















