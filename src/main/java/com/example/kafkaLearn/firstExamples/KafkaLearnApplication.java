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

import de.codecentric.boot.admin.server.config.EnableAdminServer;


@SpringBootApplication
@EnableAdminServer
public class KafkaLearnApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		SpringApplication.run(KafkaLearnApplication.class, args);
		
		
	}

	

}
