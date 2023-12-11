package com.example.kafkaLearn.firstExamples;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KafkaLearnApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		SpringApplication.run(KafkaLearnApplication.class, args);
		var producer = new MyProducer("spring-topic-demo");
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try{
					producer.send(String.valueOf(i), "Hello from MyProducer!");
					TimeUnit.SECONDS.sleep(5);
				}
				catch(Throwable t){
					Thread.currentThread().interrupt();
				}
			}
		}).start();

		var comsumer = new MyConsumer("spring-topic-demo");

		comsumer.consume((r) -> System.out.println("key="+r.key()+", "+"value="+r.value()) );

		TimeUnit.MINUTES.sleep(5);
		
		producer.close();
		comsumer.close();
	}

}
