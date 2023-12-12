package com.example.kafkaLearn.firstExamples;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// @SpringBootApplication
public class KafkaLearnApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		// SpringApplication.run(KafkaLearnApplication.class, args);
		var producer = new MyProducer("spring-topic-demo");
		var messages = 30000;
		ExecutorService ex = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(messages);

		
		for (int i = 0; i < messages; i++) {
			final int j = i;
			ex.submit(() -> extracted(producer, latch, j));
		}
		latch.await();

		var comsumer = new MyConsumer("spring-topic-demo");

		FileWriter out = new FileWriter("test");

		comsumer.consume((r) -> {
			try {
				out.write("key=" + r.key() + ", "
				+"value="+r.value() + " partion="+ r.partition() +"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} );


		
		producer.close();
		ex.shutdown();
		TimeUnit.MINUTES.sleep(5);
		comsumer.close();

		out.close();
		
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
