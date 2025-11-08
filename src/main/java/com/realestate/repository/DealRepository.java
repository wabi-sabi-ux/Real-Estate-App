package com.realestate.repository;

import com.realestate.model.Customer;
import com.realestate.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByCustomer(Customer customer);
}
