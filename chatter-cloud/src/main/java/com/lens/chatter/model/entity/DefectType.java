package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */

@Data
@Entity
@Table(name = "defect_type")
public class DefectType extends AbstractEntity<UUID> {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Short importanceLevel = 1;
}
