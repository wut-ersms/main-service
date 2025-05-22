package com.wut.ersms.mainservice.stocks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockDataPoint {
    @JsonProperty("Date")
    private OffsetDateTime timestamp;
    @JsonProperty("Open")
    private double open;
    @JsonProperty("High")
    private double high;
    @JsonProperty("Low")
    private double low;
    @JsonProperty("Close")
    private double close;
    @JsonProperty("Volume")
    private double volume;
    @JsonProperty("Dividends")
    private double dividends;
    @JsonProperty("Stock Splits")
    private double stockSplits;
}
