package com.realestate.service.impl;

import com.realestate.exception.NotFoundException;
import com.realestate.model.Broker;
import com.realestate.repository.BrokerRepository;
import com.realestate.service.IBrokerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrokerServiceImpl implements IBrokerService {

    private final BrokerRepository brokerRepository;

    public BrokerServiceImpl(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    @Override
    public Broker addBroker(Broker broker) {
        return brokerRepository.save(broker);
    }

    @Override
    public Broker editBroker(Broker broker) {
        if (broker.getBroId() == null || !brokerRepository.existsById(broker.getBroId())) {
            throw new NotFoundException("Broker not found: " + broker.getBroId());
        }
        return brokerRepository.save(broker);
    }

    @Override
    public Broker removeBroker(Long broId) {
        Broker existing = brokerRepository.findById(broId)
                .orElseThrow(() -> new NotFoundException("Broker not found: " + broId));
        brokerRepository.delete(existing);
        return existing;
    }

    @Override
    @Transactional(readOnly = true)
    public Broker viewBroker(Long broId) {
        return brokerRepository.findById(broId)
                .orElseThrow(() -> new NotFoundException("Broker not found: " + broId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Broker> listAllBrokers() {
        return brokerRepository.findAll();
    }
}
