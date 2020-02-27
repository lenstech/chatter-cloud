package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Emir Gökdemir
 * on 22 Şub 2020
 */
@Data
@Entity
@Table(name = "department")
public class Department extends AbstractEntity {

    private String name;

    private String description;
}
