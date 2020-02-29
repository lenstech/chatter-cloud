package com.lens.chatter.common;

import com.lens.chatter.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.lens.chatter.constant.ErrorConstants.ID_IS_NOT_EXIST;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */

public abstract class AbstractService<T extends AbstractEntity, ID extends Serializable, DTO, RES> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    @Autowired
    protected JpaRepository<T, ID> genericRepository;

    @Autowired
    private Converter<DTO, T, RES> genericMapper;

    public RES save(DTO dto) {
        LOGGER.debug(String.format("Saving the dto [%s].", dto));
        return genericMapper.toResource(genericRepository.save(genericMapper.toEntity(dto)));
    }


    public RES get(ID id) {
        LOGGER.debug("Requesting {id} records.");
        return genericMapper.toResource(genericRepository.findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST)));
    }

    public List<RES> getAll() {
        LOGGER.debug("Requesting all records.");
        return genericMapper.toResources(genericRepository.findAll());
    }

    @Transactional
    public T put(ID id, DTO updatedDto) {
        LOGGER.debug(String.format("Request to update the record [%s].", id));
        T theReal = genericRepository.findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        if (updatedDto == null) {
            LOGGER.error("Entity can not be null.");
            return null;
        }
        if (id == null) {
            LOGGER.error("ID can not be null.");
            return null;
        }
        T updated = genericMapper.toEntity(updatedDto);
        updated.setId(theReal.getId());
        updated.setCreatedDate(theReal.getCreatedDate());
        return genericRepository.save(updated);
    }


    @Transactional
    public void delete(ID id) {
        LOGGER.debug(String.format("Request to delete the record [%s].", id));
        if (id == null) {
            LOGGER.error("ID can not be null.");
        }
        T entity = genericRepository.findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        genericRepository.delete(entity);
    }
}
