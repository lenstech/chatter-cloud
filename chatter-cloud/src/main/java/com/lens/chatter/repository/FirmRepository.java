package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Firm;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FirmRepository extends ChatterRepository<Firm, UUID> {

    Firm findFirmById(UUID id);

    Boolean existsByName(String name);

    Firm findByName(String name);
}
