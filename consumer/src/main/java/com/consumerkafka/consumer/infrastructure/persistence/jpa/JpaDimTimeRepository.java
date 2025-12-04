package com.consumerkafka.consumer.infrastructure.persistence.jpa;

import com.consumerkafka.consumer.infrastructure.persistence.entity.DimTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDimTimeRepository extends JpaRepository<DimTimeEntity, Integer> {
}

