package com.producerkafka.producer.scheduler;

import com.producerkafka.producer.KafkaProducerService;
import com.producerkafka.producer.service.TrainDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class DataPublisherScheduler {
    
    private static final long PUBLISH_RATE_MS = 5000; // 5 seconds
    
    private final TrainDataGenerator dataGenerator;
    private final KafkaProducerService kafkaProducerService;
    
    public DataPublisherScheduler(TrainDataGenerator dataGenerator, 
                                  KafkaProducerService kafkaProducerService) {
        this.dataGenerator = dataGenerator;
        this.kafkaProducerService = kafkaProducerService;
    }
    
    /**
     * Runs when the application is ready
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        log.info("Application started. Train data publisher is ready.");
        log.info("Publishing train data every {} ms to Kafka topic: train-date", PUBLISH_RATE_MS);
    }
    
    /**
     * Scheduled method that generates and publishes train data
     * Runs every 5 seconds (fixed rate)
     */
    @Scheduled(fixedRate = PUBLISH_RATE_MS)
    public void publishTrainData() {
        try {
            // Generate random train data
            Object trainData = dataGenerator.generateRandomData();
            String dataType = trainData.getClass().getSimpleName();
            
            // Send to Kafka with data type as key
            // The JsonSerializer will handle serialization and add the type header
            kafkaProducerService.sendMessage(dataType, trainData);
            
            log.info("Published {} to Kafka", dataType);
            
        } catch (Exception e) {
            log.error("Error publishing train data to Kafka", e);
        }
    }
}
