package com.consumerkafka.consumer;

import com.producerkafka.producer.model.DeparturesBoard;
import com.producerkafka.producer.model.ServiceDetails;
import com.producerkafka.producer.model.StationBoard;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "train-date", groupId = "train-data-consumer-group")
    public void consume(ConsumerRecord<String, Object> record) {
        Object message = record.value();
        
        if (message instanceof StationBoard) {
            log.info("Received StationBoard for: {}", ((StationBoard) message).getLocationName());
        } else if (message instanceof ServiceDetails) {
            log.info("Received ServiceDetails for service to: {}", ((ServiceDetails) message).getLocationName());
        } else if (message instanceof DeparturesBoard) {
            log.info("Received DeparturesBoard for: {}", ((DeparturesBoard) message).getLocationName());
        } else {
            log.info("Received unknown message type: {} (Record Key: {})", message.getClass().getName(), record.key());
        }
    }
}
