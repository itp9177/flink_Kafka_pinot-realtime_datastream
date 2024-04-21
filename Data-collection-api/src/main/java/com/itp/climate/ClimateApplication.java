package com.itp.climate;

import com.itp.climate.data.Collector;
import com.itp.climate.kafka.ProduceData;
import com.itp.openapi.model.V1ForecastGet200Response;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@SpringBootApplication
@Configuration
@EnableScheduling
public class ClimateApplication {

	private ProduceData produceData ;
    public ClimateApplication(ProduceData produceData) {
		this.produceData = produceData;
		this.produceData.produceToKafka();
    }

	@Scheduled(fixedRate = 1000) // Runs every 1 second (1000 milliseconds)
    public void runTask() {
    System.out.println("Running task every 1 second");
    this.produceData = produceData;
	this.produceData.produceToKafka();
  }

    public static void main(String[] args) {
		SpringApplication.run(ClimateApplication.class, args);

	}


}
