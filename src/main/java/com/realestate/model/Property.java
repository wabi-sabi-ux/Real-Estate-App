package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.enums.OfferType;
import com.realestate.enums.PropertyConfig;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyConfig configuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferType offerType;

    private double offerCost;
    private double areaSqft;

    private String address;
    private String street;
    private String city;

    /** true => available; false => taken */
    private boolean status = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "prop_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> imageUrls = new ArrayList<>();

    // @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnore
    // private List<PropertyComment> comments = new ArrayList<>();

    private double avgRating;
    private int reviewCount;

    public Property() {}

    // --- getters/setters for imageUrls ---
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public Long getPropId() { return propId; }
    public void setPropId(Long propId) { this.propId = propId; }

    public PropertyConfig getConfiguration() { return configuration; }
    public void setConfiguration(PropertyConfig configuration) { this.configuration = configuration; }

    public OfferType getOfferType() { return offerType; }
    public void setOfferType(OfferType offerType) { this.offerType = offerType; }

    public double getOfferCost() { return offerCost; }
    public void setOfferCost(double offerCost) { this.offerCost = offerCost; }

    public double getAreaSqft() { return areaSqft; }
    public void setAreaSqft(double areaSqft) { this.areaSqft = areaSqft; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Broker getBroker() { return broker; }
    public void setBroker(Broker broker) { this.broker = broker; }

    // public List<PropertyComment> getComments() { return comments; }
    // public void setComments(List<PropertyComment> comments) { this.comments = comments; }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
}
