package com.consumerkafka.consumer.contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contract that any TrainActivityRepository implementation must satisfy.
 */
public abstract class TrainActivityRepositoryContract {

    private TrainActivityRepository repository;

    @BeforeEach
    void setUpContract() {
        repository = createSubject();
        clearData();
    }

    @AfterEach
    void tearDownContract() {
        clearData();
    }

    protected abstract TrainActivityRepository createSubject();

    protected void clearData() {
        // optional for subclasses
    }

    protected TrainActivityRepository repository() {
        return repository;
    }

    @Test
    void savesActivityWithGeneratedId() {
        TrainActivity saved = repository().save(sampleActivity("rsid-contract-1", 15));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getLatencyMinutes()).isEqualTo(15);
        assertThat(saved.isDelayed()).isTrue();
    }

    @Test
    void findsActivityById() {
        TrainActivity saved = repository().save(sampleActivity("rsid-contract-2", 5));

        assertThat(saved.getId()).isNotNull();
        var found = repository().findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getLatencyMinutes()).isEqualTo(5);
    }

    @Test
    void findAllReturnsAllRecords() {
        repository().save(sampleActivity("rsid-contract-3", 3));
        repository().save(sampleActivity("rsid-contract-4", 7));

        assertThat(repository().findAll()).hasSize(2);
    }

    private TrainActivity sampleActivity(String rsid, int latencyMinutes) {
        LocalDateTime scheduledArrival = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime estimatedArrival = scheduledArrival.plusMinutes(latencyMinutes);

        TrainService service = ensureTrainService(
            TrainService.builder()
            .rsid(rsid)
            .arrivalStationName("Contract Station")
            .arrivalStationCode("CON")
            .scheduledArrival(scheduledArrival)
            .estimatedArrival(estimatedArrival)
            .operator("Contract Operator")
            .build()
        );

        TimeDimension timeDimension = ensureTimeDimension(
            TimeDimension.builder()
            .dateId(20241202)
            .dayOfWeek("MONDAY")
            .month(12)
            .week(49)
            .year(2024)
            .time(LocalTime.of(10, 0))
            .build()
        );

        return TrainActivity.builder()
            .trainService(service)
            .timeDimension(timeDimension)
            .latencyMinutes(latencyMinutes)
            .build();
    }

    protected TrainService ensureTrainService(TrainService service) {
        return service;
    }

    protected TimeDimension ensureTimeDimension(TimeDimension timeDimension) {
        return timeDimension;
    }
}


