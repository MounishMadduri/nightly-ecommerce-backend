package com.nightlyecommercebackend.repository;

import com.nightlyecommercebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
