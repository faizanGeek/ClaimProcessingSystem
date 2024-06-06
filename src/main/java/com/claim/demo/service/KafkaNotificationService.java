package com.claim.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import com.claim.demo.dto.ClaimStatusUpdateMessage;
import com.claim.demo.dto.NotificationDTO;

public class KafkaNotificationService {
	
    
    private static final String CLAIM_STATUS_TOPIC = "claim-status-updates";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendClaimStatusUpdate(Long claimId, String newStatus, String userEmail) {
        ClaimStatusUpdateMessage message = new ClaimStatusUpdateMessage(claimId, newStatus, userEmail);
        kafkaTemplate.send(CLAIM_STATUS_TOPIC, message);
    }
    
}
