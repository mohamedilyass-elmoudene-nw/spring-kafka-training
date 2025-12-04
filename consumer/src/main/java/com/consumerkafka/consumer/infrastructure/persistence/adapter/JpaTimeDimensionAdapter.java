package com.consumerkafka.consumer.infrastructure.persistence.adapter;

import com.consumerkafka.consumer.core.domain.model.TimeDimension;
import com.consumerkafka.consumer.core.port.TimeDimensionRepository;
import com.consumerkafka.consumer.infrastructure.persistence.entity.DimTimeEntity;
import com.consumerkafka.consumer.infrastructure.persistence.jpa.JpaDimTimeRepository;
import com.consumerkafka.consumer.infrastructure.persistence.mapper.PersistenceMapper;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@SuppressWarnings("null")
public class JpaTimeDimensionAdapter implements TimeDimensionRepository {

    private final JpaDimTimeRepository jpaRepository;
    private final PersistenceMapper mapper;

    public JpaTimeDimensionAdapter(JpaDimTimeRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TimeDimension save(TimeDimension timeDimension) {
        DimTimeEntity entity = requireNonNull(
            mapper.toEntity(timeDimension),
            "TimeDimension mapping produced null entity"
        );
        DimTimeEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeDimension> findByDateId(int dateId) {
        return jpaRepository.findById(dateId).map(mapper::toDomain);
    }
    private static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}


