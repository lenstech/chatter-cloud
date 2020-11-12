package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.exception.NotFoundException;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.entity.DefectPhoto;
import com.lens.chatter.repository.DefectPhotoRepository;
import com.lens.chatter.repository.DefectRepository;
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
    private DefectRepository defectRepository;

    @Autowired
    private DefectPhotoRepository repository;

    @Transactional
    public String uploadDefectPhoto(MultipartFile file, UUID defectId) {
        Defect defect = defectRepository.findById(defectId).orElse(null);
        if (defect == null) {
            throw new NotFoundException(ErrorConstants.DEFECT_NOT_EXIST);
        }
        DefectPhoto photo;
        if (repository.existsByDefectId(defectId)) {
            throw new BadRequestException(ErrorConstants.DEFECT_PHOTO_ALREADY_EXIST);
        } else {
            photo = new DefectPhoto();
            photo.setDefect(defect);
        }
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
        DefectPhoto photo = repository.findDefectPhotoByDefectId(defectId);
        if (photo == null) {
            throw new BadRequestException(ErrorConstants.DEFECT_PHOTO_NOT_EXIST);
        }
        return photo.getFile();
    }

    @Transactional
    public void deletePhotoByDefectId(UUID defectId) {
        repository.deleteDefectPhotoByDefectId(defectId);
    }

    @Transactional
    public void deletePhoto(UUID photoId) {
        repository.deleteById(photoId);
    }

}
