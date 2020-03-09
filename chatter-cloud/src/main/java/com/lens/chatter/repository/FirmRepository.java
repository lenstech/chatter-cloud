package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FirmRepository extends JpaRepository<Firm, UUID> {

    Firm findFirmById(UUID id);
}
