package com.lens.chatter.model.dto.product;

import com.lens.chatter.enums.LengthMeasureUnit;
import com.lens.chatter.model.entity.DefectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 12 Nis 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectDto {
    private Double xCoordinate = 0D;
    private Double yCoordinate = 0D;
    private Double zCoordinate = 0D;
    private Double size = 1D;
    private UUID defectTypeId;
    private LengthMeasureUnit measureUnit;
}
