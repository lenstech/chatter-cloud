package com.lens.chatter.repository;

import com.lens.chatter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    User findById(UUID id);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findUserByName(String name);

    Boolean existsByEmail(String email);

    @Query(value = "select * from users where name like '%:name%'",nativeQuery = true)
    List<User> findUsersByName(String name);

    List<User> findAll();

}
