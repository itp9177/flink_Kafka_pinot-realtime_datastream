package com.itp.climate.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class Topic {
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        return new KafkaAdmin(configs);
    }
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("wea2")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("wea4")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
