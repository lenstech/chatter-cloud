package com.lens.chatter.repository;

import com.lens.chatter.model.entity.ProfilePhoto;
import com.lens.chatter.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfilePhotoRepository extends ChatterRepository<ProfilePhoto, UUID> {

    ProfilePhoto findProfilePhotoByUserId(UUID userId);

    Boolean existsByUser(User user);
}
