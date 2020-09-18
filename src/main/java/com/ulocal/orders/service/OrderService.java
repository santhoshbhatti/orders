package com.ulocal.orders.service;

import com.ulocal.orders.dto.OrderDto;

import java.util.UUID;

public interface OrderService {
    OrderDto saveOrder(OrderDto orderDto);

    OrderDto updateOrder(UUID id, OrderDto orderDto);
}
