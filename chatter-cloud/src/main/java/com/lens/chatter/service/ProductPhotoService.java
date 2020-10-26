package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.exception.NotFoundException;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.entity.ProductPhoto;
import com.lens.chatter.repository.ProductPhotoRepository;
import com.lens.chatter.repository.ProductRepository;
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
public class ProductPhotoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPhotoRepository repository;

    @Transactional
    public String uploadProductPhoto(MultipartFile file, UUID productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new NotFoundException(ErrorConstants.PRODUCT_NOT_EXIST);
        }
        ProductPhoto photo;
        if (repository.existsByProductId(productId)) {
            throw new BadRequestException(ErrorConstants.PRODUCT_PHOTO_ALREADY_EXIST);
        } else {
            photo = new ProductPhoto();
            photo.setProduct(product);
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
    public byte[] getPhoto(UUID productId) {
        ProductPhoto photo = repository.findProductPhotoByProductId(productId);
        if (photo == null) {
            throw new BadRequestException(ErrorConstants.PRODUCT_PHOTO_NOT_EXIST);
        }
        return photo.getFile();
    }

    @Transactional
    public void deletePhotoByProductId(UUID productId) {
        repository.deleteProductPhotoByProductId(productId);
    }

    @Transactional
    public void deletePhoto(UUID photoId) {
        repository.deleteById(photoId);
    }

}
