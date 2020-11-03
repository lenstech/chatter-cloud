package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.model.other.SearchCriteria;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2020
 */

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
