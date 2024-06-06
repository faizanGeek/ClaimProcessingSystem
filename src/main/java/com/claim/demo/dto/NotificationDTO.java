package com.claim.demo.dto;

import java.util.Date;

public class NotificationDTO {
    private Long id;
    private Long userId;
    private String message;
    private Date timestamp;

    // Constructors
    public NotificationDTO() {}

    public NotificationDTO(Long id, Long userId, String message, Date timestamp) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
