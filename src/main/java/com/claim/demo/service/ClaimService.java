package com.claim.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claim.demo.dto.ClaimDTO;
import com.claim.demo.entity.Claim;
import com.claim.demo.repository.ClaimRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private KafkaNotificationService kafkaNotificationService;
	

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

	private static final Logger logger = LogManager.getLogger(ClaimService.class);
	
	public void updateClaimStatus(Long claimId, String newStatus, String userEmail) {
		
        redisTemplate.opsForValue().set("claimStatus:" + claimId, newStatus);


		try {
			Claim claim = claimRepository.findById(claimId).orElseThrow(() -> new RuntimeException("Claim not found"));
			String oldStatus = claim.getClaimStatus();
			claim.setClaimStatus(newStatus);
			claimRepository.save(claim);

			logger.debug("Claim status updated successfully. Claim ID: {}, Old Status: {}, New Status: {}", claimId,
					oldStatus, newStatus);

			// Publish the status update to Kafka
			kafkaNotificationService.sendClaimStatusUpdate(claimId, newStatus, userEmail);
			logger.info("Published claim status update to Kafka. Claim ID: {}", claimId);
		} catch (Exception e) {
			logger.error("Error updating claim status. Claim ID: {}, Error: {}", claimId, e.getMessage(), e);
			throw e; // Rethrow the exception after logging
		}
	}
	
    public String getClaimStatus(Long claimId) {
        // Attempt to get the status from Redis
        return (String) redisTemplate.opsForValue().get("claimStatus:" + claimId);
    }

	@Transactional
	public ClaimDTO submitClaim(Claim claim) {
		claim.setClaimStatus("Submitted");
		claim.setClaimDate(new Date());
		return convertToDTO(claimRepository.save(claim));
	}

	@Transactional(readOnly = true)
	public Claim findClaimById(Long claimId) {
		Optional<Claim> claim = claimRepository.findById(claimId);
//            .orElseThrow(() -> new RuntimeException("Claim not found with id: " + claimId));
//        return convertToDTO(claim);
		return claim.get();
	}

	@Transactional
	public ClaimDTO updateClaim(Long claimId, ClaimDTO updatedClaim) {
		Claim claim = findClaimById(claimId);
		claim.setClaimAmount(updatedClaim.getClaimAmount());
		claim.setClaimType(updatedClaim.getClaimType());
		claim.setClaimStatus(updatedClaim.getClaimStatus());
		claim.setLastUpdated(new Date());
		return convertToDTO(claimRepository.save(claim));
	}

	@Transactional
	public void deleteClaim(Long claimId) {
		Claim claim = findClaimById(claimId);
		claimRepository.delete(claim);
	}

	@Transactional(readOnly = true)
	public List<ClaimDTO> findClaimsByUserId(Long userId) {
		List<Claim> claims = claimRepository.findByUserId(userId);
		return claims.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public ClaimDTO convertToDTO(Claim claim) {
		return new ClaimDTO(claim.getClaimId(), claim.getUser() != null ? claim.getUser().getUserId() : null,
				claim.getClaimDate(), claim.getClaimAmount(), claim.getClaimType(), claim.getClaimStatus(),
				claim.getLastUpdated());
	}

	public List<Claim> findClaimsNeedingUpdate() {
		// Fetch claims based on specific criteria, e.g., status is 'Pending' and last
		// updated > 24 hours ago
		return claimRepository.findClaimsByStatusAndLastUpdatedBefore("Pending",
				new Date(System.currentTimeMillis() - 86400000));
	}


}
