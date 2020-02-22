package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.constant.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Data
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends AbstractEntity {

    @NotNull
    @Email(message = "Please provide acceptable mail address")
    private String email;

    @Column(length = 60)
    @NotEmpty(message = "Please provide your password")
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    private String department;

// TODO: 16 Şub 2020  photo will be added

    private String userFirmId;

    private String title;

    @Column(name = "confirmed")
    private boolean confirmed = false;

    public String toStringForSearch() {
        return (" " + email +
                " " + name +
                " " + surname +
                " " + department).toLowerCase(/*Locale.ENGLISH*/);
    }
}
