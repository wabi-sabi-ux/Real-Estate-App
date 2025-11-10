package com.realestate.dto;

public class UserProfile {
    private Long userId;
    private String email;
    private String role;
    private String mobile;
    private String city;
    private Long brokerId;
    private Long customerId;

    public UserProfile() {}

    public UserProfile(Long userId, String email, String role, String mobile, String city,
                       Long brokerId, Long customerId) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.mobile = mobile;
        this.city = city;
        this.brokerId = brokerId;
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
