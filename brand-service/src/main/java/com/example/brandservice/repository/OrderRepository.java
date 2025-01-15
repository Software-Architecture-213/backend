package com.example.brandservice.repository;

import com.example.brandservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByBrandId(String brandId);
    Order findByOrderId(String orderId);
    List<Order> findAllByBrandIdIn(List<String> brandIds);
}