package com.ssinfotech.ecommerce.repository;

import com.ssinfotech.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be added here if needed in the future
}
