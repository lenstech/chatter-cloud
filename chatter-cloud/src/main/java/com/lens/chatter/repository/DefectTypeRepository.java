package com.lens.chatter.repository;

import com.lens.chatter.model.entity.DefectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DefectTypeRepository extends ChatterRepository<DefectType, UUID> {

}
