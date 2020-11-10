package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@EqualsAndHashCode(exclude="defects", callSuper = true)
@Data
@Entity
@Table(name = "product")
public class Product extends AbstractEntity<UUID> {

    private String productionNo;

    private ZonedDateTime productionTime;

    @NotNull(message = "ProductType cannot be blanked!")
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    private Set<Defect> defects;

    private Integer shift;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;
}
