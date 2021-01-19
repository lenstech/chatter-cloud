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
@Table(name = "defect_photo")
public class DefectPhoto extends AbstractEntity<UUID> {

    @OneToOne
    @JoinColumn(name = "defect_id", referencedColumnName = "id")
    private Defect defect;

    @Column
    @Lob
    private byte[] file;

    public DefectPhoto(Defect defect) {
        this.defect = defect;
    }

    public DefectPhoto() {

    }
}
