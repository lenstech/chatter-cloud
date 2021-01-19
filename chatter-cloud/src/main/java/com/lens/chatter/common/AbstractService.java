package com.lens.chatter.common;

import com.lens.chatter.constant.GeneralConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.exception.NotFoundException;
import com.lens.chatter.repository.ChatterRepository;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

import static com.lens.chatter.constant.ErrorConstants.*;
import static com.lens.chatter.constant.GeneralConstants.PAGE_SIZE;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
public abstract class AbstractService<T extends AbstractEntity<ID>, ID extends Serializable, DTO, RES> {

    public abstract ChatterRepository<T, ID> getRepository();

    public abstract Converter<DTO, T, RES> getConverter();

    @Transactional
    public RES save(DTO dto, UUID userId) {
        T entity = getConverter().toEntity(dto);
        return getConverter().toResource(afterSaveOperations(getRepository().save(saveOperations(entity, dto, userId))));
    }

    public RES get(ID id, UUID userId) {
        T entity = fromIdToEntity(id);
        return getConverter().toResource(entity);
    }

    @Named("get")
    public RES get(ID id) {
        T entity = fromIdToEntity(id);
        return getConverter().toResource(entity);
    }

    public List<RES> getMultiple(List<ID> ids) {
        List<T> entities;
        try {
            entities = getRepository().findAllByIdIn(ids);
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
        return getConverter().toResources(entities);
    }

    public List<RES> getAll(UUID userId) {
        return getConverter().toResources(getRepository().findAll());
    }

    public Page<RES> getAllWithPage(int pageNumber, String sortBy, Sort.Direction direction, UUID userId) {
        PageRequest pageable;
        if (sortBy == null) {
            pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(GeneralConstants.DEFAULT_SORT_BY).descending());
        } else {
            if (direction == null) {
                direction = Sort.Direction.DESC;
            }
            try {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, direction, sortBy);
            } catch (PropertyReferenceException exception) {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(GeneralConstants.DEFAULT_SORT_BY).descending());
            }
        }
        return getRepository().findAll(pageable).map(getConverter()::toResource);
    }

    @Modifying
    @Transactional
    public RES put(ID id, DTO updatedDto, UUID userId) {
        if (id == null) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        if (updatedDto == null) {
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        T entity = fromIdToEntity(id);
        try {
            updateOperationsBeforeConvert(entity, updatedDto, userId);
            getConverter().toEntityForUpdate(updatedDto, entity);
            updateOperationsAfterConvert(entity, updatedDto, userId);
            return getConverter().toResource(getRepository().save(entity));
        } catch (Exception e) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
    }

    @Transactional
    @Modifying
    public void delete(ID id, UUID userId) {
        if (id == null) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        try {
            deleteOperations(id, userId);
            getRepository().deleteById(id);
        } catch (NullPointerException e) {
            throw new NotFoundException(ID_IS_NOT_EXIST);
        }
    }

    @Named("fromIdToEntity")
    public T fromIdToEntity(ID id) {
        Optional<T> entityOpt = getRepository().findById(id);
        if (!entityOpt.isPresent()) {
            throw new NotFoundException(getClass().getSimpleName().replace("Service", " ") + ID_IS_NOT_EXIST);
        } else {
            return entityOpt.get();
        }
    }

    @Named("fromIdsToEntities")
    public Set<T> idsToEntities(Set<ID> ids) {
        if (ids == null) {
            return new HashSet<>();
        } else {
            return getRepository().findByIdIn(ids);
        }
    }

    protected T afterSaveOperations(T entity) {
        return entity;
    }

    protected void updateOperationsAfterConvert(T entity, DTO updatedDto, UUID userId) {
    }

    ;

    protected void updateOperationsBeforeConvert(T entity, DTO dto, UUID userId) {
    }

    protected T saveOperations(T entity, DTO dto, UUID userId) {
        return entity;
    }

    protected void deleteOperations(ID id, UUID userId) {
    }
}
