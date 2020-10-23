package com.lens.chatter.repository;

import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.entity.UserGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserGroupRepository extends ChatterRepository<UserGroup, UUID> {

    List<UserGroup> findByUsers(User user);

}
