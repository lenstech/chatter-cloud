package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 22 Şub 2020
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "department")
public class Department extends AbstractEntity<UUID> {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull(message = "Branch cannot be blank")
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
