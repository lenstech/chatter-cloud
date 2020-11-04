package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2020
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "filter")
public class Filter extends AbstractEntity<UUID> {

    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String criteriaListJson;
}
