package com.lens.chatter.repository;

import com.lens.chatter.model.entity.ProductType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductTypeRepository extends ChatterRepository<ProductType, UUID> {
}
