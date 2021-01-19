package com.lens.chatter.repository;

import com.lens.chatter.model.entity.ProductPhoto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductPhotoRepository extends ChatterRepository<ProductPhoto, UUID> {

    boolean existsByProductId(UUID ProductId);

    Optional<ProductPhoto> findProductPhotoByProductId(UUID ProductId);

    void deleteProductPhotoByProductId(UUID ProductId);
}
