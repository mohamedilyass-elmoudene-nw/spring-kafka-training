package com.consumerkafka.consumer.infrastructure.persistence.mapper;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.infrastructure.persistence.entity.DimTimeEntity;
import com.consumerkafka.consumer.infrastructure.persistence.entity.FactTrainActivityEntity;
import com.consumerkafka.consumer.infrastructure.persistence.entity.TrainServiceItemEntity;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Bidirectional mapper between domain aggregates and persistence entities.
 */
@Component
public class PersistenceMapper {

    public FactTrainActivityEntity toEntity(TrainActivity activity) {
        Objects.requireNonNull(activity, "activity");
        return FactTrainActivityEntity.builder()
            .activityId(activity.getId())
            .trainServiceItem(toEntity(activity.getTrainService()))
            .time(toEntity(activity.getTimeDimension()))
            .latencyMinutes(activity.getLatencyMinutes())
            .isDelayed(activity.isDelayed() ? 1 : 0)
            .build();
    }

    public TrainActivity toDomain(FactTrainActivityEntity entity) {
        if (entity == null) {
            return null;
        }
        return TrainActivity.builder()
            .id(entity.getActivityId())
            .trainService(toDomain(entity.getTrainServiceItem()))
            .timeDimension(toDomain(entity.getTime()))
            .latencyMinutes(entity.getLatencyMinutes() == null ? 0 : entity.getLatencyMinutes())
            .build();
    }

    public TrainServiceItemEntity toEntity(TrainService service) {
        Objects.requireNonNull(service, "service");
        return TrainServiceItemEntity.builder()
            .trainServiceItemId(service.getId())
            .rsid(service.getRsid())
            .arrivalStationName(service.getArrivalStationName())
            .arrivalStationAbrv(service.getArrivalStationCode())
            .sta(service.getScheduledArrival())
            .eta(service.getEstimatedArrival())
            .operator(service.getOperator())
            .build();
    }

    public TrainService toDomain(TrainServiceItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return TrainService.builder()
            .id(entity.getTrainServiceItemId())
            .rsid(entity.getRsid())
            .arrivalStationName(entity.getArrivalStationName())
            .arrivalStationCode(entity.getArrivalStationAbrv())
            .scheduledArrival(entity.getSta())
            .estimatedArrival(entity.getEta())
            .operator(entity.getOperator())
            .build();
    }

    public DimTimeEntity toEntity(TimeDimension timeDimension) {
        Objects.requireNonNull(timeDimension, "timeDimension");
        return DimTimeEntity.builder()
            .dateId(timeDimension.getDateId())
            .day(timeDimension.getDayOfWeek())
            .month(timeDimension.getMonth())
            .week(timeDimension.getWeek())
            .year(timeDimension.getYear())
            .time(timeDimension.getTime())
            .build();
    }

    public TimeDimension toDomain(DimTimeEntity entity) {
        if (entity == null) {
            return null;
        }
        return TimeDimension.builder()
            .dateId(entity.getDateId())
            .dayOfWeek(entity.getDay())
            .month(entity.getMonth())
            .week(entity.getWeek())
            .year(entity.getYear())
            .time(entity.getTime())
            .build();
    }
}


