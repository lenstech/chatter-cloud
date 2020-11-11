package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.other.ProductTypeCount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends ChatterRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Integer countProductsByCreatedDateBetween(ZonedDateTime begin, ZonedDateTime end);

    @Query("select new com.lens.chatter.model.other.ProductTypeCount(p.productType.id, p.productType.name, p.productType.description, p.productType.color, count(p), (1.0f*count(p))/ ?3 ) " +
            " from Product p " +
            " where p.createdDate between ?1 and ?2 " +
            " group by p.productType, p.productType.name, p.productType.description, p.productType.color")
    List<ProductTypeCount> getStatisticsByProductType(ZonedDateTime startTime, ZonedDateTime endTime, float total);
}
