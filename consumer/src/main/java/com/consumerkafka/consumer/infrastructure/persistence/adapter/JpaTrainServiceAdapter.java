package com.consumerkafka.consumer.infrastructure.persistence.adapter;

import com.consumerkafka.consumer.core.domain.model.TrainService;
import com.consumerkafka.consumer.core.port.TrainServiceRepository;
import com.consumerkafka.consumer.infrastructure.persistence.entity.TrainServiceItemEntity;
import com.consumerkafka.consumer.infrastructure.persistence.jpa.JpaTrainServiceItemRepository;
import com.consumerkafka.consumer.infrastructure.persistence.mapper.PersistenceMapper;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@SuppressWarnings("null")
public class JpaTrainServiceAdapter implements TrainServiceRepository {

    private final JpaTrainServiceItemRepository jpaRepository;
    private final PersistenceMapper mapper;

    public JpaTrainServiceAdapter(JpaTrainServiceItemRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TrainService save(TrainService service) {
        TrainServiceItemEntity entity = requireNonNull(
            mapper.toEntity(service),
            "TrainService mapping produced null entity"
        );
        TrainServiceItemEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainService> findById(Integer id) {
        Integer trainServiceId = requireNonNull(id, "trainServiceId cannot be null");
        return jpaRepository.findById(trainServiceId).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainService> findByRsid(String rsid) {
        return jpaRepository.findByRsid(rsid).map(mapper::toDomain);
    }

    private static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}


