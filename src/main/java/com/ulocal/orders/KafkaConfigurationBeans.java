package com.ulocal.orders;

import com.ulocal.orders.dto.OrderDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigurationBeans {

    @Autowired
    KafkaProperties kafkaProperties;


    @Value("${orders.topic.name}")
    private String ordersTopic;
    @Value("${inventory.topic.name}")
    private String inventoryTopic;
    @Value("${payment.topic.name}")
    private String paymentTopic;

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = kafkaProperties.buildProducerProperties();
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

    @Bean
    public NewTopic orderTopic(){
        return new NewTopic(ordersTopic,3,(short)1);
    }

    @Bean
    public NewTopic inverntoryTopic(){
        return new NewTopic(inventoryTopic,3,(short)1);
    }
    @Bean
    public NewTopic paymentTopic(){
        return new NewTopic(paymentTopic,3,(short)1);
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        final JsonDeserializer<Object> jsonDeserializer=new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),new StringDeserializer(),jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Object> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
