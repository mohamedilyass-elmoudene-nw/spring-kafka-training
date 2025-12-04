package com.consumerkafka.consumer.config;

import com.consumerkafka.consumer.core.port.TimeDimensionRepository;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.core.port.TrainServiceRepository;
import com.consumerkafka.consumer.core.usecase.ProcessStationBoardUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public ProcessStationBoardUseCase processStationBoardUseCase(
        TrainActivityRepository trainActivityRepository,
        TimeDimensionRepository timeDimensionRepository,
        TrainServiceRepository trainServiceRepository
    ) {
        return new ProcessStationBoardUseCase(
            trainActivityRepository,
            timeDimensionRepository,
            trainServiceRepository
        );
    }
}


