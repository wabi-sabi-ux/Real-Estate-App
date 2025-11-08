package com.realestate.search;

import com.realestate.dto.PropertyCriteria;
import com.realestate.model.Property;
import org.springframework.data.jpa.domain.Specification;

/**
 * Builds a JPA Specification from PropertyCriteria.
 * Each clause is optional; nulls are ignored.
 */
public final class PropertySpecifications {

    private PropertySpecifications() {}

    public static Specification<Property> byCriteria(PropertyCriteria c) {
        return Specification
                .where(configEquals(c))
                .and(offerEquals(c))
                .and(cityEquals(c))
                .and(costBetween(c))
                .and(onlyAvailable());
    }

    private static Specification<Property> configEquals(PropertyCriteria c) {
        return (root, q, cb) ->
                c.getConfig() == null ? null : cb.equal(root.get("configuration"), c.getConfig());
    }

    private static Specification<Property> offerEquals(PropertyCriteria c) {
        return (root, q, cb) ->
                c.getOffer() == null ? null : cb.equal(root.get("offerType"), c.getOffer());
    }

    private static Specification<Property> cityEquals(PropertyCriteria c) {
        return (root, q, cb) ->
                c.getCity() == null || c.getCity().isBlank() ? null :
                        cb.equal(cb.lower(root.get("city")), c.getCity().toLowerCase());
    }

    private static Specification<Property> costBetween(PropertyCriteria c) {
        return (root, q, cb) -> {
            if (c.getMinCost() == null && c.getMaxCost() == null) return null;
            if (c.getMinCost() != null && c.getMaxCost() != null)
                return cb.between(root.get("offerCost"), c.getMinCost(), c.getMaxCost());
            if (c.getMinCost() != null)
                return cb.greaterThanOrEqualTo(root.get("offerCost"), c.getMinCost());
            return cb.lessThanOrEqualTo(root.get("offerCost"), c.getMaxCost());
        };
    }

    /** We don’t show sold/rented ones in searches. */
    private static Specification<Property> onlyAvailable() {
        return (root, q, cb) -> cb.isTrue(root.get("status"));
    }
}
