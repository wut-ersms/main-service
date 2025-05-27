package com.wut.ersms.mainservice.stocks;

import com.wut.ersms.mainservice.stocks.model.SingleIntervalData;
import com.wut.ersms.mainservice.stocks.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/stock_data")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StockDataController {
    private final StockService stockService;

    @GetMapping("/{ticker}/{interval}")
    public List<SingleIntervalData.ValueTimestampPair> getTickerHistoricalData(@PathVariable String ticker, @PathVariable String interval) {
        return stockService.getHistoricalData(ticker, StockService.Interval.fromString(interval));
    }
}
