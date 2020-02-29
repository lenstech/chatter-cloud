package com.lens.chatter.model.entity;

import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.constant.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
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

// TODO: 16 Şub 2020  photo will be added

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    private String userFirmId;

    private String title;

    @Column(name = "confirmed")
    private boolean confirmed = false;

    public String toStringForSearch() {
        return (" " + email +
                " " + name +
                " " + surname).toLowerCase(/*Locale.ENGLISH*/);
    }
}
