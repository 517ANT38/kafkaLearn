package com.example.kafkaLearn.firstExamples;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyConsumer implements Closeable{
    
    private String topic;
    private Consumer<String,String> consumer;

    {
        var props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(props);

        
    }

    


    public MyConsumer(String topic) {
        this.topic = topic;
        consumer.subscribe(Collections.singleton(topic));
    }


    public void consume(java.util.function.Consumer<ConsumerRecord<String,String>> s){

        new Thread(() -> {

            while (true) {
                var records = consumer.poll(Duration.ofSeconds(1));

                records.forEach((r) -> {
                    s.accept(r);
                });
            }

        }).start();
    } 



    @Override
    public void close() throws IOException {
        consumer.close();
    }


}
