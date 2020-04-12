package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;


@Repository
public interface BranchRepository extends ChatterRepository<Branch, UUID> {

    Branch findBranchById(UUID id);

    Set<Branch> findBranchesByFirmId(UUID firmId);
}
