package com.example.kafkaLearn.firstExamples.kafkaDemo;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DemoController {
    
    private final KafkaTemplate<String,String> template;
    private final CounterService cService;
    private static final String topic = "spring-topic-demo";


    @GetMapping("/send-random-message")
    public String sendRandomMessage(){
        template.send(topic, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        return "Ok";
    }

    @GetMapping("/counter-result")
    public int getResult(){
        return cService.getResult();
    }

}
