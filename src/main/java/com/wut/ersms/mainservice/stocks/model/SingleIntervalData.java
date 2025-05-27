package com.wut.ersms.mainservice.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SingleIntervalData {
    private List<ValueTimestampPair> entries;
    private LocalDateTime lastUpdated;

    public SingleIntervalData(List<ValueTimestampPair> entries, LocalDateTime lastUpdated) {
        this.entries = new ArrayList<>(entries);
        this.lastUpdated = lastUpdated;
    }

    public SingleIntervalData() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(double value, LocalDateTime timestamp) {
        entries.add(new ValueTimestampPair(value, timestamp));
    }

    public void addValue(double value) {
        entries.add(new ValueTimestampPair(value, LocalDateTime.now()));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueTimestampPair {
        private double value;
        private LocalDateTime timestamp;
    }
}