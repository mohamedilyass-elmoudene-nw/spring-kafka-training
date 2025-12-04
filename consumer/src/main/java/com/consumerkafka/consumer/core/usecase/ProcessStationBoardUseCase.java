package com.consumerkafka.consumer.core.usecase;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.core.port.TimeDimensionRepository;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.core.port.TrainServiceRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Use case responsible for processing a complete station board payload.
 */
public class ProcessStationBoardUseCase {

    private final TrainActivityRepository activityRepository;
    private final TimeDimensionRepository timeDimensionRepository;
    private final TrainServiceRepository trainServiceRepository;

    public ProcessStationBoardUseCase(
        TrainActivityRepository activityRepository,
        TimeDimensionRepository timeDimensionRepository,
        TrainServiceRepository trainServiceRepository
    ) {
        this.activityRepository = Objects.requireNonNull(activityRepository, "activityRepository");
        this.timeDimensionRepository = Objects.requireNonNull(timeDimensionRepository, "timeDimensionRepository");
        this.trainServiceRepository = Objects.requireNonNull(trainServiceRepository, "trainServiceRepository");
    }

    /**
     * Process the provided command and persist all derived train activities.
     */
    public List<TrainActivity> execute(StationBoardCommand command) {
        Objects.requireNonNull(command, "command");

        List<ServiceItemCommand> services = normalizeServices(command.services());
        if (services.isEmpty()) {
            return List.of();
        }

        List<TrainActivity> savedActivities = new ArrayList<>();
        for (ServiceItemCommand serviceItem : services) {
            if (isMissingArrivalData(serviceItem)) {
                continue;
            }

            TrainService trainService = resolveTrainService(command, serviceItem);
            TimeDimension timeDimension = resolveTimeDimension(serviceItem.estimatedArrival());
            TrainActivity activity = TrainActivity.createFromArrivalTimes(
                trainService,
                timeDimension,
                serviceItem.scheduledArrival(),
                serviceItem.estimatedArrival()
            );

            savedActivities.add(activityRepository.save(activity));
        }

        return savedActivities;
    }

    private List<ServiceItemCommand> normalizeServices(List<ServiceItemCommand> services) {
        if (services == null || services.isEmpty()) {
            return Collections.emptyList();
        }
        return services;
    }

    private boolean isMissingArrivalData(ServiceItemCommand serviceItem) {
        return serviceItem == null
            || serviceItem.scheduledArrival() == null
            || serviceItem.estimatedArrival() == null;
    }

    private TrainService resolveTrainService(StationBoardCommand command, ServiceItemCommand serviceItem) {
        if (serviceItem.rsid() != null) {
            return trainServiceRepository.findByRsid(serviceItem.rsid())
                .orElseGet(() -> trainServiceRepository.save(buildService(command, serviceItem)));
        }
        return trainServiceRepository.save(buildService(command, serviceItem));
    }

    private TrainService buildService(StationBoardCommand command, ServiceItemCommand serviceItem) {
        return TrainService.builder()
            .rsid(serviceItem.rsid())
            .arrivalStationName(command.stationName())
            .arrivalStationCode(command.stationCode())
            .scheduledArrival(serviceItem.scheduledArrival())
            .estimatedArrival(serviceItem.estimatedArrival())
            .operator(serviceItem.operator())
            .build();
    }

    private TimeDimension resolveTimeDimension(LocalDateTime arrivalTime) {
        TimeDimension candidate = TimeDimension.fromDateTime(arrivalTime);
        return timeDimensionRepository.findByDateId(candidate.getDateId())
            .orElseGet(() -> timeDimensionRepository.save(candidate));
    }

    /**
     * Command describing the incoming station board payload.
     */
    public record StationBoardCommand(
        String stationName,
        String stationCode,
        LocalDateTime generatedAt,
        List<ServiceItemCommand> services
    ) { }

    /**
     * Command describing a single service entry in the station board.
     */
    public record ServiceItemCommand(
        String rsid,
        LocalDateTime scheduledArrival,
        LocalDateTime estimatedArrival,
        String operator
    ) { }
}


