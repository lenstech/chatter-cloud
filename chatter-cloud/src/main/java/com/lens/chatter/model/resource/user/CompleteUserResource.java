package com.lens.chatter.model.resource.user;

import com.lens.chatter.constant.Role;
import com.lens.chatter.model.resource.DepartmentResource;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Getter
@Setter
@Resource
public class CompleteUserResource extends MinimalUserResource {

    private String email;

    private DepartmentResource department;

    private Role role;

}
