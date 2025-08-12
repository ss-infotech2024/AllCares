package com.ssinfotech.ecommerce.repository;

import com.ssinfotech.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom method to find all orders placed by a particular user
    List<Order> findByUserId(Long userId);
}

