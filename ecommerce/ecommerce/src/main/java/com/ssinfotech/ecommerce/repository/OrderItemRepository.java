package com.ssinfotech.ecommerce.repository;

import com.ssinfotech.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Additional query methods if needed
}

