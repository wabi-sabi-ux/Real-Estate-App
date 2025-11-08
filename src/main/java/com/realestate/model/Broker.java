package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brokers")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long broId;

    @Column(nullable = false)
    private String broName;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Property> properties = new ArrayList<>();

    private double avgRating = 0.0;
    private int ratingCount = 0;

    @OneToMany(mappedBy = "broker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BrokerRating> ratings = new ArrayList<>();

    public Broker() {}

    public Long getBroId() { return broId; }
    public void setBroId(Long broId) { this.broId = broId; }

    public String getBroName() { return broName; }
    public void setBroName(String broName) { this.broName = broName; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Property> getProperties() { return properties; }
    public void setProperties(List<Property> properties) { this.properties = properties; }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public int getRatingCount() { return ratingCount; }
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    public List<BrokerRating> getRatings() { return ratings; }
    public void setRatings(List<BrokerRating> ratings) { this.ratings = ratings; }
}
