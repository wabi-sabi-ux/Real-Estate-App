package com.realestate.service.impl;

import com.realestate.exception.NotFoundException;
import com.realestate.model.Customer;
import com.realestate.repository.CustomerRepository;
import com.realestate.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer editCustomer(Customer customer) {
        if (customer.getCustId() == null || !customerRepository.existsById(customer.getCustId())) {
            throw new NotFoundException("Customer not found: " + customer.getCustId());
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer removeCustomer(Long custId) {
        Customer existing = customerRepository.findById(custId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + custId));
        customerRepository.delete(existing);
        return existing;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer viewCustomer(Long custId) {
        return customerRepository.findById(custId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + custId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }
}
