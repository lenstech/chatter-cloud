package com.lens.chatter.model.resource.product;

import com.lens.chatter.common.AbstractResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectTypeResource extends AbstractResource {
    private String name;
    private String description;
    private Short importanceLevel;
}
