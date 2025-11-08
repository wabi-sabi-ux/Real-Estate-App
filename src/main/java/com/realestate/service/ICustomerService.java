package com.realestate.service;

import com.realestate.model.Customer;
import java.util.List;

public interface ICustomerService {
    Customer addCustomer(Customer customer);
    Customer editCustomer(Customer customer);
    Customer removeCustomer(Long custId);
    Customer viewCustomer(Long custId);
    List<Customer> listAllCustomers();
}
