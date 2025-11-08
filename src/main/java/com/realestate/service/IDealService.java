package com.realestate.service;

import com.realestate.model.Customer;
import com.realestate.model.Deal;
import com.realestate.model.Property;
import java.util.List;

public interface IDealService {
    Deal addDeal(Property property, Customer customer, double dealCost);
    List<Deal> listAllDeals();
}
