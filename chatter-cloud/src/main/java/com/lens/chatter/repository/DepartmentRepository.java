package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends ChatterRepository<Department, UUID> {

    Department findDepartmentById(UUID id);

    Set<Department> findDepartmentsByBranchId(UUID branchId);

    Boolean existsByName(String name);

    Department findTopByName(String name);
}
