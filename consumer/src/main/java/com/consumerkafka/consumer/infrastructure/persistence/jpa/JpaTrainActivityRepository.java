package com.consumerkafka.consumer.infrastructure.persistence.jpa;

import com.consumerkafka.consumer.infrastructure.persistence.entity.FactTrainActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTrainActivityRepository extends JpaRepository<FactTrainActivityEntity, Long> {
}

