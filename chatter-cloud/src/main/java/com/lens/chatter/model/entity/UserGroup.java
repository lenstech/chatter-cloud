package com.lens.chatter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lens.chatter.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */

@EqualsAndHashCode(callSuper = true, exclude = "users")
@Data
@Entity
@Table(name = "user_group")
public class UserGroup extends AbstractEntity<UUID> {

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_user_group",
            joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    private Boolean isPrivate = false;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}
