package com.realestate.dto;

import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;

public class PropertyCriteria {
    private PropertyConfig configuration;
    private OfferType offerType;
    private String city;
    private Double minCost;
    private Double maxCost;
    private Double minRating;
    private Integer minReviews;
    private Double maxAreaSqft;
    private Double minAreaSqft;
    private String street;
    private Boolean status = true;

    // Builder pattern
    public static PropertyCriteriaBuilder builder() {
        return new PropertyCriteriaBuilder();
    }

    public static class PropertyCriteriaBuilder {
        private PropertyConfig configuration;
        private OfferType offerType;
        private String city;
        private Double minCost;
        private Double maxCost;
        private Boolean status;

        public PropertyCriteriaBuilder configuration(PropertyConfig configuration) {
            this.configuration = configuration;
            return this;
        }

        public PropertyCriteriaBuilder offerType(OfferType offerType) {
            this.offerType = offerType;
            return this;
        }

        public PropertyCriteriaBuilder city(String city) {
            this.city = city;
            return this;
        }

        public PropertyCriteriaBuilder minCost(Double minCost) {
            this.minCost = minCost;
            return this;
        }

        public PropertyCriteriaBuilder maxCost(Double maxCost) {
            this.maxCost = maxCost;
            return this;
        }

        public PropertyCriteriaBuilder status(Boolean status) {
            this.status = status;
            return this;
        }

        public PropertyCriteria build() {
            PropertyCriteria criteria = new PropertyCriteria();
            criteria.setConfiguration(this.configuration);
            criteria.setOfferType(this.offerType);
            criteria.setCity(this.city);
            criteria.setMinCost(this.minCost);
            criteria.setMaxCost(this.maxCost);
            criteria.setStatus(this.status);
            return criteria;
        }
    }

    public PropertyCriteria() {}

    public PropertyConfig getConfiguration() { return configuration; }
    public void setConfiguration(PropertyConfig configuration) { this.configuration = configuration; }

    public OfferType getOfferType() { return offerType; }
    public void setOfferType(OfferType offerType) { this.offerType = offerType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getMinCost() { return minCost; }
    public void setMinCost(Double minCost) { this.minCost = minCost; }

    public Double getMaxCost() { return maxCost; }
    public void setMaxCost(Double maxCost) { this.maxCost = maxCost; }

    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }

    public Integer getMinReviews() { return minReviews; }
    public void setMinReviews(Integer minReviews) { this.minReviews = minReviews; }

    public Double getMaxAreaSqft() { return maxAreaSqft; }
    public void setMaxAreaSqft(Double maxAreaSqft) { this.maxAreaSqft = maxAreaSqft; }

    public Double getMinAreaSqft() { return minAreaSqft; }
    public void setMinAreaSqft(Double minAreaSqft) { this.minAreaSqft = minAreaSqft; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
