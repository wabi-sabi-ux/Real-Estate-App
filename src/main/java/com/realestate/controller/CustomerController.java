package com.realestate.controller;

import com.realestate.model.Customer;
import com.realestate.model.Property;
import com.realestate.service.ICustomerService;
import com.realestate.service.IPropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ICustomerService customerService;
    private final IPropertyService propertyService;

    public CustomerController(ICustomerService customerService, IPropertyService propertyService) {
        this.customerService = customerService;
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.editCustomer(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.removeCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.viewCustomer(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.listAllCustomers());
    }

    @GetMapping("/{id}/properties")
    public ResponseEntity<List<com.realestate.model.Property>> getCustomerProperties(@PathVariable Long id) {
        Customer customer = customerService.viewCustomer(id);
        return ResponseEntity.ok(customer.getProperties());
    }

    @GetMapping("/{id}/deals")
    public ResponseEntity<List<com.realestate.model.Deal>> getCustomerDeals(@PathVariable Long id) {
        // This would require a dealService method to get deals by customer
        return ResponseEntity.ok(java.util.Collections.emptyList());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{customerId}/favorites/{propertyId}")
    public ResponseEntity<?> addToFavorites(@PathVariable Long customerId, @PathVariable Long propertyId) {
        try {
            Customer customer = customerService.viewCustomer(customerId);
            Property property = propertyService.viewProperty(propertyId);
            
            if (!customer.getProperties().contains(property)) {
                customer.getProperties().add(property);
                customerService.editCustomer(customer);
            }
            
            return ResponseEntity.ok("Property added to favorites");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add property to favorites: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{customerId}/favorites/{propertyId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable Long customerId, @PathVariable Long propertyId) {
        try {
            Customer customer = customerService.viewCustomer(customerId);
            Property property = propertyService.viewProperty(propertyId);
            
            customer.getProperties().remove(property);
            customerService.editCustomer(customer);
            
            return ResponseEntity.ok("Property removed from favorites");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove property from favorites: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{customerId}/favorites/{propertyId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long customerId, @PathVariable Long propertyId) {
        try {
            Customer customer = customerService.viewCustomer(customerId);
            Property property = propertyService.viewProperty(propertyId);
            
            boolean isFavorite = customer.getProperties().contains(property);
            return ResponseEntity.ok(isFavorite);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
