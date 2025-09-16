package com.example.model_2.Service;


import com.example.model_2.Model.Customer;
import com.example.model_2.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerServiceInterface{

    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        customer.setKycStatus(Customer.KycStatus.PENDING);
        return customerRepository.save(customer);
    }

    public Customer getCustomerDetails(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer updateCustomerProfile(int id, Customer updatedCustomer) {
        Customer customer = getCustomerDetails(id);
        if (customer != null) {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setKycStatus(updatedCustomer.getKycStatus());
            return customerRepository.save(customer);
        }
        return null;
    }


    public Customer login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // New method to get all customers with PENDING KYC status
    public List<Customer> getPendingKycApplications() {
        return customerRepository.findByKycStatus(Customer.KycStatus.PENDING);
    }

    // New method for admin to approve an application
    public Customer approveApplication(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getKycStatus() == Customer.KycStatus.PENDING) {
            customer.setKycStatus(Customer.KycStatus.VERIFIED);
            return customerRepository.save(customer);
        }
        return null;
    }
    // New method for admin to reject an application
    public Customer rejectApplication(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null && customer.getKycStatus() == Customer.KycStatus.PENDING) {
            customer.setKycStatus(Customer.KycStatus.REJECTED);
            return customerRepository.save(customer);
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

}

