package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends ChatterRepository<User, UUID> {

    Set<User> findByIdIn(Collection<UUID> ids);

    User findByEmail(String email);

    User findUserByName(String name);

    Boolean existsByEmail(String email);

    Set<User> findUsersByDepartment(Department department);

    List<User> findAll();

    Page<User> findUsersByDepartmentBranchId(Pageable pageable, UUID branchId);

    List<User> findUsersByDepartmentBranchId(UUID branchId);

    Page<User> findUsersByDepartmentBranchFirmId(Pageable pageable, UUID firmId);

    List<User> findUsersByDepartmentBranchFirmId(UUID firmId);

}
