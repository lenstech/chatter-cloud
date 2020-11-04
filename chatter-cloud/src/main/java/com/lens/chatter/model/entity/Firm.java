package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 1 Mar 2020
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "firm")
public class Firm extends AbstractEntity<UUID> {

    @NotNull
    private String name;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String taxId;
}
