package com.claim.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "claim_reports")
public class ClaimReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String claimStatus;

    @Column(name = "total_claims")
    private Long totalClaims;

    @Column(name = "total_amount")
    private Double totalClaimAmount;

    @Column(name = "report_date")
    private Date reportDate;

    // Getters
    public Long getId() {
        return id;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public Long getTotalClaims() {
        return totalClaims;
    }

    public Double getTotalClaimAmount() {
        return totalClaimAmount;
    }

    public Date getReportDate() {
        return reportDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public void setTotalClaims(Long totalClaims) {
        this.totalClaims = totalClaims;
    }

    public void setTotalClaimAmount(Double totalClaimAmount) {
        this.totalClaimAmount = totalClaimAmount;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
}

