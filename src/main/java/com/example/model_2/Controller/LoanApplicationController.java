package com.example.model_2.Controller;
import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Repository.CustomerRepository;
import com.example.model_2.Repository.LoanProductRepository;
import com.example.model_2.Service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/application")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;


    @GetMapping("/getId")
    public ResponseEntity<Integer> getCustomerId(@RequestParam String email) {
        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            return ResponseEntity.ok(customer.getCustomerid());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/applyLoan")
    public ResponseEntity<LoanApplication> applyForLoan(@RequestParam Integer customerId,
                                                        @RequestParam Integer productId,
                                                        @RequestParam Double loanAmount) {

        try {
            LoanApplication application = loanApplicationService.applyForLoan(customerId, productId, loanAmount);
            return ResponseEntity.ok(application);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LoanApplication>> getPendingLoanApplications() {
        List<LoanApplication> pendingApplications = loanApplicationService.getPendingLoanApplications();
        return ResponseEntity.ok(pendingApplications);
    }

    @PutMapping("/loan/process")
    public LoanApplication processLoanApplication(@RequestParam Integer id) {
        return loanApplicationService.processLoanApplication(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        List<LoanApplication> applications = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/approve/{applicationId}")
    public ResponseEntity<LoanApplication> approveLoanApplication(@PathVariable Integer applicationId) {
        LoanApplication updatedApplication = loanApplicationService.updateApprovalStatus(applicationId, LoanApplication.ApprovalStatus.APPROVED);
        if (updatedApplication != null) {
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/reject/{applicationId}")
    public ResponseEntity<LoanApplication> rejectLoanApplication(@PathVariable Integer applicationId) {
        LoanApplication updatedApplication = loanApplicationService.updateApprovalStatus(applicationId, LoanApplication.ApprovalStatus.REJECTED);
        if (updatedApplication != null) {
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.notFound().build();
    }

}