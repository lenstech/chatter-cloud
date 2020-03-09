package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Department findDepartmentById(UUID id);

    Set<Department> findDepartmentsByBranch(Branch branch);
}
