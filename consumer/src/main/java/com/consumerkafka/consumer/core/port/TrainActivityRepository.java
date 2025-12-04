package com.consumerkafka.consumer.core.port;

import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import java.util.List;
import java.util.Optional;

/**
 * Output port for persisting and retrieving train activity aggregates.
 */
public interface TrainActivityRepository {

    TrainActivity save(TrainActivity activity);

    Optional<TrainActivity> findById(Long id);

    List<TrainActivity> findAll();
}


