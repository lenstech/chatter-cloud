package com.lens.chatter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.enums.LengthMeasureUnit;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 12 Nis 2020
 */
@Data
@Entity
@Table(name = "defect")
public class Defect extends AbstractEntity<UUID> {

    private Double xCoordinate = 0D;
    private Double yCoordinate = 0D;
    private Double zCoordinate = 0D;

    private Double size = 1D;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "DefectType cannot be blanked!")
    @ManyToOne
    @JoinColumn(name = "defect_type_id")
    private DefectType defectType;

    private LengthMeasureUnit measureUnit;
}
