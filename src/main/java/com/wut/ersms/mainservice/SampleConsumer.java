package com.wut.ersms.mainservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Tag(name = "Consumer", description = "Controller to view sample consumer")
public class SampleConsumer {

    private final List<String> alertsMessages = new ArrayList<>();
    private final List<String> mailsMessages = new ArrayList<>();

    @Operation(summary = "Fetch all alerts", description = "Some description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/alerts")
    public String showAlertsMessages() {
        return String.join("<br>", alertsMessages);
    }

    @GetMapping("/mails")
    public String showMailsMessages() {
        return String.join("<br>", mailsMessages);
    }

    @KafkaListener(id = "alertsListener", topics = SampleProducer.ALERTS_TOPIC)
    private void listenAlerts(String message) {
        var timestamp = LocalDateTime.now().toString();
        alertsMessages.add(timestamp + " - " + message);
    }

    @KafkaListener(id = "mailsListener", topics = SampleProducer.MAILS_TOPIC)
    private void listenMails(String message) {
        var timestamp = LocalDateTime.now().toString();
        mailsMessages.add(timestamp + " - " + message);
    }
}
