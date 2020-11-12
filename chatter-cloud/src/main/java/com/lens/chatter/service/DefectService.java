package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.DefectMapper;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.entity.ProductType;
import com.lens.chatter.model.resource.product.DefectResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.DefectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@Service
public class DefectService extends AbstractService<Defect, UUID, DefectDto, DefectResource> {

    @Autowired
    private DefectRepository repository;

    @Autowired
    private DefectMapper mapper;

    @Override
    public ChatterRepository<Defect, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<DefectDto, Defect, DefectResource> getConverter() {
        return mapper;
    }

    @Override
    protected Defect putOperations(Defect oldEntity, Defect newEntity, UUID userId) {
        newEntity.setRegion(checkDefectLocationAndFindRegion(newEntity));
        return super.putOperations(oldEntity, newEntity, userId);
    }

    @Override
    protected Defect saveOperations(Defect entity, DefectDto defectDto, UUID userId) {
        entity.setRegion(checkDefectLocationAndFindRegion(entity));
        return super.saveOperations(entity, defectDto, userId);
    }

    private int checkDefectLocationAndFindRegion(Defect entity) {
        ProductType type = entity.getProduct().getProductType();
        if (type.getWidth() < entity.getYCoordinate() ||
                type.getLength() < entity.getXCoordinate()) {
            throw new BadRequestException(ErrorConstants.DEFECT_SHOULD_BE_INSIDE_THE_PRODUCT);
        } else if (entity.getZCoordinate() != null && type.getHeight() < entity.getZCoordinate()) {
            throw new BadRequestException(ErrorConstants.DEFECT_SHOULD_BE_INSIDE_THE_PRODUCT);
        }
        double region = Math.ceil(type.getLength() * 3 / entity.getXCoordinate()) +
                (3 * Math.floor(type.getWidth() / (2 * entity.getYCoordinate())));
        return (int) region;
    }
}
