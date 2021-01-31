package com.lens.chatter.service;

import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.model.entity.ProfilePhoto;
import com.lens.chatter.repository.ProfilePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */
@Service
public class ProfilePhotoService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfilePhotoRepository repository;

    @Transactional
    public String uploadProfilePhoto(MultipartFile file, UUID userId) {
        ProfilePhoto photo = repository.findProfilePhotoByUserId(userId).orElse(new ProfilePhoto(userService.fromIdToEntity(userId)));
        try {
            photo.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(photo);
        return HttpSuccessMessagesConstants.PHOTO_SUCCESSFULLY_UPLOADED;
    }

    @Transactional
    public byte[] getPhoto(UUID userId) {
        ProfilePhoto photo = repository.findProfilePhotoByUserId(userId).orElse(null);
        if (photo == null) {
            return null;
        }
        return photo.getFile();
    }

    @Transactional
    public void deletePhotoByUserId(UUID userId) {
        if (repository.existsByUserId(userId)) {
            repository.deleteUserPhotoByUserId(userId);
        }
    }
}
