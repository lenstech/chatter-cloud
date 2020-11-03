package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Filter;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilterRepository extends ChatterRepository<Filter, UUID> {

}
