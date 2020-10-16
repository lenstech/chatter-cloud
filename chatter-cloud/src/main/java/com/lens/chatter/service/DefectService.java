package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.DefectMapper;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.entity.DefectType;
import com.lens.chatter.model.resource.product.DefectResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.DefectRepository;
import com.lens.chatter.repository.DefectTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;
import static com.lens.chatter.constant.ErrorConstants.ID_CANNOT_BE_EMPTY;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@Service
public class DefectService extends AbstractService<Defect, UUID, DefectDto, DefectResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefectService.class);

    @Autowired
    private DefectRepository repository;

    @Autowired
    private DefectMapper mapper;

    @Autowired
    private DefectTypeRepository typeRepository;

    @Override
    public ChatterRepository<Defect, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<DefectDto, Defect, DefectResource> getConverter() {
        return mapper;
    }

}
