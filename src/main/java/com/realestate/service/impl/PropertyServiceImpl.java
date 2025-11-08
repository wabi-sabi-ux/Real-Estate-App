package com.realestate.service.impl;

import com.realestate.dto.PropertyCriteria;
import com.realestate.exception.NotFoundException;
import com.realestate.model.Property;
import com.realestate.repository.PropertyRepository;
import com.realestate.search.PropertySpecifications;
import com.realestate.service.IPropertyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PropertyServiceImpl implements IPropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Property editProperty(Property property) {
        if (property.getPropId() == null || !propertyRepository.existsById(property.getPropId())) {
            throw new NotFoundException("Property not found: " + property.getPropId());
        }
        return propertyRepository.save(property);
    }

    @Override
    public Property removeProperty(Long propId) {
        Property existing = propertyRepository.findById(propId)
                .orElseThrow(() -> new NotFoundException("Property not found: " + propId));
        propertyRepository.delete(existing);
        return existing;
    }

    @Override
    @Transactional(readOnly = true)
    public Property viewProperty(Long propId) {
        return propertyRepository.findById(propId)
                .orElseThrow(() -> new NotFoundException("Property not found: " + propId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> listAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> listPropertyByCriteria(PropertyCriteria criteria) {
        Specification<Property> spec = PropertySpecifications.byCriteria(criteria);
        return propertyRepository.findAll(spec);
    }
}
