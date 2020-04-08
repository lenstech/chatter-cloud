package com.lens.chatter.common;

import com.lens.chatter.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.lens.chatter.constant.ErrorConstants.*;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
public abstract class AbstractService<T extends AbstractEntity, ID extends Serializable, DTO, RES> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    public abstract JpaRepository<T, ID> getRepository();

    public abstract Converter<DTO, T, RES> getConverter();

    public RES save(DTO dto) {
        LOGGER.debug(String.format("Saving the dto [%s].", dto));
        return getConverter().toResource(getRepository().save(getConverter().toEntity(dto)));
    }

    public RES get(ID id) {
        LOGGER.debug("Requesting {id} records.");
        return getConverter().toResource(getRepository().findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST)));
    }

    public List<RES> getAll() {
        LOGGER.debug("Requesting all records.");
        return getConverter().toResources(getRepository().findAll());
    }

    @Transactional
    public T put(ID id, DTO updatedDto) {
        LOGGER.debug(String.format("Request to update the record [%s].", id));
        T theReal = getRepository().findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        if (updatedDto == null) {
            LOGGER.error(DTO_CANNOT_BE_EMPTY);
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        if (id == null) {
            LOGGER.error(ID_CANNOT_BE_EMPTY);
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        T updated = getConverter().toEntity(updatedDto);
        updated.setId(theReal.getId());
        updated.setCreatedDate(theReal.getCreatedDate());
        return getRepository().save(updated);
    }


    @Transactional
    public void delete(ID id) {
        LOGGER.debug(String.format("Request to delete the record [%s].", id));
        if (id == null) {
            LOGGER.error(ID_CANNOT_BE_EMPTY);
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        T entity = getRepository().findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        getRepository().delete(entity);
    }
}
