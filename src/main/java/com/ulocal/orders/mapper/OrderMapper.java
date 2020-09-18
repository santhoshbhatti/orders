package com.ulocal.orders.mapper;

import com.ulocal.orders.domain.Order;
import com.ulocal.orders.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto orderDto);
    OrderDto orderToOrderDto(Order order);
}
