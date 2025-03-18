package com.wut.ersms.mainservice;

import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/addToTopic")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SampleProducer {

    public static final String ALERTS_TOPIC = "alerts-cdc-conditions";
    public static final String MAILS_TOPIC = "mails-cmd-send";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/alerts")
    public String addToAlertsTopic(@RequestParam String message) throws ExecutionException, InterruptedException {
        var result = kafkaTemplate.send(ALERTS_TOPIC, message);
        return "Message: " + message + " added to " + ALERTS_TOPIC + " topic<br>with result: " + result.get().toString();
    }

    @GetMapping("/mails")
    public String addToMailsTopic(@RequestParam String message) throws ExecutionException, InterruptedException {
        var result = kafkaTemplate.send(MAILS_TOPIC, message);
        return "Message: " + message + " added to " + MAILS_TOPIC + " topic<br>with result: " + result.get().toString();
    }
}
