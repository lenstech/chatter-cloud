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

    public static final int yAxisPieceCount = 2;
    public static final int xAxisPieceCount = 3;

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
    protected void updateOperationsAfterConvert(Defect entity, DefectDto dto, UUID userId) {
        entity.setRegion(checkDefectLocationAndFindRegion(entity));
    }

    @Override
    protected Defect saveOperations(Defect entity, DefectDto defectDto, UUID userId) {
        entity.setRegion(checkDefectLocationAndFindRegion(entity));
        return entity;
    }

    private Integer checkDefectLocationAndFindRegion(Defect entity) {
        ProductType type = entity.getProduct().getProductType();
        if (type.getWidth() == null || type.getLength() == null) {
            throw new BadRequestException(ErrorConstants.PRODUCT_TYPE_DIMENSIONS_ARE_NULL);
        }
        if (type.getWidth() < entity.getYCoordinate() ||
                type.getLength() < entity.getXCoordinate()) {
            throw new BadRequestException(ErrorConstants.DEFECT_SHOULD_BE_INSIDE_THE_PRODUCT);
        } else if (entity.getZCoordinate() != null && type.getHeight() < entity.getZCoordinate()) {
            throw new BadRequestException(ErrorConstants.DEFECT_SHOULD_BE_INSIDE_THE_PRODUCT);
        }
        double region = Math.ceil(entity.getXCoordinate() * xAxisPieceCount / type.getLength()) +
                (xAxisPieceCount *  Math.floor( yAxisPieceCount *entity.getYCoordinate() / (type.getWidth())));
        return (int) region;
    }
}
