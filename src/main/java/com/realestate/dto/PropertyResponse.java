package com.realestate.dto;

import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;
import java.util.List;

public class PropertyResponse {
    
    private Long propId;
    private PropertyConfig configuration;
    private OfferType offerType;
    private Double offerCost;
    private Double areaSqft;
    private String address;
    private String street;
    private String city;
    private Boolean status;
    private List<String> imageUrls;
    private Double avgRating;
    private Integer reviewCount;
    private BrokerInfo broker;

    // Nested class for broker information
    public static class BrokerInfo {
        private Long broId;
        private String broName;

        public BrokerInfo() {}

        public BrokerInfo(Long broId, String broName) {
            this.broId = broId;
            this.broName = broName;
        }

        // Getters and Setters
        public Long getBroId() {
            return broId;
        }

        public void setBroId(Long broId) {
            this.broId = broId;
        }

        public String getBroName() {
            return broName;
        }

        public void setBroName(String broName) {
            this.broName = broName;
        }
    }

    // Constructors
    public PropertyResponse() {}

    // Getters and Setters
    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public PropertyConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(PropertyConfig configuration) {
        this.configuration = configuration;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public Double getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(Double offerCost) {
        this.offerCost = offerCost;
    }

    public Double getAreaSqft() {
        return areaSqft;
    }

    public void setAreaSqft(Double areaSqft) {
        this.areaSqft = areaSqft;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public BrokerInfo getBroker() {
        return broker;
    }

    public void setBroker(BrokerInfo broker) {
        this.broker = broker;
    }
}