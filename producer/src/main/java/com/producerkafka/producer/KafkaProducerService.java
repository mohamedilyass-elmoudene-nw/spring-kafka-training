package com.producerkafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "train-date";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a message to the train-date topic
     * @param key the message key
     * @param message the message content
     */
    public void sendMessage(String key, Object message) {
        kafkaTemplate.send(TOPIC, key, message);
    }

    /**
     * Sends a message to the train-date topic without a key
     * @param message the message content
     */
    public void sendMessage(Object message) {
        kafkaTemplate.send(TOPIC, message);
    }
}

