package com.realestate.dto;

import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PropertyRequest {
    
    @NotNull
    private PropertyConfig configuration;
    
    @NotNull
    private OfferType offerType;
    
    @Positive
    @NotNull
    private Double offerCost;
    
    @Positive
    @NotNull
    private Double areaSqft;
    
    @NotNull
    private String address;
    
    private String street;
    
    @NotNull
    private String city;

    // Constructors
    public PropertyRequest() {}

    public PropertyRequest(PropertyConfig configuration, OfferType offerType, Double offerCost, 
                          Double areaSqft, String address, String street, String city) {
        this.configuration = configuration;
        this.offerType = offerType;
        this.offerCost = offerCost;
        this.areaSqft = areaSqft;
        this.address = address;
        this.street = street;
        this.city = city;
    }

    // Getters and Setters
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
}