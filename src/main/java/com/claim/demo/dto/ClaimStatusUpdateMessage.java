package com.claim.demo.dto;

public class ClaimStatusUpdateMessage {
    private Long claimId;        // Unique identifier for the claim
    private String newStatus;    // The new status of the claim
    private String userEmail;    // Email of the user to notify

    // Default constructor for deserialization
    public ClaimStatusUpdateMessage() {
    }

    // Constructor with parameters
    public ClaimStatusUpdateMessage(Long claimId, String newStatus, String userEmail) {
        this.claimId = claimId;
        this.newStatus = newStatus;
        this.userEmail = userEmail;
    }

    // Getters and setters
    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
