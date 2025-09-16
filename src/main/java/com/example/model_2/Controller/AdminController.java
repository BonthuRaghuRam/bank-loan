package com.example.model_2.Controller;

import com.example.model_2.Model.Customer;
import com.example.model_2.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/applications/pending")
    public ResponseEntity<List<Customer>> getPendingApplications() {
        List<Customer> pendingCustomers = customerService.getPendingKycApplications();
        return ResponseEntity.ok(pendingCustomers);
    }

    @PostMapping("/applications/approve/{id}")
    public ResponseEntity<Customer> approveApplication(@PathVariable int id) {
        Customer approvedCustomer = customerService.approveApplication(id);
        if (approvedCustomer != null) {
            return ResponseEntity.ok(approvedCustomer);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/applications/reject/{id}")
    public ResponseEntity<Customer> rejectApplication(@PathVariable int id) {
        Customer rejectedCustomer = customerService.rejectApplication(id);
        if (rejectedCustomer != null) {
            return ResponseEntity.ok(rejectedCustomer);
        }
        return ResponseEntity.notFound().build();
    }
}
