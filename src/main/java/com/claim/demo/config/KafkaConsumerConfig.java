package com.claim.demo.config;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.claim.demo.dto.ClaimStatusUpdateMessage;
import com.claim.demo.service.EmailService;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka consumer.
 * Uses @EnableKafka annotation to enable detection of @KafkaListener annotation on spring managed beans.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private EmailService emailService;  // Autowired EmailService to use for sending notifications via email.

    /**
     * Configures the consumer factory which is responsible for creating Kafka consumers.
     * @return a configured ConsumerFactory for Kafka.
     */
    @Bean
    public ConsumerFactory<String, ClaimStatusUpdateMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Kafka server URL
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");  // Consumer group ID
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());  // Key deserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());  // Value deserializer
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(ClaimStatusUpdateMessage.class));  // Consumer factory for ClaimStatusUpdateMessage objects.
    }

    /**
     * Bean to create a container factory to manage Kafka listener containers.
     * @return a configured KafkaListenerContainerFactory.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClaimStatusUpdateMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClaimStatusUpdateMessage> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());  // Set the custom consumer factory.
        return factory;
    }

    /**
     * Kafka listener to handle messages from 'claim-updates' topic.
     * Processes each message to perform business operations, like sending an email notification.
     * @param message the ClaimStatusUpdateMessage from Kafka.
     */
    @KafkaListener(topics = "claim-updates", groupId = "group_id")
    public void handleClaimStatusUpdate(ClaimStatusUpdateMessage message) {
        // Example email sending function call
        emailService.sendEmail(message.getUserEmail(), "Claim Status Update",
            "Your claim #" + message.getClaimId() + " has been updated to: " + message.getNewStatus());
    }
}
