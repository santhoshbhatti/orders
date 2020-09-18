package com.ulocal.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulocal.orders.domain.Order;
import com.ulocal.orders.dto.OrderDto;
import com.ulocal.orders.mapper.OrderMapper;
import com.ulocal.orders.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Value("${orders.topic.name}")
    private String orderTopic;
    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        OrderDto savedOrderDto = null;

        Order order = orderMapper.orderDtoToOrder(orderDto);
        order.setOrderId(UUID.randomUUID());
        Order savedOrder=ordersRepository.save(order);
        savedOrderDto=orderMapper.orderToOrderDto(savedOrder);
        //String orderJson= objectMapper.writeValueAsString(savedOrderDto);
        ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(orderTopic,savedOrderDto.getOrderId().toString() ,savedOrderDto);
        result.addCallback((success) -> System.out.println("success>>>"+success),(ex)->ex.printStackTrace());

        return savedOrderDto;
    }

    @Override
    public OrderDto updateOrder(UUID id, OrderDto orderDto) {
        Order order=ordersRepository.findById(id).orElseThrow(NoSuchElementException::new);
        order.setItem(orderDto.getItem());
        order.setInventoryStatus(orderDto.getInventoryStatus());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setPrice(orderDto.getPrice());
        Order updatedOrder=ordersRepository.save(order);
        return orderMapper.orderToOrderDto(updatedOrder);
    }
}
