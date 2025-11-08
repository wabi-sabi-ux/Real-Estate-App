package com.realestate.controller;

import com.realestate.model.Customer;
import com.realestate.model.Deal;
import com.realestate.model.Property;
import com.realestate.repository.CustomerRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.IDealService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final IDealService dealService;
    private final PropertyRepository propertyRepository;
    private final CustomerRepository customerRepository;

    public DealController(IDealService dealService,
                          PropertyRepository propertyRepository,
                          CustomerRepository customerRepository) {
        this.dealService = dealService;
        this.propertyRepository = propertyRepository;
        this.customerRepository = customerRepository;
    }

    // Buy/Rent: POST /api/deals?propertyId=1&customerId=2&price=4500000
    @PreAuthorize("hasRole('Customer')")
    @PostMapping
    public ResponseEntity<?> createDeal(
            @RequestParam Long propertyId,
            @RequestParam Long customerId,
            @RequestParam(name = "price") double dealCost
    ) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found: " + propertyId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        Deal saved = dealService.addDeal(property, customer, dealCost);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Deal>> listDeals() {
        return ResponseEntity.ok(dealService.listAllDeals());
    }
}
