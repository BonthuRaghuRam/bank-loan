package com.example.model_2.Controller;

import com.example.model_2.Model.Customer;
import com.example.model_2.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable int id) {
        return ResponseEntity.ok(customerService.getCustomerDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerProfile(@PathVariable int id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomerProfile(id, customer));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.findByEmail(email);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Customer> approveApplication(@PathVariable int id) {
        Customer approvedCustomer = customerService.approveApplication(id);
        if (approvedCustomer != null) {
            return ResponseEntity.ok(approvedCustomer);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Customer> rejectApplication(@PathVariable int id) {
        Customer rejectedCustomer = customerService.rejectApplication(id);
        if (rejectedCustomer != null) {
            return ResponseEntity.ok(rejectedCustomer);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

//        System.out.println(email);
//        System.out.println(password);
        Customer customer = customerService.login(email, password);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

