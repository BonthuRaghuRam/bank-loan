package com.example.model_2.Repository;

import com.example.model_2.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmailAndPassword(String email, String password);

    Customer findByEmail(String email);
    // New custom query method to find customers by KYC status
    List<Customer> findByKycStatus(Customer.KycStatus kycStatus);
}

