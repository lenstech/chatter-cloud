package com.lens.chatter.repository;

import com.lens.chatter.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserGroupRepository extends ChatterRepository<UserGroup, UUID> {
}
