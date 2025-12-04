package com.consumerkafka.consumer.api.kafka;

import com.consumerkafka.consumer.api.kafka.mapper.StationBoardToDomainMapper;
import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase;
import com.producerkafka.producer.model.StationBoard;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaTrainDataListener {

    private final ProcessStationBoardUseCase useCase;
    private final StationBoardToDomainMapper mapper;

    public KafkaTrainDataListener(
        ProcessStationBoardUseCase useCase,
        StationBoardToDomainMapper mapper
    ) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "train-date", groupId = "train-data-consumer-group")
    public void consume(ConsumerRecord<String, Object> record) {
        Object payload = record.value();
        if (payload == null) {
            log.debug("Ignoring null payload for key {}", record.key());
            return;
        }
        if (!(payload instanceof StationBoard board)) {
            log.debug("Ignoring unsupported payload type: {}", payload.getClass().getName());
            return;
        }

        var command = mapper.toCommand(board);
        var activities = useCase.execute(command);
        log.info("Processed {} train services for station {}", activities.size(), board.getLocationName());
    }
}


