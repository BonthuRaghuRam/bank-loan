package com.example.model_2.Service;

import com.example.model_2.Model.LoanProduct;
import com.example.model_2.Repository.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanProductService {

    @Autowired
    private LoanProductRepository loanProductRepository;

    public LoanProduct saveLoanProduct(LoanProduct loanProduct) {

        return loanProductRepository.save(loanProduct);
    }


    public List<LoanProduct> getAllProducts() {

        return loanProductRepository.findAll();
    }
    public void deleteLoanProduct(Long productId) {
        loanProductRepository.deleteById(productId);
    }


}






