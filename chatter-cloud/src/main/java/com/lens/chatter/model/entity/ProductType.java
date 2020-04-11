package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.enums.LengthMeasureUnit;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */

@Data
@Entity
@Table(name = "product_type")
public class ProductType extends AbstractEntity<UUID> {

    @NotNull
    private String name;

    private String description;

    private Float width;

    private Float height;

    private String color;

    @Enumerated(EnumType.STRING)
    private LengthMeasureUnit unit = LengthMeasureUnit.METER;

    // TODO: 11 Nis 2020 Resim eklenecek

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Integer price;



    private String materialType;
}