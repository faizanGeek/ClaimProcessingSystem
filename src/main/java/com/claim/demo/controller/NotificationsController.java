package com.claim.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.claim.demo.dto.NotificationDTO;
import com.claim.demo.service.NotificationService;

/**
 * Controller for handling notification subscriptions.
 * Maps all notification-related actions under the "/notifications" path.
 */
@RestController
@RequestMapping("/notifications") // Base URI for all handlers in this controller.
public class NotificationsController {
    @Autowired
    private NotificationService notificationService;  // Service layer handling the business logic for notifications.

    /**
     * Endpoint to subscribe to notifications.
     * Accepts a POST request with NotificationDTO that includes user ID and message details.
     * 
     * @param notificationDTO Data Transfer Object containing the notification details.
     * @return a response entity with a success message and the HTTP status.
     */
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody NotificationDTO notificationDTO) {
        // Call the notification service to handle the subscription logic.
        notificationService.subscribeToNotifications(notificationDTO.getUserId(), notificationDTO.getMessage());
        // Return a success response.
        return ResponseEntity.ok("Subscribed successfully to notifications.");
    }

    /**
     * Endpoint to unsubscribe from notifications.
     * Accepts a POST request with NotificationDTO that includes user ID.
     * 
     * @param notificationDTO Data Transfer Object containing the user ID to unsubscribe.
     * @return a response entity with a success message and the HTTP status.
     */
    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody NotificationDTO notificationDTO) {
        // Call the notification service to handle the unsubscription logic.
        notificationService.unsubscribeFromNotifications(notificationDTO.getUserId());
        // Return a success response.
        return ResponseEntity.ok("Unsubscribed successfully from notifications.");
    }
}
