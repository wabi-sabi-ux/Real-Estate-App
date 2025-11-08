package com.realestate.controller;

import com.realestate.model.Broker;
import com.realestate.model.BrokerRating;
import com.realestate.model.Customer;
import com.realestate.repository.BrokerRepository;
import com.realestate.repository.CustomerRepository;
import com.realestate.service.IBrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brokers")
public class BrokerController {

    private final IBrokerService brokerService;
    private final CustomerRepository customerRepo;
    private final BrokerRepository brokerRepository;

    public BrokerController(IBrokerService brokerService,
                          CustomerRepository customerRepo,
                          BrokerRepository brokerRepository) {
        this.brokerService = brokerService;
        this.customerRepo = customerRepo;
        this.brokerRepository = brokerRepository;
    }

    @PostMapping
    public ResponseEntity<Broker> addBroker(@RequestBody Broker broker) {
        return ResponseEntity.ok(brokerService.addBroker(broker));
    }

    @PutMapping
    public ResponseEntity<Broker> updateBroker(@RequestBody Broker broker) {
        return ResponseEntity.ok(brokerService.editBroker(broker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBroker(@PathVariable Long id) {
        brokerService.removeBroker(id);
        return ResponseEntity.ok("Broker deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Broker> getBroker(@PathVariable Long id) {
        return ResponseEntity.ok(brokerService.viewBroker(id));
    }

    @GetMapping
    public ResponseEntity<List<Broker>> getAllBrokers() {
        return ResponseEntity.ok(brokerService.listAllBrokers());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{brokerId}/ratings")
    public ResponseEntity<?> rateBroker(@PathVariable Long brokerId,
                                      @RequestBody BrokerRating rating,
                                      Authentication auth) {
        Broker broker = brokerService.viewBroker(brokerId);
        Customer customer = customerRepo.findByUserEmail(auth.getName())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        rating.setBroker(broker);
        rating.setCustomer(customer);
        
        if (rating.getRating() < 1 || rating.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
        }

        // Update broker's rating stats
        broker.getRatings().add(rating);
        double totalRating = broker.getAvgRating() * broker.getRatingCount() + rating.getRating();
        broker.setRatingCount(broker.getRatingCount() + 1);
        broker.setAvgRating(totalRating / broker.getRatingCount());
        
        brokerService.editBroker(broker);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{brokerId}/ratings")
    public ResponseEntity<?> getBrokerRatings(@PathVariable Long brokerId) {
        Broker broker = brokerService.viewBroker(brokerId);
        return ResponseEntity.ok(broker.getRatings());
    }

    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRatedBrokers() {
        return ResponseEntity.ok(brokerRepository.findAllOrderByRatingDesc());
    }
}
