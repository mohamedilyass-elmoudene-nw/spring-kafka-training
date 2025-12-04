package com.consumerkafka.consumer.infrastructure.persistence.adapter;

import com.consumerkafka.consumer.core.domain.model.TrainActivity;
import com.consumerkafka.consumer.core.port.TrainActivityRepository;
import com.consumerkafka.consumer.infrastructure.persistence.entity.FactTrainActivityEntity;
import com.consumerkafka.consumer.infrastructure.persistence.entity.DimTimeEntity;
import com.consumerkafka.consumer.infrastructure.persistence.entity.TrainServiceItemEntity;
import com.consumerkafka.consumer.infrastructure.persistence.jpa.JpaTrainActivityRepository;
import com.consumerkafka.consumer.infrastructure.persistence.mapper.PersistenceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@SuppressWarnings("null")
public class JpaTrainActivityAdapter implements TrainActivityRepository {

    private final JpaTrainActivityRepository jpaRepository;
    private final PersistenceMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    public JpaTrainActivityAdapter(JpaTrainActivityRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TrainActivity save(TrainActivity activity) {
        FactTrainActivityEntity entity = Objects.requireNonNull(
            mapper.toEntity(activity),
            "TrainActivity mapping produced null entity"
        );
        attachReferences(activity, entity);
        FactTrainActivityEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainActivity> findById(Long id) {
        Long activityId = Objects.requireNonNull(id, "activityId cannot be null");
        return jpaRepository.findById(activityId).map(mapper::toDomain);
    }

    private void attachReferences(TrainActivity activity, FactTrainActivityEntity entity) {
        Integer trainServiceId = Objects.requireNonNull(
            activity.getTrainService().getId(),
            "TrainActivity requires persisted TrainService with id"
        );
        int dateId = activity.getTimeDimension().getDateId();

        entity.setTrainServiceItem(
            entityManager.getReference(TrainServiceItemEntity.class, trainServiceId)
        );
        entity.setTime(entityManager.getReference(DimTimeEntity.class, dateId));
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


