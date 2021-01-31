package com.lens.chatter.model.resource.product;

import com.lens.chatter.common.AbstractResource;
import com.lens.chatter.enums.LengthMeasureUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 12 Nis 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectResource extends AbstractResource {

    private Double xCoordinate = 0D;
    private Double yCoordinate = 0D;
    private Double zCoordinate = 0D;
    private Double size = 1D;
    private String note;
    private DefectTypeResource defectType;
    private LengthMeasureUnit measureUnit;
    private Integer region;

}
