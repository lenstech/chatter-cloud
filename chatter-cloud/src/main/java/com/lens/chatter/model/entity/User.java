package com.lens.chatter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lens.chatter.common.AbstractEntity;
import com.lens.chatter.constant.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Data
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends AbstractEntity<UUID> {

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private ProfilePhoto profilePhoto;

    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    private String userFirmId;

    private String title;

    @Column(name = "confirmed")
    private boolean confirmed = false;

    @ManyToMany(mappedBy = "users")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<UserGroup> userGroups = new HashSet<>();

    public String toStringForSearch() {
        return (" " + email +
                " " + name +
                " " + surname).toLowerCase(/*Locale.ENGLISH*/);
    }
}
