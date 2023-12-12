package com.example.kafkaLearn.firstExamples;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyProducer implements Closeable{
    
    private String topic;
    private Producer<String,String> producer;
    
    {
        var props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:39092");
		props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "clientId");
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		this.producer = new KafkaProducer<>(props);

    }

    public MyProducer(String topic) {
        this.topic = topic;
    }

    public void send(String key, String value) throws InterruptedException, ExecutionException{
        producer
            .send(new ProducerRecord<String,String>(topic, key, value))
            .get();
    }

    @Override
    public void close() throws IOException {
        producer.close();
    }
}
