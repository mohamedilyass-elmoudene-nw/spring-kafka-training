package com.consumerkafka.consumer.core.port;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import java.util.Optional;

/**
 * Output port for caching the time dimension values.
 */
public interface TimeDimensionRepository {

    TimeDimension save(TimeDimension timeDimension);

    Optional<TimeDimension> findByDateId(int dateId);
}


