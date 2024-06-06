package com.claim.demo.dto;

public class ClaimReportDTO {
    private String claimStatus;
    private Long totalClaims;
    private Double totalClaimAmount;

    // Constructors
    public ClaimReportDTO() {}

    public ClaimReportDTO(String claimStatus, Long totalClaims, Double totalClaimAmount) {
        this.claimStatus = claimStatus;
        this.totalClaims = totalClaims;
        this.totalClaimAmount = totalClaimAmount;
    }

    // Getters
    public String getClaimStatus() {
        return claimStatus;
    }

    public Long getTotalClaims() {
        return totalClaims;
    }

    public Double getTotalClaimAmount() {
        return totalClaimAmount;
    }

    // Setters
    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public void setTotalClaims(Long totalClaims) {
        this.totalClaims = totalClaims;
    }

    public void setTotalClaimAmount(Double totalClaimAmount) {
        this.totalClaimAmount = totalClaimAmount;
    }
}
