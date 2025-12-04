package com.consumerkafka.consumer.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.fake.FakeTimeDimensionRepository;
import com.consumerkafka.consumer.core.fake.FakeTrainActivityRepository;
import com.consumerkafka.consumer.core.fake.FakeTrainServiceRepository;
import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase.ServiceItemCommand;
import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase.StationBoardCommand;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessStationBoardUseCaseTest {

    private FakeTrainActivityRepository activityRepository;
    private FakeTimeDimensionRepository timeDimensionRepository;
    private FakeTrainServiceRepository trainServiceRepository;
    private ProcessStationBoardUseCase useCase;

    @BeforeEach
    void setUp() {
        activityRepository = new FakeTrainActivityRepository();
        timeDimensionRepository = new FakeTimeDimensionRepository();
        trainServiceRepository = new FakeTrainServiceRepository();
        useCase = new ProcessStationBoardUseCase(
            activityRepository,
            timeDimensionRepository,
            trainServiceRepository
        );
    }

    @Test
    void shouldCalculateLatencyAndDelayFlag() {
        LocalDateTime sta = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime eta = sta.plusMinutes(15);

        StationBoardCommand command = stationBoard(
            "London Waterloo",
            "WAT",
            sta,
            new ServiceItemCommand("rsid-1", sta, eta, "Southern")
        );

        List<TrainActivity> result = useCase.execute(command);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLatencyMinutes()).isEqualTo(15);
        assertThat(result.get(0).isDelayed()).isTrue();
        assertThat(activityRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldMarkOnTimeWhenEtaEqualsSta() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 10, 0);

        StationBoardCommand command = stationBoard(
            "Test",
            "TST",
            time,
            new ServiceItemCommand("rsid-2", time, time, "Operator")
        );

        List<TrainActivity> result = useCase.execute(command);

        assertThat(result).hasSize(1);
        TrainActivity activity = result.get(0);
        assertThat(activity.getLatencyMinutes()).isZero();
        assertThat(activity.isDelayed()).isFalse();
    }

    @Test
    void shouldSkipServicesMissingTimes() {
        LocalDateTime generatedAt = LocalDateTime.of(2024, 12, 2, 10, 0);

        StationBoardCommand command = stationBoard(
            "Test",
            "TST",
            generatedAt,
            new ServiceItemCommand("rsid-3", null, generatedAt, "Operator")
        );

        List<TrainActivity> result = useCase.execute(command);

        assertThat(result).isEmpty();
        assertThat(activityRepository.count()).isZero();
    }

    @Test
    void shouldReuseExistingTrainServiceByRsid() {
        LocalDateTime sta = LocalDateTime.of(2024, 12, 2, 10, 0);
        LocalDateTime eta = sta.plusMinutes(5);

        StationBoardCommand first = stationBoard(
            "Station",
            "STN",
            sta,
            new ServiceItemCommand("shared-rsid", sta, eta, "Operator")
        );

        StationBoardCommand second = stationBoard(
            "Station",
            "STN",
            sta,
            new ServiceItemCommand("shared-rsid", sta.plusHours(1), eta.plusHours(1), "Operator")
        );

        useCase.execute(first);
        useCase.execute(second);

        assertThat(trainServiceRepository.size()).isEqualTo(1);
    }

    @Test
    void shouldCacheTimeDimensionByDate() {
        LocalDateTime firstEta = LocalDateTime.of(2024, 12, 2, 10, 15);
        LocalDateTime secondEta = LocalDateTime.of(2024, 12, 2, 11, 45);

        StationBoardCommand command = new StationBoardCommand(
            "Station",
            "STN",
            LocalDateTime.now(),
            List.of(
                new ServiceItemCommand("rsid-a", firstEta.minusMinutes(10), firstEta, "Operator"),
                new ServiceItemCommand("rsid-b", secondEta.minusMinutes(10), secondEta, "Operator")
            )
        );

        useCase.execute(command);

        assertThat(timeDimensionRepository.size()).isEqualTo(1);
    }

    private StationBoardCommand stationBoard(
        String name,
        String code,
        LocalDateTime generatedAt,
        ServiceItemCommand service
    ) {
        return new StationBoardCommand(
            name,
            code,
            generatedAt,
            List.of(service)
        );
    }
}


