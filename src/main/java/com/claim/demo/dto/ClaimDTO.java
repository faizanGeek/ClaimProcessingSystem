package com.claim.demo.dto;

import java.util.Date;

public class ClaimDTO {
    private Long claimId;
    private Long userId;
    private String emailId;
    private Date claimDate;
    private Double claimAmount;
    private String claimType;
    private String claimStatus;
    private Date lastUpdated;

    // Constructors, getters, and setters

    public ClaimDTO() {}

    public ClaimDTO(Long claimId, Long userId, Date claimDate, Double claimAmount, String claimType, String claimStatus, Date lastUpdated) {
        this.claimId = claimId;
        this.userId = userId;
        this.claimDate = claimDate;
        this.claimAmount = claimAmount;
        this.claimType = claimType;
        this.claimStatus = claimStatus;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters omitted for brevity
    
    // Getters
    public Long getClaimId() {
        return claimId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public String getClaimType() {
        return claimType;
    }

    public String getClaimStatus() {
        return claimStatus;
    }
    public String getEmailId() {
        return emailId;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }

    // Setters
    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
