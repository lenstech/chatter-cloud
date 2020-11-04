package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "product_photo")
public class ProductPhoto extends AbstractEntity<UUID> {

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column
    @Lob
    private byte[] file;

}
