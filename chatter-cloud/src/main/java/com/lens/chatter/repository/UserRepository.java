package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserById(UUID id);

    Set<User> findByIdIn(Collection<UUID> ids);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findUserByName(String name);

    Boolean existsByEmail(String email);

    Set<User> findUsersByDepartment(Department department);

    @Query(value = "select * from users where name like '%:name%'", nativeQuery = true)
    List<User> findUsersByName(String name);

    List<User> findAll();

}
