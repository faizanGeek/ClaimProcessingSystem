package com.claim.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.claim.demo.dto.ClaimDTO;
import com.claim.demo.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUserId(Long userId);

	Claim save(Claim claim);
	
    List<Claim> findClaimsByStatusAndLastUpdatedBefore(String status, Date lastUpdated);

}