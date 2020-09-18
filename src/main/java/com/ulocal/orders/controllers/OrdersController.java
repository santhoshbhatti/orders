package com.ulocal.orders.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ulocal.orders.mapper.OrderMapper;
import com.ulocal.orders.repository.OrdersRepository;
import com.ulocal.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import com.ulocal.orders.dto.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderDto=orderService.saveOrder(orderDto);
        return new ResponseEntity<OrderDto>(orderDto,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable("id") UUID id,@RequestBody OrderDto orderDto){
        orderDto = orderService.updateOrder(id,orderDto);
        return new ResponseEntity<OrderDto>(orderDto,HttpStatus.NO_CONTENT);
    }

}
