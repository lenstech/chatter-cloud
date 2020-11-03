package com.lens.chatter.mapper;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.FilterDto;
import com.lens.chatter.model.entity.Filter;
import com.lens.chatter.model.other.SearchCriteria;
import com.lens.chatter.model.resource.product.FilterResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FilterMapper implements Converter<FilterDto, Filter, FilterResource> {
    @Autowired
    private MinimalUserMapper minimalUserMapper;

    @Autowired
    private Gson gson;

    public FilterResource toResource(Filter filter) {
        FilterResource resource = new FilterResource(filter.getName(),
                minimalUserMapper.toResource(filter.getUser()),
                jsonToList(filter.getCriteriaListJson())
        );
        resource.setCreatedDate(filter.getCreatedDate());
        resource.setLastModifiedDate(filter.getLastModifiedDate());
        resource.setName(filter.getName());
        resource.setId(filter.getId());
        return resource;
    }


    public Filter toEntity(FilterDto filterDto) {
        Filter filter = new Filter();
        filter.setCriteriaListJson(gson.toJson(filterDto.getFilterCriteria()));
        filter.setName(filterDto.getName());
        return filter;
    }

    public List<SearchCriteria> jsonToList(String criteriaListJson) {
        Type type = new TypeToken<List<SearchCriteria>>() {
        }.getType();
        return gson.fromJson(criteriaListJson, type);
    }

    @Override
    public List<FilterResource> toResources(List<Filter> entities) {
        if (entities == null) {
            return null;
        }
        List<FilterResource> list = new ArrayList<FilterResource>(entities.size());
        for (Filter filter : entities) {
            list.add(toResource(filter));
        }
        return list;
    }

    @Override
    public Set<FilterResource> toResources(Set<Filter> entities) {
        if (entities == null) {
            return null;
        }

        Set<FilterResource> set = new HashSet<FilterResource>(Math.max((int) (entities.size() / .75f) + 1, 16));
        for (Filter filter : entities) {
            set.add(toResource(filter));
        }

        return set;
    }
}
