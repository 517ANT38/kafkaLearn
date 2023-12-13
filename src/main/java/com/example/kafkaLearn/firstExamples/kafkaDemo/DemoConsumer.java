package com.example.kafkaLearn.firstExamples.kafkaDemo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DemoConsumer {
    
    private final CounterService cService;

    @KafkaListener(topics = "spring-topic-demo", concurrency = "4")
    void listenTopic(ConsumerRecord<String, String> r){
        cService.increment();
    }
}
