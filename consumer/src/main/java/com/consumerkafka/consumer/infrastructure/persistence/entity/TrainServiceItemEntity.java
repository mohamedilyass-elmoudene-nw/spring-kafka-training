package com.consumerkafka.consumer.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "train_service_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainServiceItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_service_item_id")
    private Integer trainServiceItemId;

    @Column(name = "rsid")
    private String rsid;

    @Column(name = "arrival_station_name")
    private String arrivalStationName;

    @Column(name = "arrival_station_abrv")
    private String arrivalStationAbrv;

    @Column(name = "sta")
    private LocalDateTime sta;

    @Column(name = "eta")
    private LocalDateTime eta;

    @Column(name = "operator")
    private String operator;
}

