package com.consumerkafka.consumer.api.kafka.mapper;

import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase.ServiceItemCommand;
import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase.StationBoardCommand;
import com.producerkafka.producer.model.ServiceItem;
import com.producerkafka.producer.model.StationBoard;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Maps incoming Kafka DTOs to domain-level command objects.
 */
@Component
@Slf4j
public class StationBoardToDomainMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public StationBoardCommand toCommand(StationBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("StationBoard cannot be null");
        }

        LocalDateTime generatedAt = board.getGeneratedAt() != null
            ? board.getGeneratedAt()
            : LocalDateTime.now();

        return new StationBoardCommand(
            board.getLocationName(),
            board.getCrs(),
            generatedAt,
            mapServices(board, generatedAt)
        );
    }

    private List<ServiceItemCommand> mapServices(StationBoard board, LocalDateTime baseDateTime) {
        if (board.getTrainServices() == null) {
            return Collections.emptyList();
        }

        return board.getTrainServices()
            .stream()
            .map(service -> toServiceItemCommand(service, baseDateTime))
            .toList();
    }

    private ServiceItemCommand toServiceItemCommand(ServiceItem serviceItem, LocalDateTime baseDateTime) {
        return new ServiceItemCommand(
            serviceItem.getRsid(),
            parseDateTime(serviceItem.getSta(), baseDateTime),
            parseDateTime(serviceItem.getEta(), baseDateTime),
            serviceItem.getOperator()
        );
    }

    private LocalDateTime parseDateTime(String timeStr, LocalDateTime baseDateTime) {
        if (timeStr == null || timeStr.isBlank()) {
            return null;
        }

        try {
            LocalTime time = LocalTime.parse(timeStr, TIME_FORMATTER);
            return LocalDateTime.of(baseDateTime.toLocalDate(), time);
        } catch (DateTimeParseException ex) {
            log.warn("Failed to parse time '{}'", timeStr, ex);
            return null;
        }
    }
}


