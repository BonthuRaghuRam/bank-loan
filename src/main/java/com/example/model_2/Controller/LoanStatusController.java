package com.example.model_2.Controller;

import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Service.LoanStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanStatusController {

    @Autowired
    private LoanStatusService loanStatusService;

    @GetMapping("/status/{email}")
    public ResponseEntity<List<LoanApplication>> getLoanStatusByEmail(@PathVariable String email) {
        List<LoanApplication> loans = loanStatusService.getLoansByCustomerEmail(email);
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }
}