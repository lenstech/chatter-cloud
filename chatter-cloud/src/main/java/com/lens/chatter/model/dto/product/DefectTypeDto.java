package com.lens.chatter.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectTypeDto {
    private String name;
    private String description;
    @NotNull
    private Short importanceLevel;
}
