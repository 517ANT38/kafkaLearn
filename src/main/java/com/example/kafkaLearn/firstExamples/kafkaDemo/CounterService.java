package com.example.kafkaLearn.firstExamples.kafkaDemo;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private AtomicInteger counter = new AtomicInteger();

    public void increment() {
        counter.incrementAndGet();
    }

    public Integer getResult(){
        return counter.get();
    }

}
