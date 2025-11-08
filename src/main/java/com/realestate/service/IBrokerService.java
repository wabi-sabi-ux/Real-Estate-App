package com.realestate.service;

import com.realestate.model.Broker;

import java.util.List;

public interface IBrokerService {
    Broker addBroker(Broker broker);
    Broker editBroker(Broker broker);
    Broker removeBroker(Long broId);
    Broker viewBroker(Long broId);
    List<Broker> listAllBrokers();
}
