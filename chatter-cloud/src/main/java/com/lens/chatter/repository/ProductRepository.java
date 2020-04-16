package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends ChatterRepository<Product, UUID> {
}
