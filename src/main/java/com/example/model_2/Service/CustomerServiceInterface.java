package com.example.model_2.Service;


import com.example.model_2.Model.Customer;

public interface CustomerServiceInterface {

    Customer registerCustomer(Customer customer);

    Customer getCustomerDetails(int id);

    Customer updateCustomerProfile(int id, Customer updatedCustomer);

    Customer login(String email, String password);

    Customer findByEmail(String email);
}
