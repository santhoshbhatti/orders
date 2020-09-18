package com.ulocal.orders.messagelisteners;

import com.ulocal.orders.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryMessageListener {



    @KafkaListener(topics="inventory-topic",
            clientIdPrefix = "json",
    containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, OrderDto> cr,@Payload OrderDto orderDto){
        log.info(orderDto.toString());
    }
}
