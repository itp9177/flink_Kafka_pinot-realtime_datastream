package com.itp.climate.kafka;

import com.itp.climate.data.Collector;
import com.itp.openapi.model.V1ForecastGet200Response;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ProduceData {

    @Autowired
    @Qualifier("kafka1")
    public KafkaTemplate<String, V1ForecastGet200Response> kafkaTemplateJson;
    public void produceToKafka() {
        Collector co = new Collector();
        System.out.println("done");
        co.getForecast()
                .subscribe(this::handleResponse, error -> {
                    System.err.println("Error: " + error.getMessage());
                });
        //	System.out.println(co.getForecast().subscribe());
    }

    public void handleResponse(V1ForecastGet200Response data) {
        System.out.println("Response: " + data);
        ProducerRecord<String, V1ForecastGet200Response> record = new ProducerRecord<>("wea2", UUID.randomUUID().toString(), data);
        this.kafkaTemplateJson.send(record);
    }
}
