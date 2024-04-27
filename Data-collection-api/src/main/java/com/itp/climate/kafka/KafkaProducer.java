package com.itp.climate.kafka;

import com.itp.openapi.model.V1ForecastGet200Response;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducer {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    public String producerServer;

    @Bean(name = "kafka1")
    public KafkaTemplate<String, V1ForecastGet200Response> kafkaTemplate(ProducerFactory<String, V1ForecastGet200Response> producerFactoryJson)
    {
        return new KafkaTemplate<>(producerFactoryJson);
    }

    @Bean
    public ProducerFactory<String, V1ForecastGet200Response> producerFactoryJson() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,producerServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
