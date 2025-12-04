package com.consumerkafka.consumer.core.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Aggregate root representing a processed train activity record.
 */
public final class TrainActivity {

    private final Long id;
    private final TrainService trainService;
    private final TimeDimension timeDimension;
    private final int latencyMinutes;
    private final boolean delayed;

    private TrainActivity(Builder builder) {
        this.id = builder.id;
        this.trainService = Objects.requireNonNull(builder.trainService, "trainService");
        this.timeDimension = Objects.requireNonNull(builder.timeDimension, "timeDimension");
        this.latencyMinutes = builder.latencyMinutes;
        this.delayed = builder.latencyMinutes > 0;
    }

    public static TrainActivity createFromArrivalTimes(
        TrainService trainService,
        TimeDimension timeDimension,
        LocalDateTime scheduledArrival,
        LocalDateTime estimatedArrival
    ) {
        Objects.requireNonNull(scheduledArrival, "scheduledArrival");
        Objects.requireNonNull(estimatedArrival, "estimatedArrival");
        int latency = (int) Duration.between(scheduledArrival, estimatedArrival).toMinutes();
        return builder()
            .trainService(trainService)
            .timeDimension(timeDimension)
            .latencyMinutes(latency)
            .build();
    }

    public Long getId() {
        return id;
    }

    public TrainService getTrainService() {
        return trainService;
    }

    public TimeDimension getTimeDimension() {
        return timeDimension;
    }

    public int getLatencyMinutes() {
        return latencyMinutes;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public TrainActivity withId(Long id) {
        return builder()
            .from(this)
            .id(id)
            .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private TrainService trainService;
        private TimeDimension timeDimension;
        private int latencyMinutes;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder trainService(TrainService trainService) {
            this.trainService = trainService;
            return this;
        }

        public Builder timeDimension(TimeDimension timeDimension) {
            this.timeDimension = timeDimension;
            return this;
        }

        public Builder latencyMinutes(int latencyMinutes) {
            this.latencyMinutes = latencyMinutes;
            return this;
        }

        public Builder from(TrainActivity activity) {
            this.id = activity.id;
            this.trainService = activity.trainService;
            this.timeDimension = activity.timeDimension;
            this.latencyMinutes = activity.latencyMinutes;
            return this;
        }

        public TrainActivity build() {
            return new TrainActivity(this);
        }
    }
}


