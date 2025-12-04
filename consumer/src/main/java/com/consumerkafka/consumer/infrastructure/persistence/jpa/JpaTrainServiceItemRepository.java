package com.consumerkafka.consumer.infrastructure.persistence.jpa;

import com.consumerkafka.consumer.infrastructure.persistence.entity.TrainServiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTrainServiceItemRepository extends JpaRepository<TrainServiceItemEntity, Integer> {

    Optional<TrainServiceItemEntity> findByRsid(String rsid);
}

