package com.consumerkafka.consumer.infrastructure.persistence.adapter;

import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.infrastructure.persistence.entity.FactTrainActivityEntity;
import com.consumerkafka.consumer.infrastructure.persistence.jpa.JpaTrainActivityRepository;
import com.consumerkafka.consumer.infrastructure.persistence.mapper.PersistenceMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@SuppressWarnings("null")
public class JpaTrainActivityAdapter implements TrainActivityRepository {

    private final JpaTrainActivityRepository jpaRepository;
    private final PersistenceMapper mapper;

    public JpaTrainActivityAdapter(JpaTrainActivityRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TrainActivity save(TrainActivity activity) {
        FactTrainActivityEntity entity = requireNonNull(
            mapper.toEntity(activity),
            "TrainActivity mapping produced null entity"
        );
        FactTrainActivityEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainActivity> findById(Long id) {
        Long activityId = requireNonNull(id, "activityId cannot be null");
        return jpaRepository.findById(activityId).map(mapper::toDomain);
    }

    private static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainActivity> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
    }
}


