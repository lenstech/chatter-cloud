package com.lens.chatter.model.dto.product;

import com.lens.chatter.enums.LengthMeasureUnit;
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
public class ProductTypeDto {
    private String name;
    private String description;

    private Float width;

    private Float height;

    private LengthMeasureUnit unit;

    private String color;

    private Integer price;

    private String materialType;

}
