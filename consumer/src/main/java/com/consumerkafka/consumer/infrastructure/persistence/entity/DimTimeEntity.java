package com.consumerkafka.consumer.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "dim_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DimTimeEntity {
    @Id
    @Column(name = "date_id")
    private Integer dateId;

    @Column(name = "day")
    private String day;

    @Column(name = "month")
    private Integer month;

    @Column(name = "week")
    private Integer week;

    @Column(name = "year")
    private Integer year;

    @Column(name = "time")
    private LocalTime time;
}

