package com.ssinfotech.ecommerce.controller;

import com.ssinfotech.ecommerce.model.Order;
import com.ssinfotech.ecommerce.model.OrderItem;
import com.ssinfotech.ecommerce.service.OrderService;
import com.ssinfotech.ecommerce.service.UserService;  // for getting user info by email
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;  // To get userId from email

    // DTO class to accept order data and order items in one request
    public static class OrderRequest {
        private Order order;             // shipping details snapshot etc.
        private List<OrderItem> orderItems;

        public Order getOrder() { return order; }
        public void setOrder(Order order) { this.order = order; }

        public List<OrderItem> getOrderItems() { return orderItems; }
        public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    }

    // DTO to update order status
    public static class StatusUpdateRequest {
        private String status;

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    /**
     * Place a new order for the logged-in user.
     * The frontend sends an OrderRequest JSON containing order and list of order items.
     */
    @PostMapping
    public ResponseEntity<?> placeOrder(Authentication authentication,
                                        @RequestBody OrderRequest orderRequest) {
        try {
            // Get logged-in user's email
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();

            // Retrieve user ID from email
            Long userId = userService.findByEmail(email).getId();

            // Call service to place order
            Order savedOrder = orderService.placeOrder(userId, orderRequest.getOrder(), orderRequest.getOrderItems());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order: " + e.getMessage());
        }
    }

    /**
     * Get orders of the logged-in user.
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUserOrders(Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            Long userId = userService.findByEmail(email).getId();

            List<Order> orders = orderService.getOrdersByUser(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve user orders: " + e.getMessage());
        }
    }

    /**
     * Get all orders (admin only).
     * Secure this endpoint by role in security configuration.
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Update status of an order (admin only).
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @RequestBody StatusUpdateRequest statusUpdateRequest) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, statusUpdateRequest.getStatus());
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update order status: " + e.getMessage());
        }
    }
}

