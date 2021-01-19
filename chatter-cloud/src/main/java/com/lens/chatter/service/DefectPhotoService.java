package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.entity.DefectPhoto;
import com.lens.chatter.repository.DefectPhotoRepository;
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
public class DefectPhotoService {

    @Autowired
    private DefectPhotoRepository repository;

    @Autowired
    private DefectService defectService;

    @Transactional
    public String uploadDefectPhoto(MultipartFile file, UUID defectId) {
        DefectPhoto photo = repository.findDefectPhotoByDefectId(defectId).orElse(new DefectPhoto(defectService.fromIdToEntity(defectId)));
        try {
            photo.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(photo);
        return HttpSuccessMessagesConstants.PHOTO_SUCCESSFULLY_UPLOADED;
    }

    @Transactional
    public byte[] getPhoto(UUID defectId) {
        DefectPhoto photo = repository.findDefectPhotoByDefectId(defectId).orElse(null);
        if (photo == null) {
            throw new BadRequestException(ErrorConstants.DEFECT_PHOTO_NOT_EXIST);
        }
        return photo.getFile();
    }

    @Transactional
    public void deletePhotoByDefectId(UUID defectId) {
        if (repository.existsByDefectId(defectId)) {
            repository.deleteDefectPhotoByDefectId(defectId);
        }
    }

    @Transactional
    public void deletePhoto(UUID photoId) {
        repository.deleteById(photoId);
    }

}
