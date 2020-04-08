package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.DefectTypeMapper;
import com.lens.chatter.model.dto.product.DefectTypeDto;
import com.lens.chatter.model.entity.DefectType;
import com.lens.chatter.model.resource.product.DefectTypeResource;
import com.lens.chatter.repository.DefectTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@Service
public class DefectTypeService extends AbstractService<DefectType, UUID, DefectTypeDto, DefectTypeResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefectTypeService.class);

    @Autowired
    private DefectTypeRepository repository;

    @Autowired
    private DefectTypeMapper mapper;

    @Override
    public JpaRepository<DefectType, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<DefectTypeDto, DefectType, DefectTypeResource> getConverter() {
        return mapper;
    }


}
