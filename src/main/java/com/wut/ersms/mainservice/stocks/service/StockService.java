package com.wut.ersms.mainservice.stocks.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wut.ersms.mainservice.stocks.model.SingleIntervalData;
import com.wut.ersms.mainservice.stocks.model.StockDataPoint;
import com.wut.ersms.mainservice.stocks.model.StockPriceUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StockService {

    @AllArgsConstructor
    @Getter
    public enum Interval {
        MINUTE("1m", Duration.ofMinutes(1)),
        HOUR("1h", Duration.ofHours(1)),
        DAY("1d", Duration.ofDays(1));

        private final String intervalStr;
        private final Duration duration;

        public static Interval fromString(String value) {
            for (Interval interval : values()) {
                if (interval.intervalStr.equalsIgnoreCase(value)) {
                    return interval;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + value);
        }
    }

    private final String STOCK_UPDATE_TOPIC = "stock-price-updates";
    private final List<StockPriceUpdate> updates = new ArrayList<>();
    Map<String, Map<Interval, SingleIntervalData>> stockHistoricalData = new HashMap<>();

    private final ObjectMapper objectMapper;
    private final RestClient stockServiceClient;

    @KafkaListener(id = "stockUpdateListener", topics = STOCK_UPDATE_TOPIC)
    private void listenStockUpdates(String update) throws JsonProcessingException {
        try {
            StockPriceUpdate stockPriceUpdate = objectMapper.readValue(update, StockPriceUpdate.class);
            String ticker = stockPriceUpdate.getTicker();
            if (!stockHistoricalData.containsKey(ticker)) {
                return;
            }
            for (Map.Entry<Interval, SingleIntervalData> entry : stockHistoricalData.get(ticker).entrySet()) {
                Interval interval = entry.getKey();
                SingleIntervalData data = entry.getValue();
                if (data.getLastUpdated().plus(interval.getDuration())
                        .isBefore(LocalDateTime.now())) {
                    data.addEntry(stockPriceUpdate.getPrice(), stockPriceUpdate.getTimestamp());
                    data.setLastUpdated(LocalDateTime.now());
                }
            }
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse JSON:");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Original JSON: " + update);
            e.printStackTrace(); // Logs full stack trace to standard error
        }

    }

    private void fetchStockHistoricalData(String ticker, Interval interval) {
        List<StockDataPoint> stockDataPoints;
        stockDataPoints = stockServiceClient.get()
                .uri("/historical/{ticker}/{interval}", ticker, interval.getIntervalStr())
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        ((request, response) -> {
                            log.error("Error with code {} and headers: {} ", response.getStatusCode(), response.getHeaders());
                            throw new RuntimeException("Error with code " + response.getStatusCode());
                        }))
                .body(new ParameterizedTypeReference<List<StockDataPoint>>() {});
        List<SingleIntervalData.ValueTimestampPair> data_points = stockDataPoints.stream()
                .map(p -> new SingleIntervalData.ValueTimestampPair(p.getClose(), p.getTimestamp().toLocalDateTime()))
                .toList();
        if (!stockHistoricalData.containsKey(ticker)) {
            stockHistoricalData.put(ticker, new HashMap<>());
        }
        var newData = new SingleIntervalData(data_points, LocalDateTime.now());
        stockHistoricalData.get(ticker).put(interval, newData);
    }

    public List<SingleIntervalData.ValueTimestampPair> getHistoricalData(String ticker, Interval interval) {
        if (!stockHistoricalData.containsKey(ticker) || !stockHistoricalData.get(ticker).containsKey(interval)) {
            fetchStockHistoricalData(ticker, interval);
        }
        return stockHistoricalData.get(ticker).get(interval).getEntries();
    }
}
