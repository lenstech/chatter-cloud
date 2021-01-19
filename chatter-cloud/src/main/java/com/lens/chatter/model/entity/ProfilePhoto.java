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
@Table(name = "profile_photo")
public class ProfilePhoto extends AbstractEntity<UUID> {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    @Lob
    private byte[] file;

    public ProfilePhoto(User user) {
        this.user = user;
    }

    public ProfilePhoto() {
    }
}
