package com.ulocal.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private UUID orderId;
    private String item;
    private BigDecimal price;
    private String orderStatus;
    private String inventoryStatus;
    private String paymentStatus;
}
