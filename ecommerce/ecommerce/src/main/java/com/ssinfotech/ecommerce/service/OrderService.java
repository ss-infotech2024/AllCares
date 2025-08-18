package com.ssinfotech.ecommerce.service;

import com.ssinfotech.ecommerce.model.Order;
import com.ssinfotech.ecommerce.model.OrderItem;
import com.ssinfotech.ecommerce.model.Product;
import com.ssinfotech.ecommerce.model.User;
import com.ssinfotech.ecommerce.repository.OrderRepository;
import com.ssinfotech.ecommerce.repository.UserRepository;
import com.ssinfotech.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    // 1. Place order (with address snapshot, items list, userId)
    public Order placeOrder(Long userId, Order order, List<OrderItem> orderItems) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);

        // Associate items to the order and set price from products
        for (OrderItem item : orderItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            item.setOrder(order);
            item.setPrice(product.getPrice());
        }
        order.setOrderItems(orderItems);

        // Calculate total
        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);
        order.setOrderStatus("PENDING"); // initial status

        return orderRepository.save(order);
    }

    // 2. Get orders by user (for user's order history)
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // 3. Get all orders (admin dashboard)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 4. Update order status (admin feature)
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        // Optionally update updatedAt field
        order.setUpdatedAt(java.time.LocalDateTime.now());
        return orderRepository.save(order);
    }
}

