package com.consumerkafka.consumer.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fact_train_activity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactTrainActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_train_service_item_id", nullable = false)
    private TrainServiceItemEntity trainServiceItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_time_id", nullable = false)
    private DimTimeEntity time;

    @Column(name = "latency_minutes")
    private Integer latencyMinutes;

    @Column(name = "is_delayed", nullable = false)
    @Builder.Default
    private Integer isDelayed = 0;
}

