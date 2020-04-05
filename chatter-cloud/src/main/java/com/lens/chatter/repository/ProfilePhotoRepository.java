package com.lens.chatter.repository;

import com.lens.chatter.model.entity.ProfilePhoto;
import com.lens.chatter.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, UUID> {
    ProfilePhoto findProfilePhotoById(UUID id);

    ProfilePhoto findProfilePhotoByUserId(UUID userId);

    Boolean existsByUser(User user);
}
