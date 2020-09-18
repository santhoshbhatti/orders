package com.ulocal.orders.repository;

import com.ulocal.orders.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OrdersRepository extends MongoRepository<Order, UUID> {
}
