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
        PageRequest pageable = getPageable(pageNumber);
        ProductSpecification spec = new ProductSpecification();
        spec.addAll(searchDto.getFilterCriteria());
        try {
            return productRepository.findAll(spec, pageable).map(productMapper::toResource);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new BadRequestException(ErrorConstants.INVALID_FILTER_OBJECTS);
        }
    }

    @Override
    protected Filter saveOperations(Filter entity, FilterDto filterDto, UUID userId) {
        entity.setUser(userService.fromIdToEntity(userId));
        return entity;
    }

    @Override
    protected void updateOperationsAfterConvert(Filter entity, FilterDto updatedDto, UUID userId) {
        if (entity.getUser() == null) {
            entity.setUser(userService.fromIdToEntity(userId));
        }
    }

    public Page<ProductResource> searchBySavedFilter(UUID filterId, UUID userId, Integer pageNo) {
        Filter filter = fromIdToEntity(filterId);
        if (!filter.getUser().getId().equals(userId)) {
            throw new BadRequestException(ErrorConstants.THIS_FILTER_IS_NOT_BELONG_TO_THIS_USER);
        }
        return search(new SearchDto(mapper.jsonToList(filter.getCriteriaListJson())), pageNo);
    }
}
