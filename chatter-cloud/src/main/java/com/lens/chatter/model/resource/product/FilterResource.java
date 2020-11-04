package com.lens.chatter.model.resource.product;

import com.lens.chatter.common.AbstractResource;
import com.lens.chatter.model.other.SearchCriteria;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterResource  extends AbstractResource {
    private String name;

    private MinimalUserResource user;

    private List<SearchCriteria> searchCriteria;
}
