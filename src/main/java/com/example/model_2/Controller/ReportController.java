package com.example.model_2.Controller;

import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping("/loan-report")
    public ResponseEntity<Map<String, Object>> generateLoanReport() {
        List<LoanApplication> allApplications = loanApplicationService.getAllLoanApplications();

        long totalApplications = allApplications.size();
        long approvedLoans = allApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.APPROVED)
                .count();
        long pendingLoans = allApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.PENDING)
                .count();
        long rejectedLoans = allApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.REJECTED)
                .count();

        // Map each application to a simplified customer detail structure
        List<Map<String, Object>> customerDetails = allApplications.stream()
                .map(app -> Map.of(
                        "applicationId", app.getApplicationId(),
                        "customerName", app.getCustomerId(),
//                        "email", app.customerId.getEmail(),
                        "loanAmount", app.getLoanAmount(),
                        "approvalStatus", app.getApprovalStatus().toString()
                ))
                .collect(Collectors.toList());

        Map<String, Object> report = Map.of(
                "totalApplications", totalApplications,
                "approvedLoans", approvedLoans,
                "pendingLoans", pendingLoans,
                "rejectedLoans", rejectedLoans,
                "customerDetails", customerDetails
        );

        return ResponseEntity.ok(report);
    }

}
