package com.consumerkafka.consumer.core.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;

/**
 * Value object describing the temporal dimension of a train event.
 */
public final class TimeDimension {

    private final int dateId;
    private final String dayOfWeek;
    private final int month;
    private final int week;
    private final int year;
    private final LocalTime time;

    private TimeDimension(Builder builder) {
        this.dateId = builder.dateId;
        this.dayOfWeek = Objects.requireNonNull(builder.dayOfWeek, "dayOfWeek");
        this.month = builder.month;
        this.week = builder.week;
        this.year = builder.year;
        this.time = Objects.requireNonNull(builder.time, "time");
    }

    public int getDateId() {
        return dateId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getMonth() {
        return month;
    }

    public int getWeek() {
        return week;
    }

    public int getYear() {
        return year;
    }

    public LocalTime getTime() {
        return time;
    }

    public static TimeDimension fromDateTime(LocalDate date, LocalTime time) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(time, "time");
        int dateId = date.getYear() * 10000 + date.getMonthValue() * 100 + date.getDayOfMonth();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return builder()
            .dateId(dateId)
            .dayOfWeek(date.getDayOfWeek().name())
            .month(date.getMonthValue())
            .week(date.get(weekFields.weekOfWeekBasedYear()))
            .year(date.getYear())
            .time(time)
            .build();
    }

    public static TimeDimension fromDateTime(LocalDateTime dateTime) {
        Objects.requireNonNull(dateTime, "dateTime");
        return fromDateTime(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int dateId;
        private String dayOfWeek;
        private int month;
        private int week;
        private int year;
        private LocalTime time;

        public Builder dateId(int dateId) {
            this.dateId = dateId;
            return this;
        }

        public Builder dayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder month(int month) {
            this.month = month;
            return this;
        }

        public Builder week(int week) {
            this.week = week;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder from(TimeDimension other) {
            this.dateId = other.dateId;
            this.dayOfWeek = other.dayOfWeek;
            this.month = other.month;
            this.week = other.week;
            this.year = other.year;
            this.time = other.time;
            return this;
        }

        public TimeDimension build() {
            return new TimeDimension(this);
        }
    }
}


