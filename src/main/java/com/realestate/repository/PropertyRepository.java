package com.realestate.repository;

import com.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaSpecificationExecutor gives us dynamic filtering (by criteria).
 */
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> { }
