package com.lens.chatter.model.resource.user;

import com.lens.chatter.enums.Role;
import com.lens.chatter.model.resource.organization.DepartmentResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 15 Eki 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InviteMailResource {
    private String mail;
    private String title;
    private Role role;
    private DepartmentResource department;
}
