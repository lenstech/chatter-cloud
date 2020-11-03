package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.FilterMapper;
import com.lens.chatter.mapper.ProductMapper;
import com.lens.chatter.model.dto.product.FilterDto;
import com.lens.chatter.model.dto.product.SearchDto;
import com.lens.chatter.model.entity.Filter;
import com.lens.chatter.model.resource.product.FilterResource;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.FilterRepository;
import com.lens.chatter.repository.ProductRepository;
import com.lens.chatter.repository.specifications.ProductSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.lens.chatter.constant.GeneralConstants.PAGE_SIZE;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2020
 */
@Service
public class FilterService extends AbstractService<Filter, UUID, FilterDto, FilterResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterService.class);

    @Autowired
    private FilterRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FilterMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ChatterRepository<Filter, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<FilterDto, Filter, FilterResource> getConverter() {
        return mapper;
    }

    public Page<ProductResource> search(SearchDto searchDto, int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        ProductSpecification spec = new ProductSpecification();
        spec.addAll(searchDto.getFilterCriteria());
        return productRepository.findAll(spec, pageable).map(productMapper::toResource);
    }

    @Override
    protected Filter saveOperations(Filter entity, FilterDto filterDto, UUID userId) {
        entity.setUser(userService.fromIdToEntity(userId));
        return entity;
    }

    @Override
    protected Filter putOperations(Filter oldEntity, Filter newEntity, UUID userId) {
        try {
            newEntity.setUser(oldEntity.getUser());
        } catch (NullPointerException e){
            newEntity.setUser(userService.fromIdToEntity(userId));
        }
        return newEntity;
    }

    public Page<ProductResource> searchBySavedFilter(UUID filterId, UUID userId, Integer pageNo){
        Filter filter = fromIdToEntity(filterId);
        if (!filter.getUser().getId().equals(userId)){
            throw new BadRequestException(ErrorConstants.THIS_OPERATION_IS_NOT_BELONG_TO_THIS_USER);
        }
        filter.getCriteriaListJson();
        return search(new SearchDto(mapper.jsonToList(filter.getCriteriaListJson())),pageNo);
    }
}
