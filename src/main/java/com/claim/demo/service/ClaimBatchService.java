package com.claim.demo.service;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim.demo.dto.ClaimDTO;
import com.claim.demo.entity.Claim;

@Service
public class ClaimBatchService {

    @Autowired
    private ClaimService claimService; // Service that contains business logic for updating claims

    // This scheduled task runs every hour
    @Scheduled(cron = "0 0 * * * *") // Cron expression for hourly execution
    public void processPendingClaims() {
        List<Claim> claims = claimService.findClaimsNeedingUpdate(); // Fetch claims that need processing
        for (Claim claim : claims) {
            try {
                // Logic to determine the new status; simplified here
                String newStatus = "Processed"; // Example status update
                claimService.updateClaimStatus(claim.getClaimId(), newStatus, claim.getEmailId());
            } catch (Exception e) {
                // Log error or handle exception
                System.out.println("Error processing claim ID: " + claim.getClaimId() + " with error: " + e.getMessage());
            }
        }
    }
}

