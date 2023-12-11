package com.example.kafkaLearn.firstExamples;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KafkaLearnApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		SpringApplication.run(KafkaLearnApplication.class, args);
		var producer = new MyProducer("spring-kafka-demo");
		var messages = 10000;
		ExecutorService ex = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(messages);

		
		for (int i = 0; i < messages; i++) {
			final int j = i;
			ex.submit(() -> extracted(producer, latch, j));
		}
		latch.await();

		var comsumer = new MyConsumer("spring-kafka-demo");

		comsumer.consume((r) -> System.out.println("key=" + r.key() + ", "
		+"value="+r.value() + " partion="+ r.partition()) );

		
		TimeUnit.MINUTES.sleep(5);
		producer.close();
		comsumer.close();
	}

	private static void extracted(MyProducer producer, CountDownLatch latch, int i) {
		try{
			producer.send(String.valueOf(i), "Hello from MyProducer!");
			latch.countDown();
		}
		catch(Throwable t){
			Thread.currentThread().interrupt();
		}
	}

}
