package com.ulocal.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID orderId;
    private String item;
    private BigDecimal price;
    private String orderStatus;
    private String inventoryStatus;
    private String paymentStatus;
}
