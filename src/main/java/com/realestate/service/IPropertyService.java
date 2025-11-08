package com.realestate.service;

import com.realestate.dto.PropertyCriteria;
import com.realestate.model.Property;

import java.util.List;

public interface IPropertyService {
    Property addProperty(Property property);
    Property editProperty(Property property);
    Property removeProperty(Long propId);
    Property viewProperty(Long propId);
    List<Property> listAllProperties();
    List<Property> listPropertyByCriteria(PropertyCriteria criteria);
}
