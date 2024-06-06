package com.claim.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.claim.demo.dto.NotificationDTO;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer.
 * Sets up the infrastructure required to send messages to a Kafka topic.
 */
@Configuration
public class KafkaProducerConfig {
	
    /**
     * Configures and creates a ProducerFactory which sets the strategy for creating Kafka Producer instances.
     * @return ProducerFactory capable of creating producers that send NotificationDTO objects.
     */
    @Bean
    public ProducerFactory<String, NotificationDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Specify the address of the Kafka server.
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Use StringSerializer to serialize keys.
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);  // Use JsonSerializer to serialize the value objects.
        return new DefaultKafkaProducerFactory<>(configProps);  // Factory to create Kafka producers.
    }

    /**
     * KafkaTemplate wraps a Producer and provides convenience methods for sending messages to Kafka topics.
     * @return KafkaTemplate configured with the producer factory.
     */
    @Bean
    public KafkaTemplate<String, NotificationDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());  // Create a KafkaTemplate from the producer factory.
    }
}
