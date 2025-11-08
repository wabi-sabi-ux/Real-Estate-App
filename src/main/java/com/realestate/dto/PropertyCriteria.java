package com.realestate.dto;

import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;

public class PropertyCriteria {
    private PropertyConfig config;
    private OfferType offer;
    private String city;
    private Double minCost;
    private Double maxCost;
    private Double minRating;
    private Integer minReviews;
    private Double maxAreaSqft;
    private Double minAreaSqft;
    private String street;
    private Boolean onlyAvailable = true;

    public PropertyCriteria() {}

    public PropertyConfig getConfig() { return config; }
    public void setConfig(PropertyConfig config) { this.config = config; }

    public OfferType getOffer() { return offer; }
    public void setOffer(OfferType offer) { this.offer = offer; }

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

    public Boolean getOnlyAvailable() { return onlyAvailable; }
    public void setOnlyAvailable(Boolean onlyAvailable) { this.onlyAvailable = onlyAvailable; }
}
