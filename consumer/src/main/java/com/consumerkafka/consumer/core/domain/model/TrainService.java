package com.consumerkafka.consumer.core.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Pure domain representation of a train service arrival.
 */
public final class TrainService {

    private final Integer id;
    private final String rsid;
    private final String arrivalStationName;
    private final String arrivalStationCode;
    private final LocalDateTime scheduledArrival;
    private final LocalDateTime estimatedArrival;
    private final String operator;

    private TrainService(Builder builder) {
        this.id = builder.id;
        this.rsid = Objects.requireNonNull(builder.rsid, "rsid");
        this.arrivalStationName = Objects.requireNonNull(builder.arrivalStationName, "arrivalStationName");
        this.arrivalStationCode = Objects.requireNonNull(builder.arrivalStationCode, "arrivalStationCode");
        this.scheduledArrival = Objects.requireNonNull(builder.scheduledArrival, "scheduledArrival");
        this.estimatedArrival = Objects.requireNonNull(builder.estimatedArrival, "estimatedArrival");
        this.operator = Objects.requireNonNull(builder.operator, "operator");
    }

    public Integer getId() {
        return id;
    }

    public String getRsid() {
        return rsid;
    }

    public String getArrivalStationName() {
        return arrivalStationName;
    }

    public String getArrivalStationCode() {
        return arrivalStationCode;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public LocalDateTime getEstimatedArrival() {
        return estimatedArrival;
    }

    public String getOperator() {
        return operator;
    }

    public TrainService withId(Integer id) {
        return builder()
            .from(this)
            .id(id)
            .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private String rsid;
        private String arrivalStationName;
        private String arrivalStationCode;
        private LocalDateTime scheduledArrival;
        private LocalDateTime estimatedArrival;
        private String operator;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder rsid(String rsid) {
            this.rsid = rsid;
            return this;
        }

        public Builder arrivalStationName(String arrivalStationName) {
            this.arrivalStationName = arrivalStationName;
            return this;
        }

        public Builder arrivalStationCode(String arrivalStationCode) {
            this.arrivalStationCode = arrivalStationCode;
            return this;
        }

        public Builder scheduledArrival(LocalDateTime scheduledArrival) {
            this.scheduledArrival = scheduledArrival;
            return this;
        }

        public Builder estimatedArrival(LocalDateTime estimatedArrival) {
            this.estimatedArrival = estimatedArrival;
            return this;
        }

        public Builder operator(String operator) {
            this.operator = operator;
            return this;
        }

        public Builder from(TrainService trainService) {
            this.id = trainService.id;
            this.rsid = trainService.rsid;
            this.arrivalStationName = trainService.arrivalStationName;
            this.arrivalStationCode = trainService.arrivalStationCode;
            this.scheduledArrival = trainService.scheduledArrival;
            this.estimatedArrival = trainService.estimatedArrival;
            this.operator = trainService.operator;
            return this;
        }

        public TrainService build() {
            return new TrainService(this);
        }
    }
}


