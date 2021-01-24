package com.lens.chatter.model.dto.product;

import com.lens.chatter.enums.LengthMeasureUnit;
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
public class ProductTypeDto {

    @NotNull(message = "Name should be assign")
    private String name;

    private String description;

    @NotNull(message = "Width should be assign")
    private Float width;

    private Float height;

    @NotNull(message = "Length should be assign")
    private Float length;

    private LengthMeasureUnit unit;

    private String color;

    @NotNull(message = "Price should be assign")
    private Integer price;

    private String materialType;
}
