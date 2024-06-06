package com.claim.demo.service;


import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.claim.demo.dto.ClaimDTO;
import com.claim.demo.entity.Claim;
import com.claim.demo.repository.ClaimRepository;
import org.springframework.data.redis.core.RedisTemplate;



import java.util.Optional;

public class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;
    
    @InjectMocks
    private ClaimService claimService;

    @Mock
    private KafkaNotificationService kafkaNotificationService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateClaimStatus() {
        Long claimId = 1L;
        String newStatus = "Approved";
        String userEmail = "user@example.com";
        Claim claim = new Claim();
        claim.setClaimId(claimId);
        claim.setClaimStatus("Pending");

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
        doNothing().when(kafkaNotificationService).sendClaimStatusUpdate(claimId, newStatus, userEmail);
        when(claimRepository.save(claim)).thenReturn(claim);

        claimService.updateClaimStatus(claimId, newStatus, userEmail);

        verify(claimRepository).save(claim);
        verify(kafkaNotificationService).sendClaimStatusUpdate(claimId, newStatus, userEmail);
        assertEquals(newStatus, claim.getClaimStatus());
    }

    @Test
    public void testGetClaimStatus() {
        Long claimId = 1L;
        String expectedStatus = "Approved";
        when(redisTemplate.opsForValue().get("claimStatus:" + claimId)).thenReturn(expectedStatus);

        String status = claimService.getClaimStatus(claimId);

        assertEquals(expectedStatus, status);
    }

    @Test
    public void testSubmitClaim() {
        Claim claim = new Claim();
        claim.setClaimId(1L);
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);

        ClaimDTO result = claimService.submitClaim(claim);

        assertNotNull(result);
        assertEquals("Submitted", result.getClaimStatus());
    }

    @Test
    public void testDeleteClaim() {
        Long claimId = 1L;
        Claim claim = new Claim();
        claim.setClaimId(claimId);
        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
        doNothing().when(claimRepository).delete(claim);

        claimService.deleteClaim(claimId);

        verify(claimRepository).delete(claim);
    }
}
