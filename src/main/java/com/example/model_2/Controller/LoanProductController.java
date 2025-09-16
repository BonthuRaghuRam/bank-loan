package com.example.model_2.Controller;

import com.example.model_2.Model.LoanProduct;
import com.example.model_2.Service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/api/products")
public class LoanProductController {
    @Autowired
    private LoanProductService loanProductService;


    @PostMapping("addLoan")
    public LoanProduct addProduct(@RequestBody LoanProduct product) {
        return loanProductService.saveLoanProduct(product);
    }
    @GetMapping("/all")
    public List<LoanProduct> fetchAllLoanProducts() {
        return loanProductService.getAllProducts();
    }

    @GetMapping("admin-loan-list")
    public List<LoanProduct> getAllProducts() {
        return loanProductService.getAllProducts();
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        loanProductService.deleteLoanProduct(productId);
    }

    @PutMapping("/update/{productId}")
    public LoanProduct updateProduct(@PathVariable LoanProduct productId) {
        return loanProductService.saveLoanProduct(productId);
    }



}
