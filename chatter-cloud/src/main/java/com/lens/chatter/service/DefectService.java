package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.DefectMapper;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
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

}
