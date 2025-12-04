package com.consumerkafka.consumer.core.port;

import com.consumerkafka.consumer.core.domain.model.TrainService;
import java.util.Optional;

/**
 * Output port for persisting train service metadata.
 */
public interface TrainServiceRepository {

    TrainService save(TrainService service);

    Optional<TrainService> findById(Integer id);

    Optional<TrainService> findByRsid(String rsid);
}


