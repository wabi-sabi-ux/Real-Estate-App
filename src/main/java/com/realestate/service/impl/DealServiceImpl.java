package com.realestate.service.impl;

import com.realestate.exception.NotFoundException;
import com.realestate.model.Customer;
import com.realestate.model.Deal;
import com.realestate.model.Property;
import com.realestate.repository.CustomerRepository;
import com.realestate.repository.DealRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.IDealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DealServiceImpl implements IDealService {

    private final DealRepository dealRepository;
    private final PropertyRepository propertyRepository;
    private final CustomerRepository customerRepository;

    public DealServiceImpl(DealRepository dealRepository,
                           PropertyRepository propertyRepository,
                           CustomerRepository customerRepository) {
        this.dealRepository = dealRepository;
        this.propertyRepository = propertyRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Deal addDeal(Property property, Customer customer, double dealCost) {
        Property prop = propertyRepository.findById(property.getPropId())
                .orElseThrow(() -> new NotFoundException("Property not found: " + property.getPropId()));
        if (!prop.isStatus()) {
            throw new IllegalStateException("Property already sold/rented.");
        }

        Customer cust = customerRepository.findById(customer.getCustId())
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customer.getCustId()));

        // Build Deal manually (no Lombok.builder)
        Deal deal = new Deal();
        deal.setDealDate(LocalDate.now());
        deal.setDealCost(dealCost);
        deal.setProperty(prop);
        deal.setCustomer(cust);

        // Mark property unavailable
        prop.setStatus(false);

        // Track ownership/rental
        cust.getProperties().add(prop);

        Deal saved = dealRepository.save(deal);
        propertyRepository.save(prop);
        customerRepository.save(cust);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deal> listAllDeals() {
        return dealRepository.findAll();
    }
}
