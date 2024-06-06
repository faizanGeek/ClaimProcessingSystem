package com.claim.demo.dto;

import java.util.Date;

public class ClaimsSummaryDTO {
    private Date reportGenerated;
    private Integer numberOfClaims;
    private Double totalAmount;

    // Constructors
    public ClaimsSummaryDTO() {}

    public ClaimsSummaryDTO(Date reportGenerated, Integer numberOfClaims, Double totalAmount) {
        this.reportGenerated = reportGenerated;
        this.numberOfClaims = numberOfClaims;
        this.totalAmount = totalAmount;
    }

    // Getters
    public Date getReportGenerated() {
        return reportGenerated;
    }

    public Integer getNumberOfClaims() {
        return numberOfClaims;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setReportGenerated(Date reportGenerated) {
        this.reportGenerated = reportGenerated;
    }

    public void setNumberOfClaims(Integer numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
