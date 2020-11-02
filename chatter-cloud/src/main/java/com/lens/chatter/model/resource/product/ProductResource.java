package com.lens.chatter.model.resource.product;

import com.lens.chatter.common.AbstractResource;
import com.lens.chatter.model.resource.organization.BranchResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResource extends AbstractResource {

    private String productionNo;

    private ZonedDateTime productionTime;

    private ProductTypeResource productType;

    private Set<DefectResource> defects;

    private Integer shift;

    private BranchResource branch;
}
